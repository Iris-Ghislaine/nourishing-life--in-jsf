package com.nourishinglife.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Custom JSF validator (type 3 of the 3 required validation types).
 * Cross-field validation: compares the "confirm password" input's value against
 * the original password field, whose client id is passed in via
 * &lt;f:attribute name="passwordComponentId" value="..."/&gt; on the confirm field.
 */
@FacesValidator("passwordMatchValidator")
public class PasswordMatchValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object passwordComponentId = component.getAttributes().get("passwordComponentId");
        if (passwordComponentId == null) {
            return;
        }

        UIComponent passwordComponent = component.findComponent(passwordComponentId.toString());
        if (!(passwordComponent instanceof UIInput)) {
            return;
        }

        // The password field is earlier in the tree, so by the time this validator
        // runs its own validate() has often already succeeded and cleared its
        // submittedValue (JSF resets it once the local value is set) - fall back
        // to the resolved local value in that case.
        UIInput passwordInput = (UIInput) passwordComponent;
        Object passwordValue = passwordInput.getSubmittedValue();
        if (passwordValue == null) {
            passwordValue = passwordInput.getValue();
        }
        String confirm = value == null ? "" : value.toString();
        String password = passwordValue == null ? "" : passwordValue.toString();

        if (!confirm.equals(password)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Passwords do not match",
                    "Please make sure both password fields are identical.");
            throw new ValidatorException(message);
        }
    }
}
