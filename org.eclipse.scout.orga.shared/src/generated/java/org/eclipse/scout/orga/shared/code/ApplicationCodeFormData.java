package org.eclipse.scout.orga.shared.code;

import java.math.BigDecimal;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "org.eclipse.scout.orga.client.code.ApplicationCodeForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class ApplicationCodeFormData extends AbstractFormData {

	private static final long serialVersionUID = 1L;

	public Active getActive() {
		return getFieldByClass(Active.class);
	}

	public CodeId getCodeId() {
		return getFieldByClass(CodeId.class);
	}

	public CodeText getCodeText() {
		return getFieldByClass(CodeText.class);
	}

	/**
	 * access method for property CodeTypeId.
	 */
	public String getCodeTypeId() {
		return getCodeTypeIdProperty().getValue();
	}

	/**
	 * access method for property CodeTypeId.
	 */
	public void setCodeTypeId(String codeTypeId) {
		getCodeTypeIdProperty().setValue(codeTypeId);
	}

	public CodeTypeIdProperty getCodeTypeIdProperty() {
		return getPropertyByClass(CodeTypeIdProperty.class);
	}

	public Order getOrder() {
		return getFieldByClass(Order.class);
	}

	public static class Active extends AbstractValueFieldData<Boolean> {

		private static final long serialVersionUID = 1L;
	}

	public static class CodeId extends AbstractValueFieldData<String> {

		private static final long serialVersionUID = 1L;
	}

	public static class CodeText extends AbstractValueFieldData<String> {

		private static final long serialVersionUID = 1L;
	}

	public static class CodeTypeIdProperty extends AbstractPropertyData<String> {

		private static final long serialVersionUID = 1L;
	}

	public static class Order extends AbstractValueFieldData<BigDecimal> {

		private static final long serialVersionUID = 1L;
	}
}
