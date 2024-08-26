package running.tracker.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public abstract class ApiException extends RuntimeException {

    public abstract Integer getCode();

    public abstract String getMessage();

    public abstract Map<Object, Object> getPayload();

    public abstract HttpStatus getHttpStatus();
}