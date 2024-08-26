package running.tracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Map;

@Getter
public class NotFoundException extends ApiException {

    private final Integer code;
    private final String message;
    private final Map<Object, Object> payload;
    private final HttpStatus httpStatus;

    public NotFoundException(final String message) {
        super();
        this.code = ErrorMessage.NOT_FOUND.getCode();
        this.message = message;
        this.payload = Collections.emptyMap();;
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    @Override
    public String toString() {
        return "NotFoundException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", payload=" + payload +
                ", httpStatus=" + httpStatus +
                '}';
    }
}