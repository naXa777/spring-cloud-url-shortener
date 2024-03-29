package by.naxa.learning.kurlyservice

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post


/**
 * Abstract parent class for all web integration tests
 */
@SpringBootTest
@AutoConfigureMockMvc
abstract class SpringBootMvcTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    protected abstract fun getTargetUrl(): String

    @Throws(Exception::class)
    protected fun performPostRequest(body: Any?): ResultActions {
        return this.mockMvc.perform(
                post(getTargetUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
        )
    }

}
