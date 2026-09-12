package com.nourishinglife.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Custom JSF validator (type 3 of the 3 required validation types).
 * Accepts blank (phone is optional) or a string of 7-15 digits, optionally
 * starting with '+'.
 */
@FacesValidator("phoneNumberValidator")
public class PhoneNumberValidator implements Validator {

    private static final String PATTERN = "^\\+?[0-9]{7,15}$";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        String phone = value.toString().trim();
        if (phone.isEmpty()) {
            return;
        }
        if (!phone.matches(PATTERN)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid phone number",
                    "Phone number must contain 7 to 15 digits, optionally starting with '+'.");
            throw new ValidatorException(message);
        }
    }
}
