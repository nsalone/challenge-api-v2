package com.farmu.api.challenge.domain.shortener_url.service;

import com.farmu.api.challenge.common.exception.NotFoundException;
import com.farmu.api.challenge.common.utils.HashingUtils;
import com.farmu.api.challenge.common.utils.UrlUtils;
import com.farmu.api.challenge.common.utils.ValidationUtils;
import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrl;
import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrlRequest;
import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrlResponse;
import com.farmu.api.challenge.domain.shortener_url.repository.ShortenerUrlRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@Slf4j
public class ShortenerUrlService {

    @Value("${redirect.url.base}")
    private String redirectBaseUrl;

    @Autowired
    private ShortenerUrlRepository repository;

    @SneakyThrows
    public ShortenerUrlResponse shortener(final ShortenerUrlRequest shortenerUrlRequest) {
        ValidationUtils.checkArg(shortenerUrlRequest, "shortenerUrlRequest");
        ValidationUtils.checkArg(shortenerUrlRequest.getUrl(), "url");
        ValidationUtils.validateURL(shortenerUrlRequest.getUrl(), "url");

        log.info("[get uri]");
        URI uri = UrlUtils.getUri(shortenerUrlRequest.getUrl());

        log.info("[get hash]");
        String id = HashingUtils.getHash(UrlUtils.getPath(uri));

        log.info("[save hash: {}]", id);
        repository.save(ShortenerUrl.builder().id(id)
                .url(shortenerUrlRequest.getUrl())
                .build());

        return ShortenerUrlResponse.builder().shortUrl(format("%s%s", redirectBaseUrl, id)).build();
    }

    public String findById(final String id) {
        ValidationUtils.checkArg(id, "id");
        final ShortenerUrl shortenerUrl = repository.findById(id);

        if(nonNull(shortenerUrl) && isNotBlank(shortenerUrl.getUrl()) )
            return shortenerUrl.getUrl();

        throw new NotFoundException("Id not found");
    }
}