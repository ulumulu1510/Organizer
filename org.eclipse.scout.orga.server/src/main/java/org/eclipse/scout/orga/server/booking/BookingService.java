package org.eclipse.scout.orga.server.booking;

import java.util.Date;
import java.util.stream.Stream;

import org.eclipse.scout.orga.database.or.core.tables.Booking;
import org.eclipse.scout.orga.database.or.core.tables.records.BookingRecord;
import org.eclipse.scout.orga.database.table.TableUtility;
import org.eclipse.scout.orga.server.ServerSession;
import org.eclipse.scout.orga.server.common.AbstractBaseService;
import org.eclipse.scout.orga.server.document.BookingDocumentService;
import org.eclipse.scout.orga.shared.booking.BookingFormData;
import org.eclipse.scout.orga.shared.booking.BookingTablePageData;
import org.eclipse.scout.orga.shared.booking.BookingTablePageData.BookingTableRowData;
import org.eclipse.scout.orga.shared.booking.IBookingService;
import org.eclipse.scout.orga.shared.document.IDocumentService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookingService extends AbstractBaseService<Booking, BookingRecord>  implements IBookingService {

	@Override
	public Booking getTable() {
		return Booking.BOOKING;
	}

	@Override
	public Field<String> getIdColumn() {
		return Booking.BOOKING.ID;
	}


	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(BookingService.class);
	}

	@Override
	public BookingTablePageData getBookingTableData(SearchFilter filter) {
		BookingTablePageData pageData = new BookingTablePageData();

		getAll()
		.stream()
		.forEach(booking -> recordToRowData(pageData.addRow(), booking));

		return pageData;
	}

	@Override
	public BookingFormData load(BookingFormData formData) {
		BookingRecord booking = getOrCreate(formData.getBookingId().getValue());
        recordToFormData(formData, booking);
        formData.getDocumentTable().setRows(BEANS.get(IDocumentService.class).getDocumentTableData(booking.getId()).getRows());
        return formData;
	}

	@Override
	public BookingFormData store(BookingFormData formData) {
		String bookingId = formData.getBookingId().getValue();
		BookingRecord booking = getOrCreate(bookingId);
		formDataToRecord(formData, booking);
        store(booking.getId(), booking);
        return formData;
	}

	@Override
	public int delete(String id) {
		int delete = super.delete(id);
		if (delete > 0) {
			BEANS.get(BookingDocumentService.class).delete(id);
		}
		return delete;
	}


	public BookingRecord getOrCreate(String bookingId) {
		BookingRecord booking = get(bookingId);

        if (booking != null) {
            return booking;
        }

        booking = new BookingRecord();
        booking.setId(TableUtility.createId());
        booking.setActive(true);
        booking.setUserId(ServerSession.get().getUserId());

        return booking;
    }

	private void formDataToRecord(BookingFormData formData, BookingRecord booking) {
		if (booking != null && formData != null) {
			booking.setDescription(formData.getDescription().getValue());
			booking.setDateFrom(formData.getDateFrom().getValue());
			booking.setDateTo(formData.getDateTo().getValue());
			booking.setNote(formData.getNote().getValue());
			booking.setUserId(formData.getUserId().getValue());
			booking.setActive(formData.getActive().getValue());
		}
	}

	private void recordToFormData(BookingFormData formData, BookingRecord booking) {
		if (booking != null && formData != null) {
			formData.getBookingId().setValue(booking.getId());
			formData.getDescription().setValue(booking.getDescription());
			formData.getDateFrom().setValue(booking.getDateFrom());
			formData.getDateTo().setValue(booking.getDateTo());
			formData.getNote().setValue(booking.getNote());
			formData.getUserId().setValue(booking.getUserId());
			formData.getActive().setValue(booking.getActive());
		}
	}

	private void recordToRowData(BookingTableRowData row, BookingRecord booking) {
		row.setId(booking.getId());
		row.setDescription(booking.getDescription());
		row.setDateFrom(booking.getDateFrom());
		row.setDateTo(booking.getDateTo());
		if (booking.getDateFrom() != null && booking.getDateTo() != null) {
			long timeDelta = booking.getDateTo().getTime() - booking.getDateFrom().getTime();
			row.setTimeDelta(new Date(timeDelta));
		}
		row.setNote(booking.getNote());
		row.setUser(booking.getUserId());
		row.setActive(booking.getActive());
	}

	public Stream<BookingRecord> getBookingsInInterval(Date minDate, Date maxDate) {
		return getContext()
				.selectFrom(getTable())
				.where(
						getTable().DATE_FROM.gt(minDate),
						getTable().DATE_TO.lt(maxDate))
				.fetchStream();
	}

}
