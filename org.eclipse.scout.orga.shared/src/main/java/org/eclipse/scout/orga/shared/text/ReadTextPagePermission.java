package org.eclipse.scout.orga.shared.text;

import java.security.BasicPermission;

public class ReadTextPagePermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public ReadTextPagePermission() {
    super(ReadTextPagePermission.class.getSimpleName());
  }

}
