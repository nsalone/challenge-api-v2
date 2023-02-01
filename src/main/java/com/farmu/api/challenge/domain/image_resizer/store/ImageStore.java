package com.farmu.api.challenge.domain.image_resizer.store;

import com.farmu.api.challenge.common.exception.NotFoundException;
import com.farmu.api.challenge.common.store.Store;
import com.farmu.api.challenge.domain.image_resizer.dto.Image;
import com.farmu.api.challenge.domain.image_resizer.dto.ImageTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class ImageStore extends Store<Image, ImageTO> {

    @Autowired
    private ImageRepository repository;

    @Override
    protected JpaRepository<Image, Long> getRepository() {
        return repository;
    }

    @Override
    protected Class<Image> entityClass() {
        return Image.class;
    }

    @Override
    protected Class<ImageTO> dtoClass() {
        return ImageTO.class;
    }

    public ImageTO findByUUID(final String uuid) {
        return repository.findByUuid(uuid).map(it -> mapper.map(it, dtoClass()))
                .orElseThrow(() -> new NotFoundException(format("Entity with uuid %s not found", uuid)));
    }
}
