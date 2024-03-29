package org.eclipse.scout.orga.shared.vis;

import java.util.Date;
import java.util.Set;

import javax.annotation.Generated;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "org.eclipse.scout.orga.client.vis.VisForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class VisFormData extends AbstractFormData {

	private static final long serialVersionUID = 1L;

	public DateFrom getDateFrom() {
		return getFieldByClass(DateFrom.class);
	}

	public DateTo getDateTo() {
		return getFieldByClass(DateTo.class);
	}

	public Month getMonth() {
		return getFieldByClass(Month.class);
	}

	public UserListBox getUserListBox() {
		return getFieldByClass(UserListBox.class);
	}

	@ClassId("d7d287f6-f504-4db3-9b3a-763bdbce88db-formdata")
	public static class DateFrom extends AbstractValueFieldData<Date> {

		private static final long serialVersionUID = 1L;
	}

	@ClassId("c7526b36-2499-48a7-9621-7ecdbd8da9d9-formdata")
	public static class DateTo extends AbstractValueFieldData<Date> {

		private static final long serialVersionUID = 1L;
	}

	public static class Month extends AbstractValueFieldData<Date> {

		private static final long serialVersionUID = 1L;
	}

	public static class UserListBox extends AbstractValueFieldData<Set<String>> {

		private static final long serialVersionUID = 1L;
	}
}
