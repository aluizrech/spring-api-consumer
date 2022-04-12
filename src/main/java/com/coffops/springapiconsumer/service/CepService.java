package com.coffops.springapiconsumer.service;

import com.coffops.springapiconsumer.model.Address;

public interface CepService {
    Address getAddress(String cep);
}
