package by.naxa.learning.kurlyservice


import by.naxa.learning.kurlyservice.web.dto.ShortenRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import java.util.*
import javax.validation.Validator

/**
 * Simple test to make sure that Bean Validation is working
 * (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class BeanValidatorTest {

    private var validator: Validator = LocalValidatorFactoryBean()

    @Before
    fun initValidator() {
        (validator as LocalValidatorFactoryBean).afterPropertiesSet()
        LocaleContextHolder.setLocale(Locale.ENGLISH)
    }

    @Test
    fun shouldNotValidateWhenUrlIsInvalid() {
        val dto = ShortenRequest("bad url")

        val constraintViolations = validator.validate(dto)

        assertThat(constraintViolations.size).isEqualTo(1)
        val violation = constraintViolations.iterator().next()
        assertThat(violation.propertyPath.toString()).isEqualTo("url")
    }

    @Test
    fun shouldValidateWhenOk() {
        val dto = ShortenRequest("http://google.com")

        val constraintViolations = validator.validate(dto)

        assertThat(constraintViolations.size).isEqualTo(0)
    }

}
