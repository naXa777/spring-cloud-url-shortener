package by.naxa.learning.kurlyapp.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class KurlyService(@Autowired val restTemplate: RestTemplate) {

    @HystrixCommand(fallbackMethod = "unknown")
    fun shorten(): String? = restTemplate.getForEntity("http://kurly-service/shorten", String::class.java).body

    @Suppress("unused")
    fun unknown(): String = "unknown"

}
