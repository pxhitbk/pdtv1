/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.webapp.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pdt.core.model.ContactMessage;

/**
 * @author august
 *
 */
public class ContactMessageValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return ContactMessage.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "customer.fullName", "validate.user_fullname");
		ValidationUtils.rejectIfEmpty(errors, "customer.email", "validate.user_email");
		ValidationUtils.rejectIfEmpty(errors, "customer.phone", "validate.user_phone");
	}

}
