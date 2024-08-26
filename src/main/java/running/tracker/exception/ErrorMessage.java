package running.tracker.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    APPLICATION_ERROR("Running tracker service exception", 7000),
    NOT_FOUND("Not found", 7001);

    private final String message;
    private final Integer code;

    ErrorMessage(final String message, final Integer code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("Code: %d, Message: %s", code, message);
    }
}