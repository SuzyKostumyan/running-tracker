package running.tracker.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import running.tracker.exception.ApiException;
import running.tracker.exception.RunningTrackerServiceException;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<HttpErrorResponse> handleApiException(final ApiException ex) {
        log.warn("ApiException occurred: code={}, message={}, payload={}",
                ex.getCode(),
                ex.getMessage(),
                ex.getPayload()
        );
        return new ResponseEntity<>(HttpErrorResponse.from(ex), ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> handleException(final Exception ex) {
        var unexpectedException = RunningTrackerServiceException.from(new TreeMap<>());
        log.error("Running tracker service exception occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(HttpErrorResponse.from(unexpectedException), unexpectedException.getHttpStatus());
    }

    public record HttpErrorResponse(
            String error,
            Integer code,
            Map<Object, Object> payload
    ) implements Serializable {
        private static HttpErrorResponse from(final ApiException ex) {
            return new HttpErrorResponse(ex.getMessage(), ex.getCode(), ex.getPayload());
        }
    }
}