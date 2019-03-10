package by.naxa.learning.kurlyservice.web.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotNull

@ApiModel
data class ShortenRequest(
        @ApiModelProperty(required = true)
        @field:NotNull @field:URL var url: String?
)
