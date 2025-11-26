package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@RequestBody @Valid DnaRequest request) {
        boolean isMutant = mutantService.analyze(request.getDna());
        if (isMutant) {
            return ResponseEntity.ok().build(); // 200 OK si es mutante [cite: 26, 54]
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 si es humano [cite: 26, 55]
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats()); // [cite: 30, 64]
    }
}