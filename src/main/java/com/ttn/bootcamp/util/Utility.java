package com.ttn.bootcamp.util;

import java.util.Base64;
import java.util.regex.Pattern;

public class Utility {
    private static final String EMAIL_REGEX = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";

    private Utility() {
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public static String encrypt(String str) {
        return new String(Base64.getEncoder().encode((str.getBytes())));
    }

    public static String decrypt(String str) {
        return new String(Base64.getDecoder().decode((str.getBytes())));
    }
}
