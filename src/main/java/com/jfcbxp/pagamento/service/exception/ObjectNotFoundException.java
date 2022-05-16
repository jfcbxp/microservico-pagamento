package com.jfcbxp.pagamento.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3034438742020956856L;

    public ObjectNotFoundException(String message) {
        super(message);
    }
}