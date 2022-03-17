package comp30910.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private String clientName;
    private String clientEmail;
    private String movieId;
    private String date;
    private String showTime;
    private int tickets;
    private String ticketType;
    private double amount;
}
