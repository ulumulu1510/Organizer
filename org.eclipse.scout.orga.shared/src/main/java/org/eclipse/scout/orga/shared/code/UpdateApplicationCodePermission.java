package org.eclipse.scout.orga.shared.code;

import java.security.BasicPermission;

public class UpdateApplicationCodePermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public UpdateApplicationCodePermission() {
		super(UpdateApplicationCodePermission.class.getSimpleName());
	}
}
