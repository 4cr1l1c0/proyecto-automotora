package cl.duoc.ms_ensurances.controller;

import cl.duoc.ms_ensurances.dto.InsuranceRequestDto;
import cl.duoc.ms_ensurances.dto.InsuranceResponseDto;
import cl.duoc.ms_ensurances.service.InsuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/insurances")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<InsuranceResponseDto>> findAll() {
        logger.info("GET /api/v1/insurances");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceResponseDto> findById(@PathVariable Long id) {
        try {
            InsuranceResponseDto insurance = service.findById(id);
            if (insurance == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(insurance);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<InsuranceResponseDto> create(@Valid @RequestBody InsuranceRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceResponseDto> update(@PathVariable Long id, @Valid @RequestBody InsuranceRequestDto dto) {
        dto.setId(id);
        InsuranceResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
