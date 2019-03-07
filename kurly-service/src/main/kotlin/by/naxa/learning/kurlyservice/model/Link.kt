package by.naxa.learning.kurlyservice.model

import org.hibernate.validator.constraints.URL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Lob

@Entity
data class Link (
        @field:URL
        @field:Lob
        @field:Column(nullable = false)
        var originalUrl: String,

        @field:Column(length = 30, unique = true)
        var shortCode: String? = null
) : AbstractJpaPersistable<Long>()
