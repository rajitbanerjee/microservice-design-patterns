package comp30910.controller;

import comp30910.model.Cinema;
import comp30910.service.AggregatorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AggregatorController {
    private final AggregatorService aggregatorService;

    @GetMapping("/movie/list")
    public List<Cinema> cinemaList() {
        return aggregatorService.cinemaList(HttpMethod.GET, "/movie/list");
    }
}
