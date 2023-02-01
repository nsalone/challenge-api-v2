package com.farmu.api.challenge.common.utils;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Test
public class ValidationUtilsTest {

    public void hasContent() {
        String input = "asd";
        assertTrue(ValidationUtils.hasContent(input));
    }

}
