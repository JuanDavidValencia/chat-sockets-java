package com.example.chat.businessLayer.businessExceptions;

public class OperacionNoAutorizadaException extends RuntimeException {

    public OperacionNoAutorizadaException(String mensaje){
        super(mensaje);
    }

}
