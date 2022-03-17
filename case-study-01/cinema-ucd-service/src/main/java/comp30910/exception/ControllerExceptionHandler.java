package comp30910.exception;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {
    // 400
    @ExceptionHandler({
        IllegalArgumentException.class,
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage error400(Exception ex, WebRequest request) {
        return standardErrorMessage(
                HttpStatus.BAD_REQUEST, Arrays.asList(ex.getMessage()), request);
    }

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage error404(Exception ex, WebRequest request) {
        return standardErrorMessage(HttpStatus.NOT_FOUND, Arrays.asList(ex.getMessage()), request);
    }

    // 500
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage error500(Exception ex, WebRequest request) {
        return standardErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR, Arrays.asList(ex.getMessage()), request);
    }

    private ErrorMessage standardErrorMessage(
            HttpStatus status, List<String> messages, WebRequest request) {
        return new ErrorMessage(
                status.value(), status.name(), new Date(), messages, request.getDescription(false));
    }
}
