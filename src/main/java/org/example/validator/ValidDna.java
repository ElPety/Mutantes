package org.example.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DnaValidator.class) // Conecta con la lógica
@Target({ElementType.FIELD})                  // Se usa sobre campos
@Retention(RetentionPolicy.RUNTIME)           // Funciona en tiempo de ejecución
public @interface ValidDna {
    String message() default "Secuencia de ADN inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}