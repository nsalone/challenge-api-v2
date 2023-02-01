package com.farmu.api.challenge.common.utils;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class TestUtils {

    public static EasyRandom newEasyRandom() {
        final EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.setRandomizationDepth(5);
        parameters.setCollectionSizeRange(new EasyRandomParameters.Range<>(2,2));
        return new EasyRandom(parameters);
    }

}
