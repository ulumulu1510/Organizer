package org.eclipse.scout.orga.client.text;

import java.util.Locale;
import java.util.Map;

import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

import org.eclipse.scout.orga.client.text.TextForm.MainBox.CancelButton;
import org.eclipse.scout.orga.client.text.TextForm.MainBox.OkButton;
import org.eclipse.scout.orga.client.text.TextForm.MainBox.TopBox;
import org.eclipse.scout.orga.client.text.TextForm.MainBox.TopBox.HasTextFilterField;
import org.eclipse.scout.orga.client.text.TextForm.MainBox.TopBox.KeyField;
import org.eclipse.scout.orga.client.text.TextForm.MainBox.TopBox.LocaleFilterField;
import org.eclipse.scout.orga.client.text.TextForm.MainBox.TopBox.TranslationTableField;
import org.eclipse.scout.orga.client.text.TextForm.MainBox.TopBox.TranslationTableField.Table;
import org.eclipse.scout.orga.shared.code.LocaleCodeType;
import org.eclipse.scout.orga.shared.text.ITextService;
import org.eclipse.scout.orga.shared.text.UpdateTranslationPermission;

public class TextForm extends AbstractForm {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Translation");
	}

	public void setKey(String key) {
		getKeyField().setValue(key);
	}

	public String getKey() {
		return getKeyField().getValue();
	}

	public void startModify() {
		startInternalExclusive(new ModifyHandler());
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public TopBox getTopBox() {
		return getFieldByClass(TopBox.class);
	}

	public KeyField getKeyField() {
		return getFieldByClass(KeyField.class);
	}

	public TranslationTableField getTranslationTableField() {
		return getFieldByClass(TranslationTableField.class);
	}

	public LocaleFilterField getLocaleFilterField() {
		return getFieldByClass(LocaleFilterField.class);
	}

	public HasTextFilterField getHasTextFilterField() {
		return getFieldByClass(HasTextFilterField.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	@Order(1000)
	public class MainBox extends AbstractGroupBox {

		@Order(1000)
		public class TopBox extends AbstractGroupBox {

			@Order(10)
			public class KeyField extends AbstractStringField {

				@Override
				protected boolean getConfiguredEnabled() {
					return false;
				}

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("TextKey");
				}

				@Override
				protected int getConfiguredGridW() {
					return 2;
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 128;
				}
			}

			@Order(20)
			public class LocaleFilterField extends AbstractSmartField<String> {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("LocaleFilter");
				}
				
				@Override
				protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
					return LocaleCodeType.class;
				}

				@Override
				protected void execChangedValue() {
					reloadTranslations();
				}
			}

			@Order(30)
			public class HasTextFilterField extends AbstractBooleanField {
				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("HasTextFilter");
				}

				@Override
				protected void execChangedValue() {
					reloadTranslations();
				}
			}

			@Order(40)
			public class TranslationTableField extends AbstractTableField<TranslationTableField.Table> {
				public class Table extends AbstractTable {

					public HasTextColumn getHasTextColumn() {
						return getColumnSet().getColumnByClass(HasTextColumn.class);
					}

					public TranslationColumn getTranslationColumn() {
						return getColumnSet().getColumnByClass(TranslationColumn.class);
					}

					public LocaleColumn getLocaleColumn() {
						return getColumnSet().getColumnByClass(LocaleColumn.class);
					}

					@Order(10)
					public class LocaleColumn extends AbstractSmartColumn<String> {
						@Override
						protected String getConfiguredHeaderText() {
							return TEXTS.get("Language");
						}

						@Override
						protected int getConfiguredWidth() {
							return 200;
						}
						
						@Override
						protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
							return LocaleCodeType.class;
						}
					}

					@Order(20)
					public class TranslationColumn extends AbstractStringColumn {
						@Override
						protected String getConfiguredHeaderText() {
							return TEXTS.get("Translation");
						}

						@Override
						protected int getConfiguredWidth() {
							return 300;
						}

						@Override
						protected boolean getConfiguredEditable() {
							return true;
						}

						@Override
						protected void execCompleteEdit(ITableRow row, IFormField editingField) {
							String value = ((AbstractStringField) editingField).getValue();

							row.getCellForUpdate(getTranslationColumn()).setValue(value);
							row.getCellForUpdate(getHasTextColumn()).setValue(StringUtility.hasText(value));
						}
					}

					@Order(3000)
					public class HasTextColumn extends AbstractBooleanColumn {
						@Override
						protected String getConfiguredHeaderText() {
							return TEXTS.get("HasText");
						}

						@Override
						protected int getConfiguredWidth() {
							return 100;
						}
					}

				}

				@Override
				protected boolean getConfiguredLabelVisible() {
					return false;
				}

				@Override
				protected int getConfiguredGridW() {
					return 2;
				}

				@Override
				protected int getConfiguredGridH() {
					return 6;
				}
			}

		}

		@Order(100000)
		public class OkButton extends AbstractOkButton {
		}

		@Order(101000)
		public class CancelButton extends AbstractCancelButton {
		}
	}

	public class ModifyHandler extends AbstractFormHandler {

		@Override
		protected void execLoad() {
			setEnabledPermission(new UpdateTranslationPermission());
			reloadTranslations();
		}

		@Override
		protected void execStore() {
			saveTranslations();
		}
	}

	private void reloadTranslations() {
		String key = getKey();
		String localeFilter = getLocaleFilterField().getValue();
		boolean textFilter = getHasTextFilterField().getValue();
		Map<String, String> map = BEANS.get(ITextService.class).getTexts(key);

		Table table = getTranslationTableField().getTable();
		table.deleteAllRows();

		CODES.findCodeTypeById(LocaleCodeType.ID)
		.getCodes()
		.stream()
		.forEach(localeCode -> {
			String locale = (String) localeCode.getId();
			boolean addLocale = true;
			boolean hasText = map.containsKey(locale);

			if (localeFilter != null && !localeFilter.contentEquals(Locale.ROOT.toLanguageTag())) {
				if (!locale.startsWith(localeFilter)) {
					addLocale = false;
				}
			}

			if (textFilter && !hasText) {
				addLocale = false;
			}

			if (addLocale) {
				ITableRow row = table.createRow();
				table.getLocaleColumn().setValue(row, locale);
				table.getTranslationColumn().setValue(row, map.get(locale));
				table.getHasTextColumn().setValue(row, hasText);
				table.addRow(row);
			}
		});
	}

	private void saveTranslations() {
		String key = getKey();
		Table table = getTranslationTableField().getTable();
		ITextService service = BEANS.get(ITextService.class); 

		table.getRows()
		.stream()
		.forEach(row -> {
			String locale = (String) row.getKeyValues().get(0);
			String text = (String) row.getCell(table.getTranslationColumn()).getValue();

			if(StringUtility.hasText(text)) { service.addText(key, locale, text); }
			else { service.deleteText(key, locale);	}
		});
		
		service.invalidateCache();
	}
}
