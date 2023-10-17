package by.naxa.learning.kurlyservice.service

import by.naxa.learning.kurlyservice.config.KurlyConfiguration
import by.naxa.learning.kurlyservice.exception.NotFoundException
import by.naxa.learning.kurlyservice.model.Link
import by.naxa.learning.kurlyservice.repository.LinkRepository
import by.naxa.learning.kurlyservice.util.CustomBase64
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import jakarta.transaction.Transactional

@Service
@RefreshScope
@Transactional
class URLConverterService(
        @Autowired val repository: LinkRepository,
        @Autowired val config: KurlyConfiguration
) {

    fun shortenURL(longUrl: String): String {
        LOGGER.info("Shortening $longUrl")

        var link = Link(longUrl)

        link = repository.save(link)
        val id = link.id!!
        val base64id = CustomBase64.encode(id)
        link.shortCode = base64id
        repository.save(link)

        val fallbackBaseUrl = "http://localhost:9000/"
        val uriComponents = UriComponentsBuilder.fromHttpUrl(config.baseUrl ?: fallbackBaseUrl)
                .path(base64id)
                .build()

        return uriComponents.toUriString()
    }

    @Throws(Exception::class)
    fun getLongURLFromID(uniqueID: String): String {
        val link = repository.findByShortCode(uniqueID) ?: throw NotFoundException()
        LOGGER.info("Converting shortened URL back to ${link.originalUrl}")
        return link.originalUrl
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(URLConverterService::class.java)
    }

}
