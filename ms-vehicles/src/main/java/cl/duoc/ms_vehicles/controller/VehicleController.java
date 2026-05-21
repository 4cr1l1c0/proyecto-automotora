package cl.duoc.ms_vehicles.controller;

import cl.duoc.ms_vehicles.dto.VehicleRequestDto;
import cl.duoc.ms_vehicles.dto.VehicleResponseDto;
import cl.duoc.ms_vehicles.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> findAll() {
        logger.info("GET /api/v1/vehicles");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> findById(@PathVariable Long id) {
        try {
            VehicleResponseDto vehicle = service.findById(id);
            if (vehicle == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(vehicle);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDto> create(@Valid @RequestBody VehicleRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> update(@PathVariable Long id, @Valid @RequestBody VehicleRequestDto dto) {
        dto.setId(id);
        VehicleResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
