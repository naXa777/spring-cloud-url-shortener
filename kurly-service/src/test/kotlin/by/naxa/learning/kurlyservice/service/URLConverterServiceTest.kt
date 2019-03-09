package by.naxa.learning.kurlyservice.service

import by.naxa.learning.kurlyservice.config.KurlyConfiguration
import by.naxa.learning.kurlyservice.exception.NotFoundException
import by.naxa.learning.kurlyservice.model.Link
import by.naxa.learning.kurlyservice.repository.LinkRepository
import com.nhaarman.mockitokotlin2.atLeast
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.SoftAssertions
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


/**
 *
 * this class is using mockito-kotlin for unit testing. known issues:
 * 0. https://github.com/nhaarman/mockito-kotlin/issues/302
 */
@RunWith(MockitoJUnitRunner::class)
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

    @After
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
        softly.assertThat(linkCaptor.value).satisfies {link ->
            softly.assertThat(link.originalUrl).isEqualTo(longUrl)
            softly.assertThat(link.shortCode).isEqualTo(shortCode)
            softly.assertThat(link.creationDate).isNotNull
        }
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
        softly.assertThat(linkCaptor.value).satisfies {link ->
            softly.assertThat(link.originalUrl).isEqualTo(longUrl)
            softly.assertThat(link.shortCode).isEqualTo(shortCode)
            softly.assertThat(link.creationDate).isNotNull
        }
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
        softly.assertThat(linkCaptor.value).satisfies {link ->
            softly.assertThat(link.originalUrl).isEqualTo(longUrl)
            softly.assertThat(link.shortCode).isEqualTo(shortCode)
            softly.assertThat(link.creationDate).isNotNull
        }
    }

    @Test
    fun shouldReturnLongUrlWhenShortCodeExists() {

        // Arrange
        val expectedLongUrl = "https://github.com/naXa777"
        val shortCode = "y"
        val id = 60L
        val link = Link(expectedLongUrl, shortCode)
        link.id = id

        whenever(repository.findByShortCode(shortCode)).thenReturn(Optional.of(link))

        // Act
        val result = service.getLongURLFromID(shortCode)

        // Assert
        softly.assertThat(result).isEqualTo(expectedLongUrl)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowExceptionWhenShortCodeDoesntExist() {

        // Arrange
        val shortCode = "y"

        whenever(repository.findByShortCode(shortCode)).thenReturn(Optional.empty())

        // Act
        service.getLongURLFromID(shortCode)
    }

}
