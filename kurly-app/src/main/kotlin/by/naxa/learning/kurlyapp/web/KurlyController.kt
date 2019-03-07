package by.naxa.learning.kurlyapp.web

import by.naxa.learning.kurlyapp.service.KurlyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class KurlyController(@Autowired val service: KurlyService) {

    //@GetMapping()
    //fun shorten(): String = ""

}
