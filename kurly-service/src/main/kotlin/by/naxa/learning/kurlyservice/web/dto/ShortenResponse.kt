package by.naxa.learning.kurlyservice.web.dto

import io.swagger.annotations.ApiModel

@ApiModel
data class ShortenResponse(
        val originalUrl: String,
        val shortUrl: String
)
