package comp30910.controller;

import comp30910.model.Movie;
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
    public List<Movie> movieList() {
        return aggregatorService.movieList(HttpMethod.GET, "/movie/list");
    }
}
