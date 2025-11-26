package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @Mock
    private MutantDetector mutantDetector;

    @InjectMocks
    private MutantService mutantService;

    @Test
    void shouldReturnCachedResult_WhenDnaAlreadyExists() {
        // ARRANGE: Simulamos que el ADN ya está guardado en BD y es mutante
        DnaRecord existingRecord = new DnaRecord("fakeHash", true);
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(existingRecord));

        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};

        // ACT
        boolean result = mutantService.analyze(dna);

        // ASSERT
        assertTrue(result);
        // Verificación clave: El detector NUNCA debió ejecutarse porque estaba en caché
        verify(mutantDetector, never()).isMutant(any());
        // Tampoco se debió guardar nada nuevo
        verify(repository, never()).save(any());
    }

    @Test
    void shouldAnalyzeAndSave_WhenDnaIsNew() {
        // ARRANGE: Simulamos que NO está en BD
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        // Simulamos que el detector real dice que es mutante
        when(mutantDetector.isMutant(any())).thenReturn(true);

        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};

        // ACT
        boolean result = mutantService.analyze(dna);

        // ASSERT
        assertTrue(result);
        // Verificación clave: Se llamó al detector y se guardó en BD
        verify(mutantDetector, times(1)).isMutant(any());
        verify(repository, times(1)).save(any(DnaRecord.class));
    }
}