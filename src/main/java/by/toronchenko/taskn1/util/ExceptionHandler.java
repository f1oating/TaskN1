package by.toronchenko.taskn1.util;

import by.toronchenko.taskn1.util.exception.NoCompanyFoundException;
import by.toronchenko.taskn1.util.exception.NoUserFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoUserFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoUserFoundException(NoUserFoundException u) {
        return "pageNotFound";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoCompanyFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoCompanyFoundException(NoCompanyFoundException c) {
        return "pageNotFound";
    }
}
