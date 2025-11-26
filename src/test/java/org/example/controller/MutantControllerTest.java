package org.example.controller;

import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Test
    void shouldReturnOk_WhenIsMutant() throws Exception {
        // Simulamos que el servicio detecta un mutante
        when(mutantService.analyze(any())).thenReturn(true);

        String jsonRequest = "{\"dna\":[\"AAAA\",\"CCCC\",\"TCAG\",\"GGTC\"]}";

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk()); // Debe ser 200 OK
    }

    @Test
    void shouldReturnForbidden_WhenIsHuman() throws Exception {
        // Simulamos que el servicio detecta un humano
        when(mutantService.analyze(any())).thenReturn(false);

        String jsonRequest = "{\"dna\":[\"AAAT\",\"CCCT\",\"TCAG\",\"GGTC\"]}";

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden()); // Debe ser 403 Forbidden
    }

    @Test
    void shouldReturnBadRequest_WhenDnaIsInvalid() throws Exception {
        // Enviamos un ADN inválido (números) para disparar la validación @ValidDna
        String jsonRequest = "{\"dna\":[\"1234\",\"5678\"]}";

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest()); // Debe ser 400 Bad Request
    }

    @Test
    void shouldReturnStats() throws Exception {
        // Simulamos una respuesta de estadísticas
        StatsResponse mockStats = new StatsResponse(40, 100, 0.4);
        when(statsService.getStats()).thenReturn(mockStats);

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }
}