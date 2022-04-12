package com.coffops.springapiconsumer.service;

import com.coffops.springapiconsumer.model.Address;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService implements CepService {

    public Address getAddress(String cep) {
        String url = "https://viacep.com.br/ws/"+cep+"/json/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Address> response = restTemplate.exchange(url, HttpMethod.GET, null, Address.class);
        return response.getBody();
    }
}
