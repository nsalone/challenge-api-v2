package com.farmu.api.challenge.domain.shortener_url.repository;

import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ShortenerUrlRepository {

    @Autowired
    private RedisTemplate<String, ShortenerUrl> redisTemplate;

    @Transactional
    public void save(ShortenerUrl shortenerUrl) {
        redisTemplate.opsForValue()
                .set(shortenerUrl.getId(), shortenerUrl);
    }

    public ShortenerUrl findById(String id) {
        return redisTemplate.opsForValue().get(id);
    }

}
