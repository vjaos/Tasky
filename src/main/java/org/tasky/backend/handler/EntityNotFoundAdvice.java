package org.tasky.backend.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tasky.backend.dto.response.MessageResponse;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Date;


@ControllerAdvice
public class EntityNotFoundAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    MessageResponse entityNotFoundExceptionHandler(EntityNotFoundException e) {
        MessageResponse messageResponse = new MessageResponse();

        Timestamp currentTimestamp = new Timestamp(new Date().getTime());

        messageResponse.setTimestamp(currentTimestamp);
        messageResponse.setMessage(e.getMessage());

        return messageResponse;
    }

}
