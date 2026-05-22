package cl.duoc.ms_sales.controller;

import cl.duoc.ms_sales.dto.SaleRequestDto;
import cl.duoc.ms_sales.dto.SaleResponseDto;
import cl.duoc.ms_sales.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<SaleResponseDto>> findAll() {
        logger.info("GET /api/v1/sales");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDto> findById(@PathVariable Long id) {
        try {
            SaleResponseDto sale = service.findById(id);
            if (sale == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(sale);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SaleResponseDto> create(@Valid @RequestBody SaleRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDto> update(@PathVariable Long id, @Valid @RequestBody SaleRequestDto dto) {
        dto.setId(id);
        SaleResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
