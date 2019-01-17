package org.eclipse.scout.orga.client.admin;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import org.eclipse.scout.orga.client.role.PermissionTablePage;
import org.eclipse.scout.orga.client.role.RoleTablePage;
import org.eclipse.scout.orga.client.user.UserTablePage;

public class AccountNodePage extends AbstractPageWithNodes {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("AccountNodePage");
	}

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		pageList.add(new UserTablePage());
		pageList.add(new RoleTablePage());
		pageList.add(new PermissionTablePage());
	}
}
