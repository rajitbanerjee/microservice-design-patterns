package comp30910.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private String movieName;
    private String cinemaName;
    private Date date;
    private String time;
    private Integer numTickets;
    private String ticketCategory;
    private Double totalFarePaid;
    private String clientName;
    private String clientEmail;
}
