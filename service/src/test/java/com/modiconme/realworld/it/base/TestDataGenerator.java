package com.modiconme.realworld.it.base;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class TestDataGenerator {

    public static final Random RANDOM = new Random();

    public static String uniqString() {
        return uniqString(16);
    }

    public static String uniqString(int length) {
        String alphabet = "qwertyuiopasdfghjklzxcvbnm";
        if (length < 0) {
            throw new IllegalArgumentException();
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(RANDOM.nextInt(alphabet.length())));
        }
        return result.toString();
    }

    public static String uniqEmail() {
        return uniqEmail(16);
    }

    public static String uniqEmail(int length) {
        String postFix = "@email.com";
        if (length < postFix.length() + 1)
            throw new IllegalArgumentException();
        return uniqString(length - postFix.length()) + postFix;
    }
}
