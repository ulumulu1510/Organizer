package org.eclipse.scout.orga.ui.html;

import org.eclipse.scout.orga.client.ui.IVisField;
import org.eclipse.scout.orga.ui.html.vis.JsonVisField;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;

@Replace
public class OrgaJsonObjectFactory extends org.eclipse.scout.rt.ui.html.JsonObjectFactory {

	@Override
	public IJsonAdapter<?> createJsonAdapter(Object model, IUiSession session, String id, IJsonAdapter<?> parent) {
		if (model instanceof IVisField) {
			return new JsonVisField<>((IVisField) model, session, id, parent);
		}
		return super.createJsonAdapter(model, session, id, parent);
	}
}