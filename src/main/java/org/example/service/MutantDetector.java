package org.example.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {
    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        // --- AGREGA ESTO ---
        if (dna == null || dna.length == 0) {
            return false;
        }
        // -------------------

        int n = dna.length;

        // --- AGREGA ESTO TAMBIÉN (Validación de matriz cuadrada) ---
        if (dna[0] == null) return false; // Evita error si la primera fila es null
        // -----------------------------------------------------------

        // Optimización 1: Convertir a char[][] para acceso rápido
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            // Validación extra por seguridad
            if (dna[i] == null || dna[i].length() != n) return false;
            matrix[i] = dna[i].toCharArray();
        }

        int sequenceCount = 0;

        // Recorremos la matriz una sola vez
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Optimización 2: Early Termination
                if (sequenceCount > 1) return true;

                // Chequeo Horizontal
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkLine(matrix, row, col, 0, 1)) sequenceCount++;
                }

                // Chequeo Vertical
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkLine(matrix, row, col, 1, 0)) sequenceCount++;
                }

                // Chequeo Diagonal (↘)
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkLine(matrix, row, col, 1, 1)) sequenceCount++;
                }

                // Chequeo Diagonal Inversa (↙)
                if (row <= n - SEQUENCE_LENGTH && col >= SEQUENCE_LENGTH - 1) {
                    if (checkLine(matrix, row, col, 1, -1)) sequenceCount++;
                }
            }
        }
        return sequenceCount > 1;
    }

    private boolean checkLine(char[][] matrix, int row, int col, int dRow, int dCol) {
        char first = matrix[row][col];
        return matrix[row + dRow][col + dCol] == first &&
                matrix[row + 2 * dRow][col + 2 * dCol] == first &&
                matrix[row + 3 * dRow][col + 3 * dCol] == first;
    }
}