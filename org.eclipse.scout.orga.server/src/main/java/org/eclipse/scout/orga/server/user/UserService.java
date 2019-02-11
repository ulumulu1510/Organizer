package org.eclipse.scout.orga.server.user;

import java.security.AllPermission;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.IAccessControlService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.scout.orga.database.or.core.tables.User;
import org.eclipse.scout.orga.database.or.core.tables.UserRole;
import org.eclipse.scout.orga.database.or.core.tables.records.PersonRecord;
import org.eclipse.scout.orga.database.or.core.tables.records.UserRecord;
import org.eclipse.scout.orga.database.or.core.tables.records.UserRoleRecord;
import org.eclipse.scout.orga.database.table.RoleTable;
import org.eclipse.scout.orga.server.ServerSession;
import org.eclipse.scout.orga.server.common.AbstractBaseService;
import org.eclipse.scout.orga.server.person.PersonService;
import org.eclipse.scout.orga.server.text.TextService;
import org.eclipse.scout.orga.shared.security.PasswordUtility;
import org.eclipse.scout.orga.shared.user.IUserService;
import org.eclipse.scout.orga.shared.user.ProfileFormData;
import org.eclipse.scout.orga.shared.user.ProfileFormData.OptionUserBox;
import org.eclipse.scout.orga.shared.user.UserFormData;
import org.eclipse.scout.orga.shared.user.UserFormData.RoleTable.RoleTableRowData;
import org.eclipse.scout.orga.shared.user.UserFormData.UserBox;
import org.eclipse.scout.orga.shared.user.UserTablePageData;
import org.eclipse.scout.orga.shared.user.UserTablePageData.UserTableRowData;

public class UserService extends AbstractBaseService<User, UserRecord> implements IUserService {

	@Override
	public User getTable() {
		return User.USER;
	}

	@Override
	public Field<String> getIdColumn() {
		return User.USER.USERNAME;
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(UserService.class);
	}

	/**
	 * Returns a list of all usernames.
	 */
	@Override
	public List<String> getUsernames() {
		return getAll().stream()
				.map(UserRecord::getUsername)
				.collect(Collectors.toList());
	}

	/**
	 * Gets the user specified by the username.
	 *
	 * @return the existing user record (or a new empty user record if no
	 *         existing record can be found)
	 */
	public UserRecord getOrCreate(String username) {
		UserRecord user = get(username);

		if (user != null) {
			return user;
		}

		user = new UserRecord();
		user.setUsername(username);
		user.setActive(true);

		return user;
	}

	/**
	 * Persists the provided user, person data and associated roles.
	 */
	private void store(UserRecord user, PersonRecord person, List<String> roleIds) {
		BEANS.get(PersonService.class)
				.store(person.getId(), person);
		store(user.getUsername(), user);
		storeUserRoles(user, roleIds);

		BEANS.get(IAccessControlService.class)
				.clearCache();
	}

	private void storeUserRoles(UserRecord user, List<String> roles) {
		String username = user.getUsername();

		DSLContext context = getContext();

		// delete existing user roles
		UserRole table = UserRole.USER_ROLE;
		context.deleteFrom(table)
				.where(table.USERNAME.eq(username))
				.execute();

		// add new user roles
		roles.stream()
				.forEach(role -> context.executeInsert(new UserRoleRecord(username, role)));
	}

	/**
	 * Returns a list of role ids for the user specified by the provided user
	 * id.
	 */
	private List<String> getRoles(UserRecord user) {
		if (user == null) {
			return new ArrayList<>();
		}

		UserRole table = UserRole.USER_ROLE;
		String username = user.getUsername();
		return getContext().select(table.ROLE_NAME)
				.from(table)
				.where(table.USERNAME.eq(username))
				.fetchStream()
				.map(record -> record.getValue(table.ROLE_NAME))
				.collect(Collectors.toList());
	}

