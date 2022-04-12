package com.coffops.springapiconsumer.controller;

import com.coffops.springapiconsumer.model.Address;
import com.coffops.springapiconsumer.service.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cep")
public class CepController {

    private final CepService cepService;

    public CepController(@Autowired CepService cepService){
        this.cepService = cepService;
    }

    @GetMapping("/{cepSearch}")
    private ResponseEntity<?> getAddressInformation(@PathVariable("cepSearch") String cepSearch){
        Address address = cepService.getAddress(cepSearch);
        return ResponseEntity.ok(address);
    }

}
