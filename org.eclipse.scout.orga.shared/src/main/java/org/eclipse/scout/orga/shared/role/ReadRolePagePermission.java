package org.eclipse.scout.orga.shared.role;

import java.security.BasicPermission;

public class ReadRolePagePermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public ReadRolePagePermission() {
    super(ReadRolePagePermission.class.getSimpleName());
  }

}
