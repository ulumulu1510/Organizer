package org.eclipse.scout.orga.shared;

import java.security.BasicPermission;

public class ViewAdminOutlinePermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public ViewAdminOutlinePermission() {
    super(ViewAdminOutlinePermission.class.getSimpleName());
  }
}
