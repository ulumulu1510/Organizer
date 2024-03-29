package org.eclipse.scout.orga.shared.booking;

import java.util.Date;

import javax.annotation.Generated;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@ClassId("1d16a8e6-667c-4bb7-8729-9110ba359e44-formdata")
@Generated(value = "org.eclipse.scout.orga.client.booking.BookingTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class BookingTablePageData extends AbstractTablePageData {

	private static final long serialVersionUID = 1L;

	@Override
	public BookingTableRowData addRow() {
		return (BookingTableRowData) super.addRow();
	}

	@Override
	public BookingTableRowData addRow(int rowState) {
		return (BookingTableRowData) super.addRow(rowState);
	}

	@Override
	public BookingTableRowData createRow() {
		return new BookingTableRowData();
	}

	@Override
	public Class<? extends AbstractTableRowData> getRowType() {
		return BookingTableRowData.class;
	}

	@Override
	public BookingTableRowData[] getRows() {
		return (BookingTableRowData[]) super.getRows();
	}

	@Override
	public BookingTableRowData rowAt(int index) {
		return (BookingTableRowData) super.rowAt(index);
	}

	public void setRows(BookingTableRowData[] rows) {
		super.setRows(rows);
	}

	public static class BookingTableRowData extends AbstractTableRowData {

		private static final long serialVersionUID = 1L;
		public static final String id = "id";
		public static final String dateFrom = "dateFrom";
		public static final String dateTo = "dateTo";
		public static final String description = "description";
		public static final String note = "note";
		public static final String timeDelta = "timeDelta";
		public static final String active = "active";
		public static final String user = "user";
		private String m_id;
		private Date m_dateFrom;
		private Date m_dateTo;
		private String m_description;
		private String m_note;
		private Date m_timeDelta;
		private Boolean m_active;
		private String m_user;

		public String getId() {
			return m_id;
		}

		public void setId(String newId) {
			m_id = newId;
		}

		public Date getDateFrom() {
			return m_dateFrom;
		}

		public void setDateFrom(Date newDateFrom) {
			m_dateFrom = newDateFrom;
		}

		public Date getDateTo() {
			return m_dateTo;
		}

		public void setDateTo(Date newDateTo) {
			m_dateTo = newDateTo;
		}

		public String getDescription() {
			return m_description;
		}

		public void setDescription(String newDescription) {
			m_description = newDescription;
		}

		public String getNote() {
			return m_note;
		}

		public void setNote(String newNote) {
			m_note = newNote;
		}

		public Date getTimeDelta() {
			return m_timeDelta;
		}

		public void setTimeDelta(Date newTimeDelta) {
			m_timeDelta = newTimeDelta;
		}

		public Boolean getActive() {
			return m_active;
		}

		public void setActive(Boolean newActive) {
			m_active = newActive;
		}

		public String getUser() {
			return m_user;
		}

		public void setUser(String newUser) {
			m_user = newUser;
		}
	}
}
