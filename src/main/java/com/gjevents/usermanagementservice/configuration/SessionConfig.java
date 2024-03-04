package com.gjevents.usermanagementservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.session.jdbc.JdbcOperationsSessionRepository;
import org.springframework.session.web.http.DefaultCookieSerializer;
import javax.sql.DataSource;


@Configuration
public class SessionConfig {

    @Bean
    public JdbcOperationsSessionRepository sessionRepository(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new JdbcOperationsSessionRepository(jdbcTemplate);
    }

/*    @Value("${app.env:dev}")
    private String appEnv;

    @Bean
    public DefaultCookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setUseSecureCookie("prod".equals(appEnv));
        serializer.setSameSite("None");
        return serializer;
    }*/
}