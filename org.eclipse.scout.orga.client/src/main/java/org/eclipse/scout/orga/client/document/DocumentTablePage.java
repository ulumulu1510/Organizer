package org.eclipse.scout.orga.client.document;

import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import org.eclipse.scout.orga.client.document.DocumentTablePage.Table;
import org.eclipse.scout.orga.shared.booking.BookingFormData.DocumentTable;
import org.eclipse.scout.orga.shared.document.DocumentTablePageData;
import org.eclipse.scout.orga.shared.document.IDocumentService;

@Data(DocumentTablePageData.class)
public class DocumentTablePage extends AbstractPageWithTable<Table> {

	private String bookingId;

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("DocumentTablePage");
	}

	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	@Override
	protected void execLoadData(SearchFilter filter) {
		importPageData(BEANS.get(IDocumentService.class)
				.getDocumentTableData(filter, getBookingId()));
	}

	public class Table extends AbstractDocumentTable {
		@Override
		public IOutline getPageOutline() {
			return getOutline();
		}

		@Override
		protected void importFieldData(DocumentTable documentTable) {
			// Not needed, as we do that on page reload
		}

		@Override
		protected void reloadTableData() {
			reloadPage();
		}

		@Override
		protected String getBookingId() {
			return DocumentTablePage.this.getBookingId();
		}
	}
}
