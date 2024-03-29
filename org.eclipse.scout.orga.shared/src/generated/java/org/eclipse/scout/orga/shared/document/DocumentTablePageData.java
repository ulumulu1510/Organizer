package org.eclipse.scout.orga.shared.document;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "org.eclipse.scout.orga.client.document.DocumentTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class DocumentTablePageData extends AbstractTablePageData {

	private static final long serialVersionUID = 1L;

	@Override
	public DocumentTableRowData addRow() {
		return (DocumentTableRowData) super.addRow();
	}

	@Override
	public DocumentTableRowData addRow(int rowState) {
		return (DocumentTableRowData) super.addRow(rowState);
	}

	@Override
	public DocumentTableRowData createRow() {
		return new DocumentTableRowData();
	}

	@Override
	public Class<? extends AbstractTableRowData> getRowType() {
		return DocumentTableRowData.class;
	}

	@Override
	public DocumentTableRowData[] getRows() {
		return (DocumentTableRowData[]) super.getRows();
	}

	@Override
	public DocumentTableRowData rowAt(int index) {
		return (DocumentTableRowData) super.rowAt(index);
	}

	public void setRows(DocumentTableRowData[] rows) {
		super.setRows(rows);
	}

	public static class DocumentTableRowData extends AbstractTableRowData {

		private static final long serialVersionUID = 1L;
		public static final String id = "id";
		public static final String name = "name";
		public static final String type = "type";
		public static final String size = "size";
		public static final String user = "user";
		public static final String uploaded = "uploaded";
		public static final String active = "active";
		private String m_id;
		private String m_name;
		private String m_type;
		private BigInteger m_size;
		private String m_user;
		private Date m_uploaded;
		private Boolean m_active;

		public String getId() {
			return m_id;
		}

		public void setId(String newId) {
			m_id = newId;
		}

		public String getName() {
			return m_name;
		}

		public void setName(String newName) {
			m_name = newName;
		}

		public String getType() {
			return m_type;
		}

		public void setType(String newType) {
			m_type = newType;
		}

		public BigInteger getSize() {
			return m_size;
		}

		public void setSize(BigInteger newSize) {
			m_size = newSize;
		}

		public String getUser() {
			return m_user;
		}

		public void setUser(String newUser) {
			m_user = newUser;
		}

		public Date getUploaded() {
			return m_uploaded;
		}

		public void setUploaded(Date newUploaded) {
			m_uploaded = newUploaded;
		}

		public Boolean getActive() {
			return m_active;
		}

		public void setActive(Boolean newActive) {
			m_active = newActive;
		}
	}
}
