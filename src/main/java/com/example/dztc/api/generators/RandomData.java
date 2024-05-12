package com.example.dztc.api.generators;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {
    public static final int LENGTH = 10;
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+-=[]{}|;:',.<>/?";

    public static String getString() {
        return "Test" + RandomStringUtils.randomAlphabetic(LENGTH);
    }

    public static String getStringWithSpecialSymbols() {
        String randomString = RandomStringUtils.randomAlphabetic(LENGTH - 3) + RandomStringUtils.random(3,
                                                                                                        SPECIAL_CHARACTERS);
        List<Character> shuffledChars = randomString.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(shuffledChars);
        return shuffledChars.stream().map(String::valueOf).collect(Collectors.joining());
    }

    public static String getString(int length) {
        return "Test" + RandomStringUtils.randomAlphabetic(length);
    }
}