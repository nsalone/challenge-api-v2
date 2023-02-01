package com.farmu.api.challenge.common.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashingUtils {

    public static String getHash(final String value) {
        return String.valueOf(Hashing.murmur3_128().hashString(value, StandardCharsets.UTF_8));
    }

}
