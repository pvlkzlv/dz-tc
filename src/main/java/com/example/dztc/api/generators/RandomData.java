package com.example.dztc.api.generators;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {
    public static final int LENGTH = 10;

    public static String getString() {
        return "test" + RandomStringUtils.randomAlphabetic(LENGTH);
    }

    public static String getString(int length) {
        return "test" + RandomStringUtils.randomAlphabetic(length);
    }
}