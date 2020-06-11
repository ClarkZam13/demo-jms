package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionVentaException extends RuntimeException {

    private String codigoError;
    private String mensaje;

    public TransactionVentaException(String codigoError, String mensaje){
        this.codigoError = codigoError;
        this.mensaje = mensaje;
    }
}
