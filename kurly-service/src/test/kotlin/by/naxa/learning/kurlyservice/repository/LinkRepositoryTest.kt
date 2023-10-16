package by.naxa.learning.kurlyservice.repository

import by.naxa.learning.kurlyservice.model.Link
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import java.util.function.Consumer
import jakarta.transaction.Transactional


@DataJpaTest
@Transactional
class LinkRepositoryTest {

    @Autowired
    private lateinit var repository: LinkRepository

    var softly = SoftAssertions()

    @AfterEach
    fun assertAll() {
        softly.assertAll()
    }

    @Test
    fun shouldSave() {
        // Arrange
        val newLink = Link(originalUrl = "https://test.com")

        // Act
        val persistedLink = repository.save(newLink)

        // Assert
        softly.assertThat(persistedLink).isNotNull
            .isInstanceOf(Link::class.java)
            .satisfies(Consumer { persisted: Link ->
                softly.assertThat(persisted.originalUrl).isEqualTo(newLink.originalUrl)
                softly.assertThat(persisted.id).isNotNull
                softly.assertThat(persisted.id).isNotNegative
            })
    }

    @Test
    fun shouldNotSaveDuplicatedShortCode() {
        // Arrange
        val badLink = Link(originalUrl = "https://google.com", shortCode = "2")

        assertThrows<DataIntegrityViolationException> {
            // Act
            repository.save(badLink)
        }
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
        softly.assertThat(maybeFound).isNotNull
            .isInstanceOf(Link::class.java)
            .satisfies(Consumer { found: Link? ->
                softly.assertThat(found?.shortCode).isEqualTo(expected.shortCode)
                softly.assertThat(found?.originalUrl).isEqualTo(expected.originalUrl)
                softly.assertThat(found?.id).isEqualTo(expectedId)
            })
    }

    @Test
    fun shouldFindNothingByShortCode() {
        // Act
        val maybeFound = repository.findByShortCode("404")

        // Assert
        softly.assertThat(maybeFound).isNull()
    }

}
