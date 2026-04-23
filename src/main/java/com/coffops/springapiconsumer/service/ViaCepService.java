package com.coffops.springapiconsumer.service;

import com.coffops.springapiconsumer.exception.CepNotFoundException;
import com.coffops.springapiconsumer.exception.InvalidCepException;
import com.coffops.springapiconsumer.model.Address;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ViaCepService implements CepService {

    private final RestTemplate restTemplate;

    public ViaCepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Address getAddress(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        try {
            ResponseEntity<Map<String, Object>> raw = restTemplate.exchange(url, HttpMethod.GET, null, new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {});
            Map<String, Object> body = raw.getBody();
            if (body != null && Boolean.TRUE.equals(body.get("erro"))) {
                throw new CepNotFoundException(cep);
            }
            ResponseEntity<Address> response = restTemplate.exchange(url, HttpMethod.GET, null, Address.class);
            Address address = response.getBody();
            if (address == null || address.getZipCode() == null) {
                throw new CepNotFoundException(cep);
            }
            return address;
        } catch (HttpClientErrorException.BadRequest e) {
            throw new InvalidCepException(cep);
        }
    }
}
