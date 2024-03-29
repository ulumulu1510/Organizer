package org.eclipse.scout.orga.client.admin;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import org.eclipse.scout.orga.client.code.ApplicationCodeTablePage;
import org.eclipse.scout.orga.shared.code.FileCodeType;
import org.eclipse.scout.orga.shared.code.LocaleCodeType;
import org.eclipse.scout.orga.shared.code.SexCodeType;

public class CodeNodePage extends AbstractPageWithNodes {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("CodeNodePage");
	}

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		pageList.add(new ApplicationCodeTablePage(LocaleCodeType.class));
		pageList.add(new ApplicationCodeTablePage(FileCodeType.class));
		pageList.add(new ApplicationCodeTablePage(SexCodeType.class));
	}
}
