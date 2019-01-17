package org.eclipse.scout.orga.shared.user;

import java.security.BasicPermission;

public class UpdateUserPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public UpdateUserPermission() {
    super(UpdateUserPermission.class.getSimpleName());
  }
}
