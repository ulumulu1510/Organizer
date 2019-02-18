package org.eclipse.scout.orga.server.document;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.scout.orga.database.or.core.tables.BookingDocument;
import org.eclipse.scout.orga.database.or.core.tables.Document;
import org.eclipse.scout.orga.database.or.core.tables.records.BookingDocumentRecord;
import org.eclipse.scout.orga.database.or.core.tables.records.DocumentRecord;
import org.eclipse.scout.orga.database.table.TableUtility;
import org.eclipse.scout.orga.server.common.AbstractBaseService;
import org.eclipse.scout.orga.shared.booking.BookingFormData.DocumentTable;
import org.eclipse.scout.orga.shared.code.FileCodeType;
import org.eclipse.scout.orga.shared.common.DateTimeUtility;
import org.eclipse.scout.orga.shared.document.DocumentTablePageData;
import org.eclipse.scout.orga.shared.document.DocumentTablePageData.DocumentTableRowData;
import org.eclipse.scout.orga.shared.document.IDocumentService;

public class DocumentService extends AbstractBaseService<Document, DocumentRecord> implements IDocumentService {

	@Override
	public Document getTable() {
		return Document.DOCUMENT;
	}

	@Override
	public Field<String> getIdColumn() {
		return Document.DOCUMENT.ID;
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(DocumentService.class);
	}

	@Override
	public BinaryResource getResource(String documentId) {
		DocumentRecord document = get(documentId);
		return new BinaryResource(document.getName(), document.getContent());
	}

	@Override
	public DocumentTablePageData getDocumentTableData(SearchFilter filter, String bookingId) {
		DocumentTablePageData pageData = new DocumentTablePageData();

		forEachDocumentWithBookingId(bookingId, document -> {
			DocumentTableRowData row = pageData.addRow();
			row.setId(document.getId());
			row.setName(document.getName());
			row.setType(document.getType());
			row.setSize(document.getSize()
					.toBigInteger());
			row.setUser(document.getUserId());
			row.setUploaded(DateTimeUtility.convertToDate(document.getUploaded()));
			row.setActive(document.getActive());
		});

		return pageData;
	}

	@Override
	public void store(String name, byte[] content, String userId, String bookingId) {
		String documentId = TableUtility.createId();
		String bookingDocumentId = TableUtility.createId();
		String type = getDocumentType(name);
		BigDecimal size = BigDecimal.valueOf(content.length);
		String uploaded = DateTimeUtility.nowInUtcAsString();
		DocumentRecord document = new DocumentRecord(documentId, name, type, size, content, userId, uploaded, true);

		store(documentId, document);
		BEANS.get(BookingDocumentService.class).store(bookingDocumentId, new BookingDocumentRecord(bookingDocumentId, bookingId, documentId));
	}

	private String getDocumentType(String name) {
		String type = IOUtility.getFileExtension(name);
		return BEANS.get(FileCodeType.class)
				.getCode(type) != null ? type : FileCodeType.UknownCode.ID;
	}

	@Override
	public List<? extends ILookupRow<String>> getLookupRows(boolean activeOnly) {
		return getAll().stream()
				.filter(document -> !activeOnly || (activeOnly && document.getActive()))
				.map(document -> new LookupRow<>(document.getId(), document.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public DocumentTable getDocumentTableData(String bookingId) {
		DocumentTable table = new DocumentTable();

		forEachDocumentWithBookingId(bookingId, document -> {
			org.eclipse.scout.orga.shared.booking.BookingFormData.DocumentTable.DocumentTableRowData row = table
					.addRow();
			row.setId(document.getId());
			row.setName(document.getName());
			row.setType(document.getType());
			row.setSize(document.getSize()
					.toBigInteger());
			row.setUser(document.getUserId());
			row.setUploaded(DateTimeUtility.convertToDate(document.getUploaded()));
			row.setActive(document.getActive());
		});

		return table;
	}

	private void forEachDocumentWithBookingId(String bookingId, Consumer<? super DocumentRecord> consumer) {
		getContext()
		.selectFrom(getTable())
		.where(getIdColumn().in(
				getContext()
        		.select(BookingDocument.BOOKING_DOCUMENT.DOCUMENT_ID)
        		.from(BookingDocument.BOOKING_DOCUMENT)
        		.where(BookingDocument.BOOKING_DOCUMENT.BOOKING_ID.eq(bookingId))
        		))
		.stream()
		.forEach(consumer);
	}
}
