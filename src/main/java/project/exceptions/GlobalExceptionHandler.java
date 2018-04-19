package project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CryptoDataNotFoundException.class)
    public @ResponseBody
    ExceptionResponse handleError(CryptoDataNotFoundException e) {
        ExceptionResponse stats = new ExceptionResponse();
        //assigns message on the UserService class to the ExceptionResponse class
        stats.setMessage(e.getMessage());
        //set HttpStatus accordingly
        stats.setStatus(HttpStatus.UNAUTHORIZED);
        return stats;
    }

    @ExceptionHandler(DuplicateDataException.class)
    public @ResponseBody ExceptionResponse handleError(DuplicateDataException e) {
        ExceptionResponse stats = new ExceptionResponse();
        stats.setMessage(e.getMessage());
        stats.setStatus(HttpStatus.CONFLICT);
        return stats;
    }

}
