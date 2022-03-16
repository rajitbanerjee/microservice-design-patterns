package comp30910.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private int id;
    private int userId;
    private int movieId;
    private int cinemaId;
    private Date datetime;
    private int tickets;
    private String ticketType;
    private double amount;
}
