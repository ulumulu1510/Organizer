package org.eclipse.scout.orga.client.common;

import java.io.File;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.IAction;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.docx4j.client.ScoutXlsxSpreadsheetAdapter;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.scout.orga.shared.FontAwesomeIcons;

public abstract class AbstractExportableTable extends AbstractTable {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(AbstractExportableTable.class);

	// TODO check if there is a better way to do this (ie. extending classes not
	// having to implement getPageOutline...
	abstract public IOutline getPageOutline();

	@Order(100000)
	public class ExportToExcelMenu extends AbstractMenu {

		@Override
		protected String getConfiguredIconId() {
			return FontAwesomeIcons.fa_fileExcelO;
		}

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("ExportToExcel");
		}

		@Override
		protected String getConfiguredTooltipText() {
			return TEXTS.get("ExportToExcel");
		}

		@Override
		protected boolean getConfiguredVisible() {
			return getPageOutline() != null;
		}

		@Override
		protected byte getConfiguredHorizontalAlignment() {
			return IAction.HORIZONTAL_ALIGNMENT_RIGHT;
		}

		@Override
		protected Set<? extends IMenuType> getConfiguredMenuTypes() {
			return CollectionUtility.hashSet(TableMenuType.EmptySpace, TableMenuType.SingleSelection,
					TableMenuType.MultiSelection);
		}

		@Override
		protected void execAction() {
			if (pageIsActive()) {
				File xlsx = createExcelFile();
				DownloadUtility.download(xlsx);
			}
		}

		private boolean pageIsActive() {
			return getPageOutline() != null && getPageOutline().getActivePage() != null;
		}

		private File createExcelFile() {
			ScoutXlsxSpreadsheetAdapter s = new ScoutXlsxSpreadsheetAdapter();
			return s.exportPage(null, 0, 0, getPageOutline().getActivePage());
		}
	}
}
