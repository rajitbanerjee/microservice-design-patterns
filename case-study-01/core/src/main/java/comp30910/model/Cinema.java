package comp30910.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cinema {
    private String id;
    private String cinemaName;
    private List<Movie> movies;
}
