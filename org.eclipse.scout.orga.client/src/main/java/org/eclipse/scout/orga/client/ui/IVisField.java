package org.eclipse.scout.orga.client.ui;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.scout.orga.shared.vis.VisItem;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;

public interface IVisField extends IFormField {

	String PROP_USERS = "users";
	String PROP_START = "start";
	String PROP_END = "end";

	Map<String, List<VisItem>> getPropertyUsers();
	void setPropertyUsers(Map<String, List<VisItem>> users);

	Date getPropertyStart();
	void setPropertyStart(Date start);

	Date getPropertyEnd();
	void setPropertyEnd(Date end);

}
