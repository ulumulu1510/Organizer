package org.eclipse.scout.orga.shared.text;

import java.security.BasicPermission;

public class CreateTranslationPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public CreateTranslationPermission() {
		super(CreateTranslationPermission.class.getSimpleName());
	}
}
