package project.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateDataException extends Exception {

    String message;
    HttpStatus status;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        message = "Duplicate Data Found";
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
