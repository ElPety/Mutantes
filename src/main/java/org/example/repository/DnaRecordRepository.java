package org.example.repository;

import org.example.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {
    // Busca por hash para detectar duplicados [cite: 195]
    Optional<DnaRecord> findByDnaHash(String dnaHash);

    // Cuenta para las estadísticas [cite: 108]
    long countByIsMutant(boolean isMutant);
}