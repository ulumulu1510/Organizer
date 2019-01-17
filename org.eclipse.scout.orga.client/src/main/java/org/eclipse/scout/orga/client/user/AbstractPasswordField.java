package org.eclipse.scout.orga.client.user;

import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.status.MultiStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;

import org.eclipse.scout.orga.shared.security.PasswordUtility;

public class AbstractPasswordField extends AbstractStringField {

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("Password");
  }

  @Override
  protected boolean getConfiguredInputMasked() {
    return true;
  }

  /**
   * Verifies password value and sets error indicators accordingly.
   *
   * @return true if the password complies with the implemented password policy. in all other cases false is returned.
   */
  public boolean validateField() {
    clearErrorStatus();

    if (getValue() == null) {
      setError(TEXTS.get("PasswordEmptyError"));
      return false;
    }

    if (getValue().length() < PasswordUtility.PASSWORD_LENGTH_MIN) {
      setError(TEXTS.get("PasswordTooShortError", String.valueOf(PasswordUtility.PASSWORD_LENGTH_MIN)));
      return false;
    }

    if (getValue().length() > PasswordUtility.PASSWORD_LENGTH_MAX) {
      setError(TEXTS.get("PasswordTooLongError", String.valueOf(PasswordUtility.PASSWORD_LENGTH_MAX)));
      return false;
    }

    // make sure that we catch all relevant password constrains
    if (!PasswordUtility.matchesPasswordPolicy(getValue())) {
      setError(TEXTS.get("PasswordPolicyError"));
      return false;
    }

    return true;
  }

  public void setError(String message) {
    MultiStatus error = new MultiStatus();
    Status status = new Status(message);
    error.add(status);
    setErrorStatus(error);
  }
}
