package org.eclipse.scout.orga.client.work;

import java.util.List;

import org.eclipse.scout.orga.client.booking.BookingTablePage;
import org.eclipse.scout.orga.client.calendar.CalendarForm;
import org.eclipse.scout.orga.client.common.FormPage;
import org.eclipse.scout.orga.client.vis.VisForm;
import org.eclipse.scout.orga.shared.Icons;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

/**
 * <h3>{@link WorkOutline}</h3>
 */
@Order(1000)
public class WorkOutline extends AbstractOutline {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Work");
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.Pencil;
	}

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		super.execCreateChildPages(pageList);

		pageList.add(new FormPage<>(CalendarForm.class, TEXTS.get("Calendar")));
		pageList.add(new FormPage<>(VisForm.class, TEXTS.get("Report")));
		pageList.add(new BookingTablePage());
	}
}
