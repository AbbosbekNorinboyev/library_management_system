package uz.pdp.library_management_system.exception;

public class CustomizedRequestException extends RuntimeException {
    private int code;

    private int httpResponseCode;

    public CustomizedRequestException(String message, int code, int httpResponseCode) {
        super(message);
        this.code = code;
        this.httpResponseCode = httpResponseCode;
    }
}
