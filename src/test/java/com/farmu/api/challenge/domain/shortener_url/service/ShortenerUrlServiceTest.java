package com.farmu.api.challenge.domain.shortener_url.service;

import com.farmu.api.challenge.common.utils.HashingUtils;
import com.farmu.api.challenge.common.utils.TestUtils;
import com.farmu.api.challenge.common.utils.UrlUtils;
import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrl;
import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrlRequest;
import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrlResponse;
import com.farmu.api.challenge.domain.shortener_url.repository.ShortenerUrlRepository;
import org.jeasy.random.EasyRandom;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

@Test
public class ShortenerUrlServiceTest {

    protected static final EasyRandom RANDOM = TestUtils.newEasyRandom();
    public static final String REDIRECT_URL = "redirect.com/";
    public static final String URL_DB = "http://www.google.com/serch?1q=greater+than+zero&rlz=1C1CHBF";

    @InjectMocks
    private ShortenerUrlService target;

    @Mock
    private ShortenerUrlRepository repository;


    @BeforeTest
    public void init() {
        openMocks(this);
        ReflectionTestUtils.setField(target, "redirectBaseUrl", REDIRECT_URL);
    }

    public void shortener_test() {

        ShortenerUrlRequest shortenerUrlRequest = ShortenerUrlRequest.builder()
                .url("http://www.google.com/serch?1q=greater+than+zero&rlz=1C1CHBF")
                .build();

        ShortenerUrlResponse shortenerUrlResponse = ShortenerUrlResponse.builder()
                .shortUrl("redirect.com/serch?1q=greater+than+zero&rlz=1C1CHBF")
                .build();

        URI uri = UrlUtils.getUri(shortenerUrlRequest.getUrl());
        String id = HashingUtils.getHash(UrlUtils.getPath(uri));

        ShortenerUrlResponse response = target.shortener(shortenerUrlRequest);

        verify(repository).save(any(ShortenerUrl.class));
        assertNotNull(response);
        assertEquals(response.getShortUrl(), REDIRECT_URL + id);
    }

    public void shortener_fail_url_test() {

        ShortenerUrlRequest shortenerUrlRequest = ShortenerUrlRequest.builder()
                .url("asad")
                .build();
        assertThrows(IllegalArgumentException.class, () -> {
            target.shortener(shortenerUrlRequest);
        });
    }

    public void shortener_empty_url_test() {

        ShortenerUrlRequest shortenerUrlRequest = ShortenerUrlRequest.builder().build();
        assertThrows(IllegalArgumentException.class, () -> {
            target.shortener(shortenerUrlRequest);
        });
    }


    public void findById_test() {

        ShortenerUrl shortenerUrl = ShortenerUrl.builder()
                .id(RANDOM.nextObject(String.class))
                .url(URL_DB)
                .build();

        when(repository.findById(eq(shortenerUrl.getId()))).thenReturn(shortenerUrl);

        String response = target.findById(shortenerUrl.getId());

        verify(repository).findById(shortenerUrl.getId());
        assertNotNull(response);
        assertEquals(response, URL_DB);
    }

}
