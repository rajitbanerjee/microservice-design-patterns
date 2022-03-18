package comp30910.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/{name}")
    public String message(@PathVariable("name") String serviceName) {
        return String.format("Fallback: Circuit broken in %s!", serviceName);
    }
}
