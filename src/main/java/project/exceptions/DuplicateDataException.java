package project.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateDataException extends Exception {

    String message;
    HttpStatus status;

    public DuplicateDataException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
