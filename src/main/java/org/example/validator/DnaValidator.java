package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DnaValidator implements ConstraintValidator<ValidDna, String[]> {

    @Override
    public void initialize(ValidDna constraintAnnotation) {
        // Inicialización si fuera necesaria
    }

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        // 1. Validar que no sea nulo
        if (dna == null) {
            return false;
        }

        int n = dna.length;
        // 2. Validar que no esté vacío
        if (n == 0) {
            return false;
        }

        for (String row : dna) {
            // 3. Validar que la fila no sea nula
            if (row == null) {
                return false;
            }

            // 4. Validar que sea NxN (cuadrada)
            if (row.length() != n) {
                return false;
            }

            // 5. Validar caracteres (Solo A, T, C, G)
            // Explicación regex: ^ inicio, [ATCG]+ uno o más de estos, $ fin
            if (!row.matches("^[ATCG]+$")) {
                return false;
            }
        }

        return true; // Si pasa todo, es válido
    }
}