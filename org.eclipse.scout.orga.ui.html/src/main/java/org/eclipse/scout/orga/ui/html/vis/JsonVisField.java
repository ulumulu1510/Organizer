package org.eclipse.scout.orga.ui.html.vis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.scout.orga.client.ui.IVisField;
import org.eclipse.scout.orga.shared.vis.VisItem;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonProperty;
import org.eclipse.scout.rt.ui.html.json.form.fields.JsonFormField;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonVisField<T extends IVisField> extends JsonFormField<T> {
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public JsonVisField(T model, IUiSession uiSession, String id, IJsonAdapter<?> parent) {
		super(model, uiSession, id, parent);
	}

	@Override
	public String getObjectType() {
		return "VisField";
	}

	@Override
	protected void initJsonProperties(T model) {
		super.initJsonProperties(model);
		putJsonProperty(new JsonProperty<T>(IVisField.PROP_USERS, model) {
			@Override
			protected Map<String, List<VisItem>> modelValue() {
				return getModel().getPropertyUsers();
			}

			@SuppressWarnings("unchecked")
			@Override
			public Object prepareValueForToJson(Object value) {
				return itemsMapToJSON((Map<String, List<VisItem>>) value);
			}
		});
		putJsonProperty(new JsonProperty<T>(IVisField.PROP_START, model) {
			@Override
			protected Date modelValue() {
				return getModel().getPropertyStart();
			}

			@Override
			public Object prepareValueForToJson(Object value) {
				return dateToJSON((Date) value);
			}
		});
		putJsonProperty(new JsonProperty<T>(IVisField.PROP_END, model) {
			@Override
			protected Date modelValue() {
				return getModel().getPropertyEnd();
			}

			@Override
			public Object prepareValueForToJson(Object value) {
				return dateToJSON((Date) value);
			}
		});
	}

	private JSONObject itemsMapToJSON(Map<String, List<VisItem>> map) {
		if (CollectionUtility.isEmpty(map)) {
			return null;
		}

		JSONObject itemsMap = new JSONObject();
		map.entrySet()
				.stream()
				.forEach(entry -> putProperty(itemsMap, entry.getKey(), entryToJSON(entry)));
		return itemsMap;
	}

	private JSONArray entryToJSON(Entry<String, List<VisItem>> entry) {
		JSONArray array = new JSONArray();
		entry.getValue()
				.stream()
				.forEach(visItem -> array.put(visItemToJSON(visItem)));
		return array;
	}

	private JSONObject visItemToJSON(VisItem visItem) {
		JSONObject item = new JSONObject();
		putProperty(item, "x", dateToJSON(visItem.getX()));
		putProperty(item, "y", visItem.getY());
		return item;
	}

	private String dateToJSON(Date date) {
		if (date == null) {
			return null;
		}
		return FORMAT.format(date);
	}

}
