package by.naxa.learning.turbine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.turbine.EnableTurbine

@EnableTurbine
@EnableEurekaClient
@SpringBootApplication
class TurbineApplication

fun main(args: Array<String>) {
    runApplication<TurbineApplication>(*args)
}