	@Override
	public List<Permission> getPermissions(String username) {
		List<Permission> permissions = new ArrayList<>();
		List<String> roles = getRoles(get(username));

		// special case: user has root role
		if (roles.contains("root")) {
			permissions.add(new AllPermission());
		}
		// all 'normal' users
		else {
			RoleService roleService = BEANS.get(RoleService.class);

			for (String role : roles) {
				for (Permission permission : roleService.getPermissions(role)) {
					permissions.add(permission);
				}
			}
		}

		return permissions;
	}

	@Override
	public boolean verifyPassword(final String username, final String passwordPlain) {
		UserRecord user = get(username);

		if (user == null) {
			getLogger().warn("Provided user is is null");
			return false;
		}

		if (!PasswordUtility.passwordIsValid(passwordPlain, user.getPasswordEncrypted())) {
			getLogger().warn("Provided user and password do not match");
			return false;
		}

		getLogger().info("Valid password provided for user '{}'", username);
		return true;
	}

	@Override
	public UserFormData load(UserFormData formData) {
		UserRecord user = get(formData.getUserBox()
				.getUserId()
				.getValue());
		addUserData(formData, user);
		addPersonData(formData, user);
		addRoleData(formData, user);

		return formData;
	}

	private UserRecord addUserData(UserFormData formData, UserRecord user) {
		UserBox box = formData.getUserBox();

		if (user != null) {
			box.getLocale()
					.setValue(user.getLocale());
			formData.getPassword()
					.setValue("");
			formData.getAccountLocked()
					.setValue(!user.getActive());
		} else {
			formData.getAccountLocked()
					.setValue(false);
		}

		return user;
	}

	private void addPersonData(UserFormData formData, UserRecord user) {
		UserBox box = formData.getUserBox();

		if (user != null) {
			PersonRecord person = BEANS.get(PersonService.class)
					.get(user.getPersonId());
			box.getFirstName()
					.setValue(person.getFirstName());
			box.getLastName()
					.setValue(person.getLastName());
		}
	}

	private void addRoleData(UserFormData formData, UserRecord user) {
		final List<String> roles = getRoles(user);
		org.eclipse.scout.orga.shared.user.UserFormData.RoleTable rt = formData.getRoleTable();

		BEANS.get(RoleService.class)
				.getAll()
				.stream()
				.forEach(record -> {
					String role = record.getName();
					String roleTextId = RoleTable.toTextKey(role);
					Locale locale = ServerSession.get()
							.getLocale();
					RoleTableRowData row = rt.addRow();
					row.setId(role);
					row.setRole(TEXTS.getWithFallback(locale, roleTextId, role));
					row.setAssigned(roles.contains(role));
				});
	}

	@Override
	public UserFormData store(UserFormData formData) {
		UserRecord user = toUser(formData);
		PersonRecord person = toPerson(formData, user);
		List<String> roleIds = toRoleIds(formData);

		store(user, person, roleIds);

		return formData;
	}

	private List<String> toRoleIds(UserFormData formData) {
		return Arrays.asList(formData.getRoleTable()
				.getRows())
				.stream()
				.filter(RoleTableRowData::getAssigned)
				.map(RoleTableRowData::getId)
				.collect(Collectors.toList());

	}

	private UserRecord toUser(UserFormData formData) {
		UserBox box = formData.getUserBox();
		String username = box.getUserId()
				.getValue();
		String password = formData.getPassword()
				.getValue();
		UserRecord user = getOrCreate(username);

		user.setLocale(box.getLocale()
				.getValue());

		// only update password if field contains non-empty value
		if (StringUtility.hasText(password)) {
			user.setPasswordEncrypted(PasswordUtility.calculateEncodedPassword(password));
		}
		user.setActive(!formData.getAccountLocked()
				.getValue());

		return user;
	}

