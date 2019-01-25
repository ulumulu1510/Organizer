package org.eclipse.scout.orga.client.common;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.IForm;

public class FormPage<T extends AbstractForm> extends AbstractPageWithNodes {
	  private final Class<T> formClass;
	  private final String text;

	  public FormPage(Class<T> calendarFormClass, String text) {
	    super(false, calendarFormClass.getName());
	    this.formClass = calendarFormClass;
	    this.text = text;
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
	    return formClass;
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
	  public AbstractForm getDetailForm() {
	    return (AbstractForm) super.getDetailForm();
	  }

	  @Override
	  protected void startDetailForm() {
	    getDetailForm().start();
	  }
}
