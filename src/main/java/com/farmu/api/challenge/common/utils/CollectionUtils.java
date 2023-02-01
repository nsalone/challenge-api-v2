package com.farmu.api.challenge.common.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.empty;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

public class CollectionUtils {

    private CollectionUtils() {}

    public static <T> Stream<T> safeStream(final Collection<T> collection) {
        return isEmpty(collection) ? empty() : collection.stream().filter(Objects::nonNull);
    }

    public static <T> List<T> safeArrayToList(final T[] array) {
        return ofNullable(array).stream()
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .collect(toList());
    }

}
