package com.farmu.api.challenge.common.mapper;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.util.Map;

import static com.google.common.collect.Maps.newLinkedHashMap;
import static org.apache.commons.collections4.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Convert a json to a map of strings.
 */
public class JsonMapConverter extends BidirectionalConverter<String, Map<String, String>>  {

    final private JsonMapper jsonMapper;

    public JsonMapConverter(final JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public Map<String, String> convertTo(final String source, Type<Map<String, String>> destinationType, final MappingContext context) {
        if (isNotBlank(source)) {
            return jsonMapper.deserialize(source, destinationType);
        } else {
            return newLinkedHashMap();
        }
    }

    @Override
    public String convertFrom(final Map<String, String> source, final Type<String> destinationType, final MappingContext context) {
        if (isNotEmpty(source)) {
            return jsonMapper.serialize(source);
        } else {
            return jsonMapper.serialize(newLinkedHashMap());
        }
    }

}