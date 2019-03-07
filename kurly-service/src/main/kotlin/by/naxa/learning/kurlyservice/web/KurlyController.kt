package by.naxa.learning.kurlyservice.web

import by.naxa.learning.kurlyservice.service.URLConverterService
import by.naxa.learning.kurlyservice.web.dto.ShortenRequest
import by.naxa.learning.kurlyservice.web.dto.ShortenResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import java.io.IOException
import java.net.URISyntaxException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
class KurlyController(@Autowired val service: URLConverterService) {

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(value = ["/shorten"], consumes = ["application/json"])
    @Throws(Exception::class)
    fun shortenUrl(@RequestBody @Valid shortenRequest: ShortenRequest): ShortenResponse {
        val longUrl = shortenRequest.url ?: ""
        LOGGER.info("Received url to shorten: $longUrl")
        val shortUrl = service.shortenURL(longUrl)
        LOGGER.info("Shortened url to: $shortUrl")
        return ShortenResponse(longUrl, shortUrl)
    }

    @ResponseStatus(code = HttpStatus.FOUND)
    @GetMapping("/{id:[\\w-]{1,30}}")
    @Throws(IOException::class, URISyntaxException::class, Exception::class)
    fun redirectUrl(@PathVariable id: String, request: HttpServletRequest, response: HttpServletResponse): RedirectView {
        LOGGER.info("Received shortened url to redirect: $id")
        val redirectUrlString = service.getLongURLFromID(id)
        LOGGER.info("Original URL: $redirectUrlString")
        val redirectView = RedirectView()
        redirectView.url = redirectUrlString
        return redirectView
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(KurlyController::class.java)
    }

}
