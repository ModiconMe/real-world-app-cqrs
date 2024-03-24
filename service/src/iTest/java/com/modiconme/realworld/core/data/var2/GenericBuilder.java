package com.modiconme.realworld.core.data.var2;

import java.util.random.RandomGenerator;

public interface GenericBuilder<T, B extends GenericBuilder<T, B>> {

    T build();

    String NOT_EXIST = "not_exist";
    String EMPTY_STRING = "";
    String SPACE_STRING = " ";

    static String uniqString() {
        return uniqString(8);
    }

    static String uniqString(int length) {
        String alphabet = "1234567890qwertyuiopasdfghjklzxcvbnm";
        if (length < 0) {
            throw new IllegalArgumentException();
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(RandomGenerator.getDefault().nextInt(alphabet.length())));
        }
        return result.toString();
    }

    static String uniqEmail() {
        return uniqEmail(16);
    }

    static String uniqEmail(int length) {
        String postFix = "@email.com";
        if (length < postFix.length() + 1)
            throw new IllegalArgumentException();
        return uniqString(length - postFix.length()) + postFix;
    }
}

