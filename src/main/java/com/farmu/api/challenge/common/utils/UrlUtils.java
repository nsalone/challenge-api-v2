package com.farmu.api.challenge.common.utils;

import lombok.SneakyThrows;

import java.net.URI;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class UrlUtils {

    @SneakyThrows
    public static URI getUri(String url) {
        return new URI(url);
    }

    @SneakyThrows
    public static String getDomain(URI uri) {
        return format("%s://%s/", uri.getScheme(), uri.getHost());
    }

    @SneakyThrows
    public static String getPath(URI uri) {
        return format("%s%s", uri.getPath(), isNotBlank(uri.getQuery())? "?" + uri.getQuery() : "");
    }

}
