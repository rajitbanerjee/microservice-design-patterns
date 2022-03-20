package comp30910.model;

import lombok.Data;

@Data
public class RequestMessage {
    private String endpoint;
    private Object body;
}
