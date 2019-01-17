package org.eclipse.scout.orga.shared.user;

import java.security.BasicPermission;

public class CreateUserPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public CreateUserPermission() {
    super(CreateUserPermission.class.getSimpleName());
  }
}
