package by.naxa.learning.kurlyservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "kurly")
data class KurlyConfiguration(
        var property: String? = null,
        var baseUrl: String? = null,
        var allowedProtocols: String = "http,https"
)
