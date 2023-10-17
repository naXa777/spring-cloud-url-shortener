package by.naxa.learning.kurlyservice.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class ShortenResponse(
        val originalUrl: String,
        val shortUrl: String
)
