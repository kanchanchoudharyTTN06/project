package com.ttn.bootcamp;

import java.util.Base64;

public class Utility {
    private Utility() {
    }

    public static String encrypt(String str) {
        return new String(Base64.getEncoder().encode((str.getBytes())));
    }

    public static String decrypt(String str) {
        return new String(Base64.getDecoder().decode((str.getBytes())));
    }
}
