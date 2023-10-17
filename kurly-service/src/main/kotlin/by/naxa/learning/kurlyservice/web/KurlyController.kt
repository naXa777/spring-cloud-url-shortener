package by.naxa.learning.kurlyservice.web

import by.naxa.learning.kurlyservice.service.URLConverterService
import by.naxa.learning.kurlyservice.web.dto.ShortenRequest
import by.naxa.learning.kurlyservice.web.dto.ShortenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import java.io.IOException
import java.net.URISyntaxException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid

@Tag(name = "public")
@RestController
class KurlyController(@Autowired val service: URLConverterService) {

    @Operation(summary = "Create short URL")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Created"),
            ApiResponse(responseCode = "400", description = "Bad Request")
        ]
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(value = ["/shorten"], consumes = ["application/json"], produces = ["application/json"])
    @Throws(Exception::class)
    fun shortenUrl(@RequestBody @Valid shortenRequest: ShortenRequest): ShortenResponse {
        val longUrl = shortenRequest.url ?: ""
        LOGGER.info("Received url to shorten: $longUrl")
        val shortUrl = service.shortenURL(longUrl)
        LOGGER.info("Shortened url to: $shortUrl")
        return ShortenResponse(longUrl, shortUrl)
    }

    @Operation(summary = "Redirect to original URL")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "302", description = "Found"),
            ApiResponse(responseCode = "404", description = "Not Found")
        ]
    )
    @ResponseStatus(code = HttpStatus.FOUND)
    @GetMapping("/{id:[\\w-]{1,30}}")
    @Throws(IOException::class, URISyntaxException::class, Exception::class)
    fun redirectUrl(
        @PathVariable id: String,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): RedirectView {
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
