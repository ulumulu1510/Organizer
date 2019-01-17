package org.eclipse.scout.orga.client.calendar;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;

public class CalendarPage extends AbstractPageWithNodes {

	  private final Class<CalendarForm> calendarFormClass;
	  private final String text;

	  public CalendarPage() {
	    super(false, CalendarForm.class.getName());
	    calendarFormClass = CalendarForm.class;
	    text = "Calendar";
	    callInitializer();
	  }


	  public String getText() {
	    return text;
	  }

	  @Override
	  protected boolean getConfiguredLeaf() {
	    return true;
	  }

	  @Override
	  protected Class<? extends IForm> getConfiguredDetailForm() {
	    return calendarFormClass;
	  }

	  @Override
	  protected void execInitTreeNode() {
	    if (getCellForUpdate().getText() == null) {
	      getCellForUpdate().setText(text);
	    }
	  }

	  @Override
	  protected void execInitPage() {
	    setTableVisible(false);
	  }

	  @Override
	  public CalendarForm getDetailForm() {
	    return (CalendarForm) super.getDetailForm();
	  }

	  @Override
	  protected void startDetailForm() {
	    getDetailForm().start();
	  }
}
