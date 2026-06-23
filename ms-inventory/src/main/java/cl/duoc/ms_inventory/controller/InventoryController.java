package cl.duoc.ms_inventory.controller;

import cl.duoc.ms_inventory.assembler.InventoryModelAssembler;
import cl.duoc.ms_inventory.dto.InventoryRequestDto;
import cl.duoc.ms_inventory.dto.InventoryResponseDto;
import cl.duoc.ms_inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Inventario", description = "Operaciones relacionadas con el inventario de vehículos")
public class InventoryController {

    private final InventoryService service;
    private final InventoryModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    @Operation(summary = "Obtener todo el inventario", description = "Obtiene la lista completa de ítems de inventario registrados")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
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
    @Operation(summary = "Obtener un ítem de inventario por id", description = "Obtiene un ítem de inventario a partir de su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem encontrado"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    public ResponseEntity<EntityModel<InventoryResponseDto>> findById(
            @Parameter(description = "Identificador del ítem de inventario", required = true) @PathVariable Long id) {
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
    @Operation(summary = "Crear un ítem de inventario", description = "Registra un nuevo ítem en el inventario")
    @ApiResponse(responseCode = "201", description = "Ítem creado exitosamente")
    public ResponseEntity<EntityModel<InventoryResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del ítem de inventario a crear", required = true)
            @Valid @RequestBody InventoryRequestDto dto) {
        InventoryResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un ítem de inventario", description = "Actualiza los datos de un ítem de inventario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    public ResponseEntity<EntityModel<InventoryResponseDto>> update(
            @Parameter(description = "Identificador del ítem de inventario", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados del ítem de inventario", required = true)
            @Valid @RequestBody InventoryRequestDto dto) {
        dto.setId(id);
        InventoryResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un ítem de inventario", description = "Elimina un ítem de inventario por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ítem eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identificador del ítem de inventario", required = true) @PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
