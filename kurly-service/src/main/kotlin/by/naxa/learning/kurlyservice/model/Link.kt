package by.naxa.learning.kurlyservice.model

import org.hibernate.validator.constraints.URL
import java.time.LocalDateTime
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Lob

const val MAX_URL_LENGTH = 200 * 1024   // 200 KB

@Entity
data class Link (
        @field:URL
        @field:Lob
        @field:Column(nullable = false, length = MAX_URL_LENGTH, columnDefinition = "mediumtext")
        var originalUrl: String,

        @field:Column(length = 30, unique = true)
        var shortCode: String? = null,

        @field:Column(nullable = false, updatable = false)
        var creationDate: LocalDateTime = LocalDateTime.now()
) : AbstractJpaPersistable<Long>()
