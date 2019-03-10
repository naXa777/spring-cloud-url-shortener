package by.naxa.learning.kurlyservice.web

import by.naxa.learning.kurlyservice.service.URLConverterService
import by.naxa.learning.kurlyservice.web.dto.ShortenRequest
import by.naxa.learning.kurlyservice.web.dto.ShortenResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
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

@Api(tags = ["public"])
@RestController
class KurlyController(@Autowired val service: URLConverterService) {

    @ApiOperation(value = "Create short URL", response = ShortenResponse::class)
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created", response = ShortenResponse::class),
        ApiResponse(code = 400, message = "Bad Request", response = ShortenResponse::class)
    ])
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

    @ApiOperation(value = "Redirect to original URL")
    @ApiResponses(value = [
        ApiResponse(code = 302, message = "Found"),
        ApiResponse(code = 404, message = "Not Found")
    ])
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
