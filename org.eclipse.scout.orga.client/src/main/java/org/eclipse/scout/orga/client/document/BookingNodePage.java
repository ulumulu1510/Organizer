package org.eclipse.scout.orga.client.document;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("37c402c4-165e-460c-b6e5-6215a8fb8909")
public class BookingNodePage extends AbstractPageWithNodes {

	private String bookingId;

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		DocumentTablePage documentTablePage = new DocumentTablePage();
		documentTablePage.setPaymentId(getBookingId());
		pageList.add(documentTablePage);
	}

}
