package com.coffops.springapiconsumer.controller;

import com.coffops.springapiconsumer.exception.CepNotFoundException;
import com.coffops.springapiconsumer.exception.InvalidCepException;
import com.coffops.springapiconsumer.model.Address;
import com.coffops.springapiconsumer.service.CepService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CepController.class)
class CepControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CepService cepService;

    private Address buildAddress(String cep, String street, String neighborhood, String localidade, String state) {
        Address address = new Address();
        address.setZipCode(cep);
        address.setStreet(street);
        address.setComplement("");
        address.setNeighborhood(neighborhood);
        address.setCity(localidade);
        address.setState(state);
        return address;
    }

    // ── Good scenarios ────────────────────────────────────────────────────────

    @Test
    void shouldReturn200WithAddressForValidCep_88020231() throws Exception {
        Address address = buildAddress("88020-231", "Rua Marechal Guilherme", "Centro", "Florianópolis", "SC");
        when(cepService.getAddress("88020231")).thenReturn(address);

        mockMvc.perform(get("/api/cep/88020231").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value("88020-231"))
                .andExpect(jsonPath("$.localidade").value("Florianópolis"))
                .andExpect(jsonPath("$.uf").value("SC"));
    }

    @Test
    void shouldReturn200WithAddressForValidCep_88137250() throws Exception {
        Address address = buildAddress("88137-250", "Rua das Flores", "Jardim das Flores", "Palhoça", "SC");
        when(cepService.getAddress("88137250")).thenReturn(address);

        mockMvc.perform(get("/api/cep/88137250").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value("88137-250"))
                .andExpect(jsonPath("$.localidade").value("Palhoça"))
                .andExpect(jsonPath("$.uf").value("SC"));
    }

    @Test
    void shouldReturn200WithAddressForValidCep_88142360() throws Exception {
        Address address = buildAddress("88142-360", "Rua das Acácias", "Bela Vista", "São José", "SC");
        when(cepService.getAddress("88142360")).thenReturn(address);

        mockMvc.perform(get("/api/cep/88142360").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value("88142-360"))
                .andExpect(jsonPath("$.localidade").value("São José"))
                .andExpect(jsonPath("$.uf").value("SC"));
    }

    // ── Bad scenarios ─────────────────────────────────────────────────────────

    @Test
    void shouldReturn404WhenCepDoesNotExist_88140000() throws Exception {
        when(cepService.getAddress("88140000")).thenThrow(new CepNotFoundException("88140000"));

        mockMvc.perform(get("/api/cep/88140000").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("CEP not found: 88140000"));
    }

    @Test
    void shouldReturn404WhenCepDoesNotExist_11140000() throws Exception {
        when(cepService.getAddress("11140000")).thenThrow(new CepNotFoundException("11140000"));

        mockMvc.perform(get("/api/cep/11140000").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("CEP not found: 11140000"));
    }

    @Test
    void shouldReturn400WhenCepFormatIsInvalid_111() throws Exception {
        when(cepService.getAddress("111")).thenThrow(new InvalidCepException("111"));

        mockMvc.perform(get("/api/cep/111").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid CEP format: 111"));
    }
}
