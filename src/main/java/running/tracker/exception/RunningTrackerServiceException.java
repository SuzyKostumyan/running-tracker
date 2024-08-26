package running.tracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class RunningTrackerServiceException extends ApiException {

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
    private final Map<Object, Object> payload;

    public RunningTrackerServiceException(final Map<Object, Object> payload) {
        this.code = ErrorMessage.APPLICATION_ERROR.getCode();
        this.message = ErrorMessage.APPLICATION_ERROR.getMessage();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.payload = payload;
    }

    public static RunningTrackerServiceException from(final Map<Object, Object> payload) {
        return new RunningTrackerServiceException(payload);
    }

    @Override
    public String toString() {
        return "RunningTrackerServiceException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", payload=" + payload +
                ", httpStatus=" + httpStatus +
                '}';
    }
}