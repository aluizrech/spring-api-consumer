package com.coffops.springapiconsumer.exception;

public class InvalidCepException extends RuntimeException {
    public InvalidCepException(String cep) {
        super("Invalid CEP format: " + cep);
    }
}
