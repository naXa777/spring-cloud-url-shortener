package by.naxa.learning.kurlyservice.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.URL
import jakarta.validation.constraints.NotNull

@Schema
data class ShortenRequest(
        @Schema(required = true)
        @field:NotNull @field:URL var url: String?
)
