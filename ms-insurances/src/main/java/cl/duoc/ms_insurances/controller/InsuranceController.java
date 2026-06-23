package cl.duoc.ms_insurances.controller;

import cl.duoc.ms_insurances.assembler.InsuranceModelAssembler;
import cl.duoc.ms_insurances.dto.InsuranceRequestDto;
import cl.duoc.ms_insurances.dto.InsuranceResponseDto;
import cl.duoc.ms_insurances.service.InsuranceService;
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
@RequestMapping("/api/v1/insurances")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService service;
    private final InsuranceModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<InsuranceResponseDto>>> findAll() {
        logger.info("GET /api/v1/insurances");
        List<EntityModel<InsuranceResponseDto>> insurances = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<InsuranceResponseDto>> collectionModel =
                CollectionModel.of(insurances, linkTo(methodOn(InsuranceController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<InsuranceResponseDto>> findById(@PathVariable Long id) {
        try {
            InsuranceResponseDto insurance = service.findById(id);
            if (insurance == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(insurance));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<InsuranceResponseDto>> create(@Valid @RequestBody InsuranceRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.create(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<InsuranceResponseDto>> update(@PathVariable Long id, @Valid @RequestBody InsuranceRequestDto dto) {
        dto.setId(id);
        InsuranceResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
