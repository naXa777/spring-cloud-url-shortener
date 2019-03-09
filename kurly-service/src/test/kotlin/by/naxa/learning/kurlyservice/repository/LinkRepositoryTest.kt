package by.naxa.learning.kurlyservice.repository

import by.naxa.learning.kurlyservice.model.Link
import org.assertj.core.api.SoftAssertions
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import javax.transaction.Transactional


@DataJpaTest
@Transactional
@RunWith(SpringRunner::class)
class LinkRepositoryTest {

    @Autowired
    private lateinit var repository: LinkRepository

    var softly = SoftAssertions()

    @After
    fun assertAll() {
        softly.assertAll()
    }

    @Test
    fun shouldFindByShortCode() {
        // Arrange
        val expectedId = 1L
        val shortCode = "1"
        val expected = Link(originalUrl = "http://test.com", shortCode = shortCode)

        // Act
        val maybeFound = repository.findByShortCode(shortCode)

        // Assert
        softly.assertThat(maybeFound).isPresent
                .containsInstanceOf(Link::class.java)
                .hasValueSatisfying { found ->
                    softly.assertThat(found.shortCode).isEqualTo(expected.shortCode)
                    softly.assertThat(found.originalUrl).isEqualTo(expected.originalUrl)
                    softly.assertThat(found.id).isEqualTo(expectedId)
                }
    }

    @Test
    fun shouldFindNothingByShortCode() {
        // Act
        val maybeFound = repository.findByShortCode("404")

        // Assert
        softly.assertThat(maybeFound).isNotPresent
    }

}
