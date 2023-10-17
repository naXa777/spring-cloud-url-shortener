package by.naxa.learning.kurlyservice.service

import by.naxa.learning.kurlyservice.config.KurlyConfiguration
import by.naxa.learning.kurlyservice.exception.NotFoundException
import by.naxa.learning.kurlyservice.model.Link
import by.naxa.learning.kurlyservice.repository.LinkRepository
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.atLeast
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.function.Consumer


/**
 *
 * this class is using mockito-kotlin for unit testing.
 */
@ExtendWith(MockitoExtension::class)
class URLConverterServiceTest {

    @InjectMocks
    private lateinit var service: URLConverterService

    @Mock
    private lateinit var repository: LinkRepository

    @Mock
    private lateinit var config: KurlyConfiguration

    @Captor
    private lateinit var linkCaptor: ArgumentCaptor<Link>

    var softly = SoftAssertions()

    @AfterEach
    fun assertAll() {
        softly.assertAll()
    }

    @Test
    fun shouldGenerateShortLinkAndSaveWhenHttpsUrlIsPassedToShortenUrl() {

        // Arrange
        val longUrl = "https://about.me/naxa"
        val baseUrl = "https://te.st/"
        val shortCode = "3"
        val expectedUrl = baseUrl + shortCode

        whenever(config.baseUrl).thenReturn(baseUrl)

        doAnswer {
            val link1 = it.arguments[0] as Link
            link1.id = 3
            link1
        }.whenever(repository).save(any(Link::class.java))

        // Act
        val result = service.shortenURL(longUrl)

        // Assert
        verify(repository, atLeast(1)).save(linkCaptor.capture())
        softly.assertThat(result).isEqualTo(expectedUrl)
        softly.assertThat(linkCaptor.value).satisfies(Consumer { link ->
            softly.assertThat(link.originalUrl).isEqualTo(longUrl)
            softly.assertThat(link.shortCode).isEqualTo(shortCode)
            softly.assertThat(link.creationDate).isNotNull
        })
    }

    @Test
    fun shouldGenerateShortLinkAndSaveWhenHttpsUrlWithQueryParametersIsPassedToShortenUrl() {

        // Arrange
        val longUrl = "https://www.google.com/search?q=search+test&oq=search+test&sourceid=chrome&ie=UTF-8"
        val baseUrl = "https://te.st/"
        val shortCode = "F"
        val expectedUrl = baseUrl + shortCode

        whenever(config.baseUrl).thenReturn(baseUrl)

        doAnswer {
            val link1 = it.arguments[0] as Link
            link1.id = 15
            link1
        }.whenever(repository).save(any(Link::class.java))

        // Act
        val result = service.shortenURL(longUrl)

        // Assert
        verify(repository, atLeast(1)).save(linkCaptor.capture())
        softly.assertThat(result).isEqualTo(expectedUrl)
        softly.assertThat(linkCaptor.value).satisfies(Consumer {link ->
            softly.assertThat(link.originalUrl).isEqualTo(longUrl)
            softly.assertThat(link.shortCode).isEqualTo(shortCode)
            softly.assertThat(link.creationDate).isNotNull
        })
    }

    @Test
    fun shouldGenerateShortLinkAndSaveEvenWhenConfigBaseUrlIsNull() {

        // Arrange
        val longUrl = "https://github.com/naXa777"
        val fallbackBaseUrl = "http://localhost:9000/"
        val shortCode = "y"
        val expectedUrl = fallbackBaseUrl + shortCode

        whenever(config.baseUrl).thenReturn(null)

        doAnswer {
            val link1 = it.arguments[0] as Link
            link1.id = 60
            link1
        }.whenever(repository).save(any(Link::class.java))

        // Act
        val result = service.shortenURL(longUrl)

        // Assert
        verify(repository, atLeast(1)).save(linkCaptor.capture())
        softly.assertThat(result).isEqualTo(expectedUrl)
        softly.assertThat(linkCaptor.value).satisfies(Consumer { link ->
            softly.assertThat(link.originalUrl).isEqualTo(longUrl)
            softly.assertThat(link.shortCode).isEqualTo(shortCode)
            softly.assertThat(link.creationDate).isNotNull
        })
    }

    @Test
    fun shouldReturnLongUrlWhenShortCodeExists() {

        // Arrange
        val expectedLongUrl = "https://github.com/naXa777"
        val shortCode = "y"
        val id = 60L
        val link = Link(expectedLongUrl, shortCode)
        link.id = id

        whenever(repository.findByShortCode(shortCode)).thenReturn(link)

        // Act
        val result = service.getLongURLFromID(shortCode)

        // Assert
        softly.assertThat(result).isEqualTo(expectedLongUrl)
    }

    @Test
    fun shouldThrowExceptionWhenShortCodeDoesntExist() {

        // Arrange
        val shortCode = "y"

        whenever(repository.findByShortCode(shortCode)).thenReturn(null)

        assertThrows<NotFoundException> {
            // Act
            service.getLongURLFromID(shortCode)
        }
    }

}
