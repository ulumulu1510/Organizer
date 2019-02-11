package org.eclipse.scout.orga.server.document;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.service.IService;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.scout.orga.database.or.core.tables.BookingDocument;
import org.eclipse.scout.orga.database.or.core.tables.records.BookingDocumentRecord;
import org.eclipse.scout.orga.server.common.AbstractBaseService;
import org.eclipse.scout.orga.shared.document.IDocumentService;

public class BookingDocumentService extends AbstractBaseService<BookingDocument, BookingDocumentRecord>
		implements IService {

	@Override
	public BookingDocument getTable() {
		return BookingDocument.BOOKING_DOCUMENT;
	}

	@Override
	public Field<String> getIdColumn() {
		return BookingDocument.BOOKING_DOCUMENT.BOOKING_ID;
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(BookingDocumentService.class);
	}

	@Override
	public int delete(String id) {
		List<BookingDocumentRecord> records = getContext().selectFrom(getTable())
				.where(getIdColumn().eq(id))
				.fetchStream()
				.collect(Collectors.toList());
		int delete = super.delete(id);
		if (delete > 0) {
			IDocumentService service = BEANS.get(IDocumentService.class);
			records.stream()
					.map(BookingDocumentRecord::getDocumentId)
					.forEach(service::delete);
		}
		return delete;
	}

}
