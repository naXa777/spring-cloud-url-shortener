package by.naxa.learning.kurlyservice.web

import by.naxa.learning.kurlyservice.SpringBootMvcTest
import by.naxa.learning.kurlyservice.web.dto.ShortenRequest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import javax.transaction.Transactional

/**
 * Test [KurlyController] and the underlying services
 */
@Transactional
class KurlyControllerIT : SpringBootMvcTest() {

    override fun getTargetUrl(): String {
        return "/shorten"
    }

    @Test
    @Throws(Exception::class)
    fun postShorten() {
        val body = ShortenRequest("http://google.com")

        performPostRequest(body)
                .andExpect(status().isCreated)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    }

    @Test
    @Throws(Exception::class)
    fun postShortenNoUrl() {
        performPostRequest(null)
                .andExpect(status().is4xxClientError)
    }

    @Test
    @Throws(Exception::class)
    fun getRedirect() {
        this.mockMvc
                .perform(
                        get("/1")
                )
                .andExpect(status().isFound)
    }

    @Test
    @Throws(Exception::class)
    fun getNotFound() {
        this.mockMvc
                .perform(
                        get("/404")
                )
                .andExpect(status().isNotFound)
    }

}
