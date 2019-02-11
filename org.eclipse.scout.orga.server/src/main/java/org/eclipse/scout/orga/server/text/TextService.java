package org.eclipse.scout.orga.server.text;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.scout.orga.database.or.core.tables.Text;
import org.eclipse.scout.orga.database.or.core.tables.records.TextRecord;
import org.eclipse.scout.orga.database.table.TextTable;
import org.eclipse.scout.orga.server.common.AbstractBaseService;
import org.eclipse.scout.orga.shared.text.ITextService;
import org.eclipse.scout.orga.shared.text.TextTablePageData;
import org.eclipse.scout.orga.shared.text.TextTablePageData.TextTableRowData;

public class TextService extends AbstractBaseService<Text, TextRecord> implements ITextService {

	public static final String TEXT_LOCALE_UNDEFINED = "[Undefined]";
	public static final String LOCALE_DEFAULT = TextTable.LOCALE_DEFAULT;
	private static final String ID_SEPARATOR = ":";

	@Override
	public Text getTable() {
		return Text.TEXT;
	}

	@Override
	public Field<String> getIdColumn() {
		return Text.TEXT.KEY;
	}

	protected Field<String> getLocaleColumn() {
		return Text.TEXT.LOCALE;
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(TextService.class);
	}

	@Override
	public boolean exists(String id) {
		DSLContext context = getContext();
		return exists(context, id);
	}

	private boolean exists(DSLContext context, String id) {
		String key = toKey(id);
		String locale = toLocale(id);

		return context.fetchExists(context.select()
				.from(getTable())
				.where(getTable().KEY.eq(key)
						.and(getTable().LOCALE.eq(locale))));
	}

	@Override
	public TextRecord get(String id) {
		String key = toKey(id);
		String locale = toLocale(id);

		return get(key, locale);
	}

	public List<TextRecord> getAll(String key) {
		return getContext().selectFrom(getTable())
				.where(getTable().KEY.eq(key))
				.stream()
				.collect(Collectors.toList());
	}

	/**
	 * Stores/updates the provided code record.
	 */
	public void store(TextRecord record) {
		String id = TextService.toId(record.getLocale(), record.getKey());
		store(id, record);
	}

	@Override
	public void store(String id, TextRecord record) {
		DSLContext context = getContext();
		if (exists(context, id)) {
			context.update(getTable())
					.set(record)
					.where(getIdColumn().eq(toKey(id)))
					.and(getLocaleColumn().eq(toLocale(id)))
					.execute();
		} else {
			context.insertInto(getTable())
					.set(record)
					.execute();
		}
	}

	public String getText(String key, String locale) {
		TextRecord record = get(key, locale);

		if (record == null && !LOCALE_DEFAULT.equals(locale)) {
			record = get(key, LOCALE_DEFAULT);
		}

		if (record == null) {
			return "missing-text-key:" + key;
		}

		return record.getText();
	}

	private TextRecord get(String key, String locale) {
		TextRecord text = getContext().selectFrom(getTable())
				.where(getTable().KEY.eq(key)
						.and(getTable().LOCALE.eq(locale)))
				.fetchOne();
		return text;
	}

	public static String toId(String locale, String key) {
		if (key == null) {
			key = "";
		}

		if (locale == null) {
			return String.format("%s%s%s", LOCALE_DEFAULT, ID_SEPARATOR, key);
		} else {
			return String.format("%s%s%s", locale, ID_SEPARATOR, key);
		}
	}

	public static String toKey(String id) {
		if (!idIsValid(id)) {
			return null;
		}

		return id.substring(id.indexOf(ID_SEPARATOR) + 1);
	}

	public static String toLocale(String id) {
		if (!idIsValid(id)) {
			return null;
		}

		return id.substring(0, id.indexOf(ID_SEPARATOR));
	}

	/**
	 * Returns the string representation for the provided locale. For a null
	 * value the method returns value {@link TextService#LOCALE_DEFAULT}.
	 */
	public static String convertLocale(Locale locale) {
		if (locale == null) {
			return LOCALE_DEFAULT;
		}

		return locale.toLanguageTag();
	}

	public static Locale convertLocale(String locale) {
		if (locale == null) {
			return Locale.forLanguageTag(LOCALE_DEFAULT);
		}

		return Locale.forLanguageTag(locale);
	}

	/**
	 * Returns true iff the provided id is a correct representation of a
	 * locale-key pair.
	 */
	private static boolean idIsValid(String id) {
		if (id == null) {
			return false;
		}

		int separatorIndex = id.indexOf(ID_SEPARATOR);

		if (separatorIndex <= 0) {
			return false;
		}

		if (separatorIndex + 1 >= id.length()) {
			return false;
		}

		return true;
	}

	@Override
	public Map<String, String> getTexts(String key) {
		Map<String, String> texts = new HashMap<>();

		getContext().selectFrom(getTable())
				.where(getTable().KEY.eq(key))
				.fetchStream()
				.forEach(text -> {
					texts.put(text.getLocale(), text.getText());
				});

		return texts;
	}

	@Override
	public void addText(String key, String locale, String text) {
		store(toId(locale, key), new TextRecord(key, locale, text));
	}

	@Override
	public void deleteText(String key, String locale) {
		getContext().deleteFrom(getTable())
				.where(getTable().KEY.eq(key)
						.and(getTable().LOCALE.eq(locale)))
				.execute();
	}

	@Override
	public void invalidateCache() {
		BEANS.get(TextDbProviderService.class)
				.invalidateCache();
	}

	@Override
	public TextTablePageData getTextTableData(SearchFilter filter) {
		TextTablePageData pageData = new TextTablePageData();

		getAll().stream()
				.forEach(text -> {
					String key = text.getKey();
					String localeId = text.getLocale();

					TextTableRowData row = pageData.addRow();
					row.setKey(key);
					row.setLocale(localeId);
					row.setText(TEXTS.getWithFallback(TextService.convertLocale(localeId), key, ""));
				});

		return pageData;
	}
}
