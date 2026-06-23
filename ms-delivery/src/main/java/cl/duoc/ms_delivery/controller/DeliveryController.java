package cl.duoc.ms_delivery.controller;

import cl.duoc.ms_delivery.assembler.DeliveryModelAssembler;
import cl.duoc.ms_delivery.dto.DeliveryRequestDto;
import cl.duoc.ms_delivery.dto.DeliveryResponseDto;
import cl.duoc.ms_delivery.service.DeliveryService;
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
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService service;
    private final DeliveryModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DeliveryResponseDto>>> findAll() {
        logger.info("GET /api/v1/deliveries");
        List<EntityModel<DeliveryResponseDto>> deliveries = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<DeliveryResponseDto>> collectionModel =
                CollectionModel.of(deliveries, linkTo(methodOn(DeliveryController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DeliveryResponseDto>> findById(@PathVariable Long id) {
        try {
            DeliveryResponseDto delivery = service.findById(id);
            if (delivery == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(delivery));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<DeliveryResponseDto>> create(@Valid @RequestBody DeliveryRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.create(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DeliveryResponseDto>> update(@PathVariable Long id, @Valid @RequestBody DeliveryRequestDto dto) {
        dto.setId(id);
        DeliveryResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
