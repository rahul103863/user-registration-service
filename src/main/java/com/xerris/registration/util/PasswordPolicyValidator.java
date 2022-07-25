package com.xerris.registration.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordPolicyValidator {
    
    private static final  Logger log = LoggerFactory.getLogger(PasswordPolicyValidator.class);

	public static boolean isValid(final String password, final String passwordPattern) {
		
		log.info("Inside isValid method");
		Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
