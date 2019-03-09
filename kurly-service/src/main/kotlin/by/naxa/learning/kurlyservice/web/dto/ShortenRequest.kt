package by.naxa.learning.kurlyservice.web.dto

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotNull

data class ShortenRequest(@field:NotNull @field:URL var url: String?)
