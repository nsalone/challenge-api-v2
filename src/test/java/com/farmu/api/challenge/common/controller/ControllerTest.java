package com.farmu.api.challenge.common.controller;

import com.farmu.api.challenge.common.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testng.annotations.BeforeClass;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public abstract class ControllerTest {

    protected static final EasyRandom RANDOM = TestUtils.newEasyRandom();

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    @BeforeClass
    @SneakyThrows
    public void init() {
        openMocks(this).close();
        this.mockMvc = standaloneSetup(this.getTarget()).setConversionService(new DefaultFormattingConversionService()).build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    protected abstract Object getTarget();

    protected <I> MvcResult perform(final MockHttpServletRequestBuilder requestBuilder, final I request, final ResultMatcher status) {
        return this.performRequest(requestBuilder, request, status);
    }

    protected <I> void perform(final MockHttpServletRequestBuilder requestBuilder, final I request, final ResultMatcher status, final String headerName, final String headerValue) {
        this.performRequest(requestBuilder, request, status, headerName, headerValue);
    }

    @SneakyThrows
    protected <I, O> O perform(final MockHttpServletRequestBuilder requestBuilder, final I request, final Class<O> aClass,
                               final ResultMatcher status) {
        final MvcResult result = performRequest(requestBuilder, request, status);
        return (aClass != null) ? objectMapper.readValue(result.getResponse().getContentAsString(), aClass) : null;
    }

    @SneakyThrows
    protected <I, O> O perform(final MockHttpServletRequestBuilder requestBuilder, final I request, final TypeReference<O> typeReference,
                               final ResultMatcher status) {
        final MvcResult result = performRequest(requestBuilder, request, status);
        return (typeReference != null) ? objectMapper.readValue(result.getResponse().getContentAsString(), typeReference) : null;
    }

    @SneakyThrows
    private <I> MvcResult performRequest(final MockHttpServletRequestBuilder requestBuilder, final I request, final ResultMatcher status) {
        final String json = objectMapper.writeValueAsString(request);

        return this.mockMvc.perform(
                requestBuilder
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(status)
                .andReturn();
    }

    @SneakyThrows
    private <I> MvcResult performRequest(final MockHttpServletRequestBuilder requestBuilder, final I request, final ResultMatcher status, final String headerName, final String headerValue) {
        final String json = objectMapper.writeValueAsString(request);

        return this.mockMvc.perform(
                requestBuilder
                        .content(json)
                        .contentType(APPLICATION_JSON)
                        .header(headerName, headerValue))
                .andExpect(status)
                .andReturn();
    }

}
