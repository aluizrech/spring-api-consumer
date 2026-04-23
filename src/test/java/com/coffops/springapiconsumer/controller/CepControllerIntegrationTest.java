package com.coffops.springapiconsumer.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CepControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String cep) {
        return "http://localhost:" + port + "/api/cep/" + cep;
    }

    // ── Good scenarios ────────────────────────────────────────────────────────

    @Test
    void shouldReturn200WithAddressForValidCep_88020231() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("88020231"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("88020-231");
        assertThat(response.getBody()).contains("SC");
        assertThat(response.getBody()).contains("Florianópolis");
        assertThat(response.getBody()).contains("Rua 13 de Maio");
    }

    @Test
    void shouldReturn200WithAddressForValidCep_88137250() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("88137250"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("88137-250");
        assertThat(response.getBody()).contains("SC");
        assertThat(response.getBody()).contains("Rua das Cerejeiras");
        assertThat(response.getBody()).contains("Pedra Branca");
        assertThat(response.getBody()).contains("Palhoça");
    }

    @Test
    void shouldReturn200WithAddressForValidCep_88142360() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("88142360"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("88142-360");
        assertThat(response.getBody()).contains("SC");
        assertThat(response.getBody()).contains("Santo Amaro da Imperatriz");
        assertThat(response.getBody()).contains("de 3115/3116 ao fim");
        assertThat(response.getBody()).contains("Rua São Sebastião");
    }

    // ── Bad scenarios ─────────────────────────────────────────────────────────

    @Test
    void shouldReturn404WhenCepDoesNotExist_88140000() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("88140000"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertEquals("{\"error\":\"CEP not found: 88140000\"}", response.getBody());
    }

    @Test
    void shouldReturn404WhenCepDoesNotExist_11140000() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("11140000"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertEquals("{\"error\":\"CEP not found: 11140000\"}", response.getBody());
    }

    @Test
    void shouldReturn400WhenCepFormatIsInvalid_111() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("111"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Invalid CEP format: 111");
    }
}
