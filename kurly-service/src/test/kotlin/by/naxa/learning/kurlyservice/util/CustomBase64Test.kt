package by.naxa.learning.kurlyservice.util

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CustomBase64Test {

    var softly = SoftAssertions()

    @AfterEach
    fun assertAll() {
        softly.assertAll()
    }

    @Test
    fun shouldEncodeWhenNumberIsPositive() {
        // Act
        val base64String = CustomBase64.encode(base10number = 31415926)

        // Assert
        softly.assertThat(base64String).isEqualTo("1trvs")
    }

    @Test
    fun shouldNotEncodeWhenNumberIsNegative() {
        assertThrows<IllegalArgumentException> {
            // Act
            CustomBase64.encode(base10number = -1)
        }
    }

    @Test
    fun shouldNotChangeStateWhenNewCharsetContainsDuplicates() {
        // Arrange
        val badCharset = "01234567890ABCDEF"
        val initialBase = CustomBase64.base
        val initialBase64Charset = CustomBase64.base64Charset

        softly.assertThatThrownBy {
            // Act
            CustomBase64.setCharset(base64Charset = badCharset)
        }.isInstanceOf(IllegalArgumentException::class.java)

        // Assert
        softly.assertThat(CustomBase64.base).isEqualTo(initialBase)
        softly.assertThat(CustomBase64.base64Charset).isEqualTo(initialBase64Charset)
    }

}
