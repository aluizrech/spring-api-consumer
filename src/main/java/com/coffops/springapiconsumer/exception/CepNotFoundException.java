package com.coffops.springapiconsumer.exception;

public class CepNotFoundException extends RuntimeException {
    public CepNotFoundException(String cep) {
        super("CEP not found: " + cep);
    }
}
