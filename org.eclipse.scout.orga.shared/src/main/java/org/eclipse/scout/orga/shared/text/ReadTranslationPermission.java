package org.eclipse.scout.orga.shared.text;

import java.security.BasicPermission;

public class ReadTranslationPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public ReadTranslationPermission() {
		super(ReadTranslationPermission.class.getSimpleName());
	}
}
