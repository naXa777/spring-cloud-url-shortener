package by.naxa.learning.kurlyservice.web.dto

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotNull

data class ShortenRequest(@NotNull @URL var url: String?)
