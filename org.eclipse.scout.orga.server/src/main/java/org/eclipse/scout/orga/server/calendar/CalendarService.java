package org.eclipse.scout.orga.server.calendar;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.scout.orga.database.or.core.tables.records.BookingRecord;
import org.eclipse.scout.orga.server.booking.BookingService;
import org.eclipse.scout.orga.shared.calendar.ICalendarService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarAppointment;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarItem;

public class CalendarService implements ICalendarService {

	@Override
	public Set<ICalendarItem> loadPaymentsInInterval(Date minDate, Date maxDate) {
		return BEANS.get(BookingService.class).getBookingsInInterval(minDate, maxDate)
				.map(this::paymentToCalenderItem)
				.collect(Collectors.toSet());
	}

	private ICalendarItem paymentToCalenderItem(BookingRecord booking) {
		CalendarAppointment calendarAppointment = new CalendarAppointment(
				booking.getId(),
				booking.getUserId(),
				booking.getDateFrom(),
				booking.getDateTo(),
				false,
				StringUtility.join(" ", booking.getDescription()) ,
				StringUtility.join(" | ", booking.getNote(), booking.getUserId()),
				"calendar-appointment");
		calendarAppointment.setOwner(booking.getUserId());
		return calendarAppointment;
	}

}
