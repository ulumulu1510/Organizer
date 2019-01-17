package org.eclipse.scout.orga.shared.role;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "org.eclipse.scout.orga.client.role.RoleForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class RoleFormData extends AbstractFormData {

	private static final long serialVersionUID = 1L;

	public PermissionTable getPermissionTable() {
		return getFieldByClass(PermissionTable.class);
	}

	public RoleId getRoleId() {
		return getFieldByClass(RoleId.class);
	}

	public static class PermissionTable extends AbstractTableFieldBeanData {

		private static final long serialVersionUID = 1L;

		@Override
		public PermissionTableRowData addRow() {
			return (PermissionTableRowData) super.addRow();
		}

		@Override
		public PermissionTableRowData addRow(int rowState) {
			return (PermissionTableRowData) super.addRow(rowState);
		}

		@Override
		public PermissionTableRowData createRow() {
			return new PermissionTableRowData();
		}

		@Override
		public Class<? extends AbstractTableRowData> getRowType() {
			return PermissionTableRowData.class;
		}

		@Override
		public PermissionTableRowData[] getRows() {
			return (PermissionTableRowData[]) super.getRows();
		}

		@Override
		public PermissionTableRowData rowAt(int index) {
			return (PermissionTableRowData) super.rowAt(index);
		}

		public void setRows(PermissionTableRowData[] rows) {
			super.setRows(rows);
		}

		public static class PermissionTableRowData extends AbstractTableRowData {

			private static final long serialVersionUID = 1L;
			public static final String id = "id";
			public static final String group = "group";
			public static final String text = "text";
			public static final String assigned = "assigned";
			private String m_id;
			private String m_group;
			private String m_text;
			private Boolean m_assigned;

			public String getId() {
				return m_id;
			}

			public void setId(String newId) {
				m_id = newId;
			}

			public String getGroup() {
				return m_group;
			}

			public void setGroup(String newGroup) {
				m_group = newGroup;
			}

			public String getText() {
				return m_text;
			}

			public void setText(String newText) {
				m_text = newText;
			}

			public Boolean getAssigned() {
				return m_assigned;
			}

			public void setAssigned(Boolean newAssigned) {
				m_assigned = newAssigned;
			}
		}
	}

	public static class RoleId extends AbstractValueFieldData<String> {

		private static final long serialVersionUID = 1L;
	}
}