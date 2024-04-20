package com.bordify.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to validate email addresses.
 */
public class EmailValidator {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);


    /**
     * Checks if the given email address is valid.
     *
     * @param email The email address to validate.
     * @return true if the email address is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
