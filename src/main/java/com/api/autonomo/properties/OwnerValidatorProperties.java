package com.api.autonomo.properties;

import com.api.autonomo.model.Owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.api.autonomo.service.OwnerService;

@Component
public class OwnerValidatorProperties implements Validator {

	@Autowired
	private OwnerService ownerService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Owner.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Owner owner = (Owner) target;

		// Validations for owner's email
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ownerEmail", "NotEmpty");

		if (owner.getEmail().length() < 6 || owner.getEmail().length() > 33) {
			errors.rejectValue("ownerEmail", "Size.ownerForm.ownerEmail");
		}

		if (ownerService.findbyOwnerEmail(owner.getEmail()) != null) {
			errors.rejectValue("ownerEmail", "Duplicated.ownerForm.ownerEmail");
		}

		// Validation for owner's email Key
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ownerEmailKey", "NotEmpty");

		if (owner.getEmail().length() < 6) {
			errors.rejectValue("ownerEmailKey", "Size.ownerForm.ownerEmailKey");
		}

		if (!owner.getEmailKeyRestore().equals(owner.getEmailKey())) {
			errors.rejectValue("ownerEmailKeyConfirm", "Key passwords do not match");
		}
	}

}
