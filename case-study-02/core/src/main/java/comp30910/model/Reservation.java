package comp30910.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation implements Serializable {
    private String clientName;
    private String clientEmail;
    private String movieId;
    private String date;
    private String showTime;
    private Integer tickets;
    private String ticketType;
    private Double amount;
}