	private PersonRecord toPerson(UserFormData formData, UserRecord user) {
		UserBox box = formData.getUserBox();

		PersonRecord person = BEANS.get(PersonService.class)
				.getOrCreate(user.getPersonId());
		person.setFirstName(box.getFirstName()
				.getValue());
		person.setLastName(box.getLastName()
				.getValue());

		// update person id for new users
		if (user.getPersonId() == null) {
			user.setPersonId(person.getId());
		}

		return person;
	}

	@Override
	public UserTablePageData getUserTableData(SearchFilter filter) {
		UserTablePageData pageData = new UserTablePageData();

		getAll().stream()
				.forEach(user -> {
					PersonRecord person = getPerson(user);
					boolean isRoot = getRoles(user).contains(RoleTable.ROOT);
					String username = user.getUsername();

					UserTableRowData row = pageData.addRow();
					row.setUserId(username);
					row.setFirstName(person.getFirstName());
					row.setLastName(person.getLastName());
					row.setIsRoot(isRoot);
					row.setIsLocked(!user.getActive());
				});

		return pageData;
	}

	public List<? extends ILookupRow<String>> getLookupRows(boolean activeOnly) {
		return getAll().stream()
				.filter(user -> !activeOnly || (activeOnly && user.getActive()))
				.map(user -> new LookupRow<>(user.getUsername(), getPersonDisplayName(user)))
				.collect(Collectors.toList());
	}

	private String getPersonDisplayName(UserRecord user) {
		PersonRecord person = getPerson(user);
		String displayName = ObjectUtility.nvl(person.getFirstName(), "");

		if (StringUtility.hasText(person.getLastName())) {
			if (StringUtility.hasText(displayName)) {
				return displayName + " " + person.getLastName();
			}

			return person.getLastName();
		}

		return displayName;
	}

	private PersonRecord getPerson(UserRecord user) {
		return BEANS.get(PersonService.class)
				.get(user.getPersonId());
	}

	@Override
	public ProfileFormData load(ProfileFormData formData) {
		OptionUserBox box = formData.getOptionUserBox();

		UserRecord user = get(box.getUserId()
				.getValue());
		box.getLocale()
				.setValue(user.getLocale());

		PersonRecord person = BEANS.get(PersonService.class)
				.get(user.getPersonId());
		box.getFirstName()
				.setValue(person.getFirstName());
		box.getLastName()
				.setValue(person.getLastName());

		return formData;
	}

	@Override
	public ProfileFormData store(ProfileFormData formData) {
		UserRecord user = updateUser(formData);
		updatePerson(formData, user.getPersonId());

		return formData;
	}

	private UserRecord updateUser(ProfileFormData formData) {
		OptionUserBox box = formData.getOptionUserBox();

		UserRecord user = get(box.getUserId()
				.getValue());
		user.setLocale(box.getLocale()
				.getValue());

		String passwordNew = formData.getNewPassword()
				.getValue();
		if (StringUtility.hasText(passwordNew)) {
			String passwordEncoded = PasswordUtility.calculateEncodedPassword(passwordNew);
			user.setPasswordEncrypted(passwordEncoded);
		}

		store(user.getUsername(), user);

		return user;
	}

	private void updatePerson(ProfileFormData formData, String personId) {
		OptionUserBox box = formData.getOptionUserBox();

		PersonRecord person = BEANS.get(PersonService.class)
				.get(personId);
		person.setFirstName(ObjectUtility.nvl(box.getFirstName()
				.getValue(), ""));
		person.setLastName(ObjectUtility.nvl(box.getLastName()
				.getValue(), ""));

		BEANS.get(PersonService.class)
				.store(person.getId(), person);
	}

	@Override
	public Locale getLocale(String username) {
		if (StringUtility.hasText(username)) {
			UserRecord user = get(username);

			if (user != null) {
				return TextService.convertLocale(user.getLocale());
			}
		}

		return Locale.ROOT;
	}

	public boolean userIsActive(String username) {
		UserRecord user = get(username);

		if (user == null) {
			getLogger().warn("Provided user is is null");
			return false;
		}

		return user.getActive();
	}
}
