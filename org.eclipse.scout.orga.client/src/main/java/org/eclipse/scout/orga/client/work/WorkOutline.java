package org.eclipse.scout.orga.client.work;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import org.eclipse.scout.orga.client.booking.BookingTablePage;
import org.eclipse.scout.orga.client.calendar.CalendarPage;
import org.eclipse.scout.orga.shared.Icons;

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

		pageList.add(new CalendarPage());
		pageList.add(new BookingTablePage());
	}
}
