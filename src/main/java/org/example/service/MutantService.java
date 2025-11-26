package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository repository;

    public boolean analyze(String[] dna) {
        // 1. Calcular Hash único del ADN
        String hash = generateHash(dna);

        // 2. Verificar si ya existe en BD (Caché) [cite: 195]
        Optional<DnaRecord> existingRecord = repository.findByDnaHash(hash);
        if (existingRecord.isPresent()) {
            return existingRecord.get().isMutant();
        }

        // 3. Si no existe, analizar
        boolean isMutant = mutantDetector.isMutant(dna);

        // 4. Guardar resultado
        repository.save(new DnaRecord(hash, isMutant));

        return isMutant;
    }

    private String generateHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String combined = String.join("", dna);
            byte[] encodedhash = digest.digest(combined.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular hash", e);
        }
    }
}