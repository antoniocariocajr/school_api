package com.school.infra.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI schoolAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("School Management API")
                        .version("1.0")
                        .description("OAuth2 + OpenAPI 3"))
                .addSecurityItem(new SecurityRequirement().addList("oauth2"))
                .components(new Components()
                        .addSecuritySchemes("oauth2",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .flows(new OAuthFlows()
                                                .authorizationCode(new OAuthFlow()
                                                        .authorizationUrl("https://accounts.google.com/o/oauth2/v2/auth")
                                                        .tokenUrl("https://oauth2.googleapis.com/token")
                                                        .scopes(new Scopes()
                                                                .addString("openid", "OpenID")
                                                                .addString("email", "E-mail")
                                                                .addString("profile", "Profile"))))));
    }
}
