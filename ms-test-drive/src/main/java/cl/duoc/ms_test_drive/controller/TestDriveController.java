package cl.duoc.ms_test_drive.controller;

import cl.duoc.ms_test_drive.dto.TestDriveRequestDto;
import cl.duoc.ms_test_drive.dto.TestDriveResponseDto;
import cl.duoc.ms_test_drive.service.TestDriveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test-drives")
@RequiredArgsConstructor
public class TestDriveController {

    private final TestDriveService service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<TestDriveResponseDto>> findAll() {
        logger.info("GET /api/v1/test-drives");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDriveResponseDto> findById(@PathVariable Long id) {
        try {
            TestDriveResponseDto visit = service.findById(id);
            if (visit == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(visit);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TestDriveResponseDto> create(@Valid @RequestBody TestDriveRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestDriveResponseDto> update(@PathVariable Long id, @Valid @RequestBody TestDriveRequestDto dto) {
        dto.setId(id);
        TestDriveResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
