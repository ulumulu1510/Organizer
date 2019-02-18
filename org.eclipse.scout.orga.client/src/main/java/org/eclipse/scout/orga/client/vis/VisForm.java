package org.eclipse.scout.orga.client.vis;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;

import org.eclipse.scout.orga.client.common.AbstractVisField;
import org.eclipse.scout.orga.client.user.UserLookupCall;
import org.eclipse.scout.orga.client.vis.VisForm.MainBox.OptionsBox;
import org.eclipse.scout.orga.client.vis.VisForm.MainBox.OptionsBox.DateFromField;
import org.eclipse.scout.orga.client.vis.VisForm.MainBox.OptionsBox.DateToField;
import org.eclipse.scout.orga.client.vis.VisForm.MainBox.OptionsBox.MonthField;
import org.eclipse.scout.orga.client.vis.VisForm.MainBox.OptionsBox.UserListBox;
import org.eclipse.scout.orga.client.vis.VisForm.MainBox.VisualizationBox;
import org.eclipse.scout.orga.client.vis.VisForm.MainBox.VisualizationBox.VisField;
import org.eclipse.scout.orga.shared.vis.VisFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = VisFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class VisForm extends AbstractForm {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Report");
	}

	@Override
	public void start() {
		startInternal(new PageFormHandler());
	}

	public UserListBox getUserListBox() {
		return getFieldByClass(UserListBox.class);
	}

	public VisField getVisField() {
		return getFieldByClass(VisField.class);
	}

	public VisualizationBox getVisualizationBox() {
		return getFieldByClass(VisualizationBox.class);
	}

	public MonthField getMonthField() {
		return getFieldByClass(MonthField.class);
	}

	public OptionsBox getOptionsBox() {
		return getFieldByClass(OptionsBox.class);
	}

	public DateFromField getDateFromField() {
		return getFieldByClass(DateFromField.class);
	}

	public DateToField getDateToField() {
		return getFieldByClass(DateToField.class);
	}

	@Order(1000)
	public class MainBox extends AbstractGroupBox {

		@Order(1000)
		public class OptionsBox extends AbstractGroupBox {
			@Override
			protected String getConfiguredLabel() {
				return TEXTS.get("Options");
			}

			@Override
			protected int getConfiguredGridH() {
				return 1;
			}

			@Override
			protected int getConfiguredGridW() {
				return 2;
			}

			@Order(0)
			public class MonthField extends AbstractDateField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Month");
				}

				@Override
				protected int getConfiguredGridW() {
					return 2;
				}

				@Override
				protected String getConfiguredDateFormatPattern() {
					return "MMMM";
				}

				@Override
				protected void execChangedValue() {
					Date date = getValue();
					if (date == null) {
						getDateFromField().setValue(null);
						getDateToField().setValue(null);
					} else {
						YearMonth month = YearMonth.from(date.toInstant()
								.atZone(ZoneId.systemDefault())
								.toLocalDate());
						LocalDate start = month.atDay(1);
						LocalDate end = month.atEndOfMonth();

						Instant instantStart = start.atStartOfDay(ZoneId.systemDefault())
								.toInstant();
						Instant instantEnd = end.atStartOfDay(ZoneId.systemDefault())
								.toInstant();

						getDateFromField().resetValue();
						getDateToField().resetValue();

						getDateFromField().setValue(Date.from(instantStart));
						getDateToField().setValue(Date.from(instantEnd));
					}
				}
			}

			@Order(1000)
			@ClassId("d7d287f6-f504-4db3-9b3a-763bdbce88db")
			public class DateFromField extends AbstractDateField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("DateFrom");
				}

				@Override
				protected Date execValidateValue(Date rawValue) {
					Date to = getDateToField().getValue();
					if (to != null && rawValue != null && rawValue.after(to)) {
						throw new VetoException(TEXTS.get("DateFromViolation"));
					}
					return super.execValidateValue(rawValue);
				}

				@Override
				protected void execChangedValue() {
					getDateToField().clearErrorStatus();
					getVisField().setStart(getValue());
				}
			}

			@Order(2000)
			@ClassId("c7526b36-2499-48a7-9621-7ecdbd8da9d9")
			public class DateToField extends AbstractDateField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("DateTo");
				}

				@Override
				protected Date execValidateValue(Date rawValue) {
					Date from = getDateFromField().getValue();
					if (from != null && rawValue != null && rawValue.before(from)) {
						throw new VetoException(TEXTS.get("DateToViolation"));
					}
					return super.execValidateValue(rawValue);
				}

				@Override
				protected void execChangedValue() {
					getDateFromField().clearErrorStatus();
					getVisField().setEnd(getValue());
				}
			}

			@Order(3000)
			public class UserListBox extends AbstractListBox<String> {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Users");
				}

				@Override
				protected int getConfiguredGridH() {
					return 4;
				}

				@Override
				protected int getConfiguredGridW() {
					return 2;
				}

				@Override
				protected double getConfiguredGridWeightY() {
					return 0;
				}

				@Override
				protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
					return UserLookupCall.class;
				}

				@Override
				protected void execChangedValue() {
					getVisField().setUsers(getValue());
				}
			}
		}

		@Order(2000)
		public class VisualizationBox extends AbstractGroupBox {

			@Override
			protected String getConfiguredLabel() {
				return TEXTS.get("Visualization");
			}

			@Order(1000)
			public class VisField extends AbstractVisField {

				@Override
				protected boolean getConfiguredLabelVisible() {
					return false;
				}

				@Override
				protected double getConfiguredGridWeightY() {
					return 1;
				}

				@Override
				protected int getConfiguredGridW() {
					return 2;
				}
			}
		}
	}

	public class PageFormHandler extends AbstractFormHandler {
		@Override
		protected void execPostLoad() {
			getMonthField().setValue(new Date());
		}
	}
}
