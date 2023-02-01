package com.farmu.api.challenge.common.store;

import com.farmu.api.challenge.common.dto.entity.BaseEntity;
import com.farmu.api.challenge.common.dto.entity.BaseEntityTO;
import com.farmu.api.challenge.common.exception.NotFoundException;
import com.farmu.api.challenge.common.mapper.Mapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.farmu.api.challenge.common.utils.CollectionUtils.safeStream;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Layer used to abstract the Spring Data framework to the domain.
 */
public abstract class Store<E extends BaseEntity, D extends BaseEntityTO> {

    @Autowired
    protected Mapper mapper;


    protected abstract JpaRepository<E, Long> getRepository();

    protected abstract Class<E> entityClass();

    protected abstract Class<D> dtoClass();


    protected static <T> T safe(final Object valueToQuery, final T t) {
        return shouldIgnore(valueToQuery) ? null : t;
    }

    protected static Predicate safe(final Object valueToQuery, final Function<Object, Predicate> functionPredicate) {
        return shouldIgnore(valueToQuery) ? null : functionPredicate.apply(valueToQuery);
    }

    protected static boolean shouldIgnore(final Object filterValue) {
        return isNull(filterValue) || (filterValue instanceof String) && isEmpty((String) filterValue) || (filterValue instanceof Collection) && CollectionUtils.isEmpty((Collection) filterValue);
    }

    public D save(final D dto) {
        checkArgument(nonNull(dto), "Param 'dto' is mandatory");
        checkArgument(isNull(dto.getId()), "Param 'dto.id' must to be null");
        return mapper.map(getRepository().save(mapper.map(dto, entityClass())), dtoClass());
    }

    public D update(final D dto) {
        checkArgument(nonNull(dto), "Param 'dto' is mandatory");
        checkArgument(nonNull(dto.getId()), "Param 'dto.id' is mandatory");
        return mapper.map(getRepository().save(mapper.map(dto, entityClass())), dtoClass());
    }

    public D saveOrUpdate(final D dto) {
        return mapper.map(getRepository().save(mapper.map(dto, entityClass())), dtoClass());
    }

    public void delete(final D dto) {
        checkArgument(nonNull(dto), "Param 'dto' is mandatory");
        checkArgument(nonNull(dto.getId()), "Param 'dto.id' must to be null");
        getRepository().delete(mapper.map(dto, entityClass()));
    }

    public List<D> save(final List<D> dtoList) {
        return mapper.map(getRepository().saveAll(mapper.map(dtoList, entityClass())), dtoClass());
    }

    public void deleteAll(final List<D> dtoList) {
        getRepository().deleteAll(mapper.map(dtoList, entityClass()));
    }

    protected Specification<E> equals(final String field, final Object value) {
        return (root, cq, cb) -> safe(value, cb.equal(root.get(field), value));
    }

    public void deleteById(final Long id) {
        checkArgument(nonNull(id), "Field 'id' is mandatory");
        getRepository().deleteById(id);
    }

    public D findById(final Long id) {
        checkArgument(nonNull(id), "Param 'id' is mandatory");
        return getRepository().findById(id)
                .map(it -> mapper.map(it, dtoClass()))
                .orElseThrow(() -> new NotFoundException(format("Entity with id %s not found", id)));
    }

    public List<D> findAll() {
        return getRepository().findAll().stream().map(it -> mapper.map(it, dtoClass())).collect(toList());
    }

    protected D map(final E entity) {
        return mapper.map(entity, dtoClass());
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    protected Optional<D> map(final Optional<E> entity) {
        return entity.map(it -> mapper.map(it, dtoClass()));
    }

    protected List<D> map(final List<E> entities) {
        return safeStream(entities).map(it -> mapper.map(it, dtoClass())).collect(toList());
    }

}