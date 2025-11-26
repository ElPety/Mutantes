package org.example.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    @Test
    void shouldDetectMutantHorizontal() {
        String[] dna = {
                "AAAA", // 4 seguidas horizontal
                "CCCC", // 4 seguidas horizontal (ya van 2 -> mutante)
                "TCAG",
                "GGTC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void shouldDetectMutantVertical() {
        String[] dna = {
                "ATCG",
                "ATCG",
                "ATCG",
                "ATCG"  // 4 columnas verticales idénticas
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void shouldDetectMutantDiagonal() {
        String[] dna = {
                "ATCG", // A en 0,0
                "GAGG", // A en 1,1
                "TGAG", // A en 2,2
                "TGGA"  // A en 3,3 (Diagonal 1) + otras posibles
        };
        // Nota: para ser mutante necesita >1 secuencia. Asegúrate de probar un caso con 2 diagonales o 1 diagonal + 1 horizontal.
        // Aquí agregamos una horizontal para asegurar el positivo:
        String[] dnaMutant = {
                "ATCG",
                "GAGG",
                "TGAG",
                "AAAA" // Secuencia extra
        };
        assertTrue(detector.isMutant(dnaMutant));
    }

    @Test
    void shouldReturnFalseForHuman() {
        String[] dna = {
                "ATCG",
                "GATG",
                "TCAG",
                "GTCG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void shouldReturnFalseForNullOrEmpty() {
        assertFalse(detector.isMutant(null));
        assertFalse(detector.isMutant(new String[]{}));
    }

    @Test
    void shouldHandleNxM_InvalidMatrix() {
        // Aunque el validador @ValidDna filtra esto en el Controller,
        // el detector debe ser robusto por si se usa directo.
        String[] dna = {"AAA", "GCG"}; // 2 filas, longitud 3
        // Dependiendo de tu lógica en MutantDetector, esto devuelve false o lanza excepción.
        // Si pusiste la validación al inicio de isMutant:
        assertFalse(detector.isMutant(dna));
    }
}