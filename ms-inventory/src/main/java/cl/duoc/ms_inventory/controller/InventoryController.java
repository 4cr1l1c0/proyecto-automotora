package cl.duoc.ms_inventory.controller;

import cl.duoc.ms_inventory.assembler.InventoryModelAssembler;
import cl.duoc.ms_inventory.dto.InventoryRequestDto;
import cl.duoc.ms_inventory.dto.InventoryResponseDto;
import cl.duoc.ms_inventory.service.InventoryService;
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
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;
    private final InventoryModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<InventoryResponseDto>>> findAll() {
        logger.info("GET /api/v1/inventory");
        List<EntityModel<InventoryResponseDto>> items = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<InventoryResponseDto>> collectionModel =
                CollectionModel.of(items,
                        linkTo(methodOn(InventoryController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<InventoryResponseDto>> findById(@PathVariable Long id) {
        try {
            InventoryResponseDto item = service.findById(id);
            if (item == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(item));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<InventoryResponseDto>> create(@Valid @RequestBody
                                                                    InventoryRequestDto dto) {
        InventoryResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<InventoryResponseDto>> update(@PathVariable Long id, @Valid
    @RequestBody InventoryRequestDto dto) {
        dto.setId(id);
        InventoryResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
