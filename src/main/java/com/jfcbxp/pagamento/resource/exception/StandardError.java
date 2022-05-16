package com.jfcbxp.pagamento.resource.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = -415637564469786085L;
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
}