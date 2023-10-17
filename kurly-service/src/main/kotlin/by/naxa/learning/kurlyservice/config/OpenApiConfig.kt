package by.naxa.learning.kurlyservice.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenApiConfig(
//        @Autowired private val config: KurlyConfiguration
) {

    @Bean
    fun api2(): OpenAPI {
        return OpenAPI().info(
            Info().title("kURLy API")
                .description("Link shortener sample application")
                .version("v1.0")
        )
        // TODO control OpenAPI using external configuration
        // .enable(config.swaggerEnable)
    }
}
