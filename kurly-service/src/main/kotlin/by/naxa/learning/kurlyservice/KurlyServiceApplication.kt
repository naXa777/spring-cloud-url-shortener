package by.naxa.learning.kurlyservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class KurlyServiceApplication

fun main(args: Array<String>) {
    runApplication<KurlyServiceApplication>(*args)
}
