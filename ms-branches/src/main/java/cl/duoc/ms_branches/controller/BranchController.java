package cl.duoc.ms_branches.controller;

import cl.duoc.ms_branches.dto.BranchRequestDto;
import cl.duoc.ms_branches.dto.BranchResponseDto;
import cl.duoc.ms_branches.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<BranchResponseDto>> findAll() {
        logger.info("GET /api/v1/branches");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponseDto> findById(@PathVariable Long id) {
        try {
            BranchResponseDto branch = service.findById(id);
            if (branch == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(branch);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BranchResponseDto> create(@Valid @RequestBody BranchRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponseDto> update(@PathVariable Long id, @Valid @RequestBody BranchRequestDto dto) {
        dto.setId(id);
        BranchResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
