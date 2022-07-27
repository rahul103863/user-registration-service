package com.myorg.registration.util;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	
	@Autowired
	ConfigUtility configUtility;
	
	@Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(configUtility.getProperty("open.api.title"))
                        .description(configUtility.getProperty("open.api.description"))
                        .version(configUtility.getProperty("open.api.version"))
                        .contact(new Contact()
                                .name(configUtility.getProperty("open.api.contact.name"))
                                .url(configUtility.getProperty("open.api.contact.url"))
                                .email(configUtility.getProperty("open.api.contact.email")))
                );
    }

}
