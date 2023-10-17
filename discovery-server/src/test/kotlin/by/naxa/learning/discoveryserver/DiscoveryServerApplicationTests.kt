package by.naxa.learning.discoveryserver

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DiscoveryServerApplicationTests {

	@LocalServerPort
	private val port: Int = 0

	@Autowired
	lateinit var testRestTemplate: TestRestTemplate

	@Test
	fun contextLoads() {
	}

	@Test
	fun `actuator health endpoint is exposed`() {
		val url = "http://localhost:$port/actuator/health"
		val response: ResponseEntity<String> = testRestTemplate.getForEntity(url, String::class.java)

		assertEquals(HttpStatus.OK, response.statusCode)
	}
}
