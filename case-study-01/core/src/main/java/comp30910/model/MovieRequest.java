package comp30910.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieRequest {
    private String movieName;
    private String cinemaName;
}
