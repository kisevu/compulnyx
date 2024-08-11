package com.compulnyx.project.test_excercise.common.Configs;/*
*
@author ameda
@project Books
*
*/

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Kevin Ameda",
                        email = "amedakevin@gmail.com",
                        url = "https://github.com/kisevu/compulnyx"
                ),
                description = "OpenApi documentation because we have Spring Security",
                title = "OpenApi specification - Ameda",
                version = "1.0",
                license = @License(
                        name = "compulnyx",
                        url = "https://compulynx.com/"
                ),
                termsOfService = "in-house test project"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:9000/api/v1"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://www.github.com/kisevu"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
