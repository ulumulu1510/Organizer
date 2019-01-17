package org.eclipse.scout.orga.shared.common;

import java.security.BasicPermission;

public class DeleteEntityPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public DeleteEntityPermission() {
		super(DeleteEntityPermission.class.getSimpleName());
	}
}
