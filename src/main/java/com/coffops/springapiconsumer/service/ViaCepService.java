package com.coffops.springapiconsumer.service;

import com.coffops.springapiconsumer.exception.CepNotFoundException;
import com.coffops.springapiconsumer.exception.InvalidCepException;
import com.coffops.springapiconsumer.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestOperations;

import java.util.Map;

@Service
public class ViaCepService implements CepService {

    @Autowired
    private RestTemplate restTemplate;

    public Address getAddress(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        try {
            ResponseEntity<Map> raw = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);
            Map body = raw.getBody();
            if (body != null && Boolean.TRUE.equals(body.get("erro"))) {
                throw new CepNotFoundException(cep);
            }
            ResponseEntity<Address> response = restTemplate.exchange(url, HttpMethod.GET, null, Address.class);
            return response.getBody();
        } catch (HttpClientErrorException.BadRequest e) {
            throw new InvalidCepException(cep);
        }
    }
}
