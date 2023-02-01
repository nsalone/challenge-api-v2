package com.farmu.api.challenge.common.config;

import com.farmu.api.challenge.domain.shortener_url.dto.ShortenerUrl;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class CoreConfig {

    @Primary
    @Bean(name = "challengeDS")
    @ConfigurationProperties(prefix = "spring.datasource.challenge")
    public DataSource challengeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "challengeDB")
    public NamedParameterJdbcTemplate challengeDB(@Qualifier("challengeDS") final DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
    @Bean
    public RedisTemplate<String, ShortenerUrl> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ShortenerUrl> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public SpringLiquibase liquibase() {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/liquibase.xml");
        liquibase.setDataSource(this.challengeDataSource());
        return liquibase;
    }

}
