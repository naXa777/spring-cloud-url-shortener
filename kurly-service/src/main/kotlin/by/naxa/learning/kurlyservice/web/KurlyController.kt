package by.naxa.learning.kurlyservice.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class KurlyController {

    @GetMapping("/shorten")
    fun shorten(): String {
        // TODO implement
        return ""
    }

}
