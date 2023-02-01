package com.farmu.api.challenge.common.mapper;

import com.google.common.collect.ImmutableMultimap;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.farmu.api.challenge.common.utils.CollectionUtils.safeStream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class Mapper {

    private static final ImmutableMultimap<Class<?>, Class<?>> MAPPINGS = ImmutableMultimap.<Class<?>, Class<?>>builder()
            .build();

    private MapperFacade mapperFacade;

    @Autowired
    private JsonMapper jsonMapper;

    @PostConstruct
    public void init() {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        for (Map.Entry<Class<?>, Class<?>> entry : MAPPINGS.entries()) {
            final ClassMapBuilder<?, ?> builder = mapperFactory.classMap(entry.getKey(), entry.getValue());
            builder.byDefault().register();
        }

        mapperFacade = mapperFactory.getMapperFacade();
    }



    public <I, O> O map(final I input, final Class<O> clazz) {
        return mapperFacade.map(input, clazz);
    }

    public <I, O> List<O> map(final List<I> input, final Class<O> clazz) {
        return safeStream(input).map(it -> this.map(it, clazz)).collect(toList());
    }

    public <I, O> Optional<O> map(final Optional<I> input, final Class<O> clazz) {
        return input.map(it -> this.map(it, clazz));
    }

    public <I, O> Set<O> map(final Set<I> input, final Class<O> clazz) {
        return safeStream(input).map(it -> this.map(it, clazz)).collect(toSet());
    }

    public static ImmutableMultimap<Class<?>, Class<?>> getMappings() {
        return MAPPINGS;
    }

}