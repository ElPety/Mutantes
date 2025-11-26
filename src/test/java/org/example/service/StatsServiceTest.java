package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private StatsService statsService;

    @Test
    void shouldCalculateRatioCorrectly() {
        // Caso normal: 40 mutantes, 100 humanos
        when(repository.countByIsMutant(true)).thenReturn(40L);
        when(repository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();

        // 40 / 100 = 0.4
        assertEquals(40, response.getCount_mutant_dna());
        assertEquals(100, response.getCount_human_dna());
        assertEquals(0.4, response.getRatio());
    }

    @Test
    void shouldHandleDivisionByZero_WhenNoHumans() {
        // Caso borde: 10 mutantes, 0 humanos
        when(repository.countByIsMutant(true)).thenReturn(10L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        // Ratio debe ser 0.0 (o lo que hayas definido) para evitar error matemático
        assertEquals(0, response.getRatio());
    }
}