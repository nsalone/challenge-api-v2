package com.farmu.api.challenge.domain.image_resizer.store;

import com.farmu.api.challenge.domain.image_resizer.dto.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByUuid(final String uuid);

}