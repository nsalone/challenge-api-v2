package com.farmu.api.challenge.domain.shortener_url.controller;

import com.farmu.api.challenge.common.controller.ControllerTest;
import com.farmu.api.challenge.common.utils.TestUtils;
import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrlRequest;
import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrlResponse;
import com.farmu.api.challenge.domain.shortener_url.service.ShortenerUrlService;
import org.jeasy.random.EasyRandom;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

@Test
public class ShortenerUrlControllerTest extends ControllerTest {

    public static final String URL_TEST_1 = "https://www.google.com/search?q=url+shortener";
    public static final String URL_RESULT_1 = "{\"shortUrl\":\"redirect.com/serch?1q=greater+than+zero&rlz=1C1CHBF\"}";
    public static final String URL_REQUEST = "http://www.google.com/serch?1q=greater+than+zero&rlz=1C1CHBF";
    public static final String URL_SHORT_RESPONSE = "redirect.com/serch?1q=greater+than+zero&rlz=1C1CHBF";

    private static final EasyRandom RANDOM = TestUtils.newEasyRandom();

    @InjectMocks
    private ShortenerUrlController target;
    @Mock
    private ShortenerUrlService service;

    @Override
    protected Object getTarget() {
        return target;
    }

    @Test
    public void findById_test() throws Exception {
        final String id = RANDOM.nextObject(String.class);

        when(service.findById(id)).thenReturn(URL_TEST_1);

        MvcResult result = mockMvc.perform(get("/api/v1/shortener-url/" + id))
                .andExpect(status().isOk())
                .andReturn();

        verify(service).findById(anyString());
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
        assertEquals(result.getResponse().getContentAsString(), URL_TEST_1);
    }

    public void shortener_test() throws Exception {
        final ShortenerUrlRequest shortenerUrlRequest = ShortenerUrlRequest.builder()
                .url(URL_REQUEST)
                .build();

        final ShortenerUrlResponse shortenerUrlResponse = ShortenerUrlResponse.builder()
                .shortUrl(URL_SHORT_RESPONSE)
                .build();

        when(service.shortener(shortenerUrlRequest)).thenReturn(shortenerUrlResponse);

        MvcResult result = perform(post("/api/v1/shortener-url"), shortenerUrlRequest, status().isOk());

        verify(service).shortener(shortenerUrlRequest);
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
        assertEquals(result.getResponse().getContentAsString(), URL_RESULT_1);
    }
    
}
