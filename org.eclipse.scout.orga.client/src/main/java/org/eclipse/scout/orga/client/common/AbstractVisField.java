package org.eclipse.scout.orga.client.common;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.scout.orga.client.ui.IVisField;
import org.eclipse.scout.orga.shared.vis.IVisService;
import org.eclipse.scout.orga.shared.vis.VisItem;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.date.DateUtility;

@ClassId("c23f7097-9760-49dd-9e39-172d54b67029")
public abstract class AbstractVisField extends AbstractFormField implements IVisField {

	/**
	 * vis.js has a nasty habit of displaying only the days which are really needed.
	 * But sometimes, you want to display time that goes just up to that date.
	 * So we increase the end and decrease the start date by this delta.
	 */
	private static final double DAYS_DELTA_VIS = 1.0;

	private Date start;
	private Date end;
	private Collection<String> users;

	public AbstractVisField() {
		this(true);
	}

	public AbstractVisField(boolean callInitializer) {
		super(callInitializer);
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
		this.setPropertyStart(start);
		calculateAndSetPropertyUsers();
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
		this.setPropertyEnd(end);
		calculateAndSetPropertyUsers();
	}

	public Collection<String> getUsers() {
		return users;
	}

	public void setUsers(Collection<String> users) {
		this.users = users;
		calculateAndSetPropertyUsers();
	}

	private void calculateAndSetPropertyUsers() {
		setPropertyUsers(calculatPropertyUsers());
	}

	private Map<String, List<VisItem>> calculatPropertyUsers() {
		Map<String, List<VisItem>> usersProperty = null;
		if (readyToCalculateUsers()) {
			usersProperty = BEANS.get(IVisService.class).calculateUserItems(users, start, end);
		}
		return usersProperty;
	}

	private boolean readyToCalculateUsers() {
		return this.start != null && this.end != null && !CollectionUtility.isEmpty(this.users);
	}

	@Override
	public Date getPropertyStart() {
		return (Date) propertySupport.getProperty(PROP_START);
	}

	@Override
	public void setPropertyStart(Date start) {
		start = DateUtility.addDays(start, -DAYS_DELTA_VIS);
		propertySupport.setProperty(PROP_START, start);
	}

	@Override
	public Date getPropertyEnd() {
		return (Date) propertySupport.getProperty(PROP_END);
	}

	@Override
	public void setPropertyEnd(Date end) {
		end = DateUtility.addDays(end, DAYS_DELTA_VIS);
		propertySupport.setProperty(PROP_END, end);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<VisItem>> getPropertyUsers() {
		return (Map<String, List<VisItem>>) propertySupport.getProperty(PROP_USERS);
	}

	@Override
	public void setPropertyUsers(Map<String, List<VisItem>> users) {
		propertySupport.setProperty(PROP_USERS, users);
	}
}
