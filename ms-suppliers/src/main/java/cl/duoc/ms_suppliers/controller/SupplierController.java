package cl.duoc.ms_suppliers.controller;

import cl.duoc.ms_suppliers.assembler.SupplierModelAssembler;
import cl.duoc.ms_suppliers.dto.SupplierRequestDto;
import cl.duoc.ms_suppliers.dto.SupplierResponseDto;
import cl.duoc.ms_suppliers.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService service;
    private final SupplierModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SupplierResponseDto>>> findAll() {
        logger.info("GET /api/v1/suppliers");
        List<EntityModel<SupplierResponseDto>> suppliers = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<SupplierResponseDto>> collectionModel =
                CollectionModel.of(suppliers, linkTo(methodOn(SupplierController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SupplierResponseDto>> findById(@PathVariable Long id) {
        try {
            SupplierResponseDto supplier = service.findById(id);
            if (supplier == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(supplier));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<SupplierResponseDto>> create(@Valid @RequestBody SupplierRequestDto dto) {
        SupplierResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<SupplierResponseDto>> update(@PathVariable Long id, @Valid @RequestBody SupplierRequestDto dto) {
        dto.setId(id);
        SupplierResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}

