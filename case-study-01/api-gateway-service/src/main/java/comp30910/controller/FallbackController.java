package comp30910.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/movies")
    public String movies() {
        return "Fallaback: Circuit broken in movies-service!";
    }

    @GetMapping("/reservations")
    public String reservations() {
        return "Fallback: Circuit broken in reservations-service!";
    }
}
