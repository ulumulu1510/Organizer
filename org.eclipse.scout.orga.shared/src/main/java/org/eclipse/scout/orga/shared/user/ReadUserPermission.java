package org.eclipse.scout.orga.shared.user;

import java.security.BasicPermission;

public class ReadUserPermission extends BasicPermission {

	private static final long serialVersionUID = 1L;

	public ReadUserPermission() {
		super(ReadUserPermission.class.getSimpleName());
	}
}
