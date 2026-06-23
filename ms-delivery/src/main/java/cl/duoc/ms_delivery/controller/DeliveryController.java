package cl.duoc.ms_delivery.controller;

import cl.duoc.ms_delivery.assembler.DeliveryModelAssembler;
import cl.duoc.ms_delivery.dto.DeliveryRequestDto;
import cl.duoc.ms_delivery.dto.DeliveryResponseDto;
import cl.duoc.ms_delivery.service.DeliveryService;
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
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
@Tag(name = "Entregas", description = "Operaciones relacionadas con las entregas de vehículos")
public class DeliveryController {

    private final DeliveryService service;
    private final DeliveryModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    @Operation(summary = "Obtener todas las entregas", description = "Obtiene la lista completa de entregas registradas")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
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
    @Operation(summary = "Obtener una entrega por id", description = "Obtiene una entrega a partir de su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrega encontrada"),
            @ApiResponse(responseCode = "404", description = "Entrega no encontrada")
    })
    public ResponseEntity<EntityModel<DeliveryResponseDto>> findById(
            @Parameter(description = "Identificador de la entrega", required = true) @PathVariable Long id) {
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
    @Operation(summary = "Crear una entrega", description = "Registra una nueva entrega")
    @ApiResponse(responseCode = "201", description = "Entrega creada exitosamente")
    public ResponseEntity<EntityModel<DeliveryResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la entrega a crear", required = true)
            @Valid @RequestBody DeliveryRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.create(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una entrega", description = "Actualiza los datos de una entrega existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrega actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Entrega no encontrada")
    })
    public ResponseEntity<EntityModel<DeliveryResponseDto>> update(
            @Parameter(description = "Identificador de la entrega", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados de la entrega", required = true)
            @Valid @RequestBody DeliveryRequestDto dto) {
        dto.setId(id);
        DeliveryResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una entrega", description = "Elimina una entrega por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entrega eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Entrega no encontrada")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identificador de la entrega", required = true) @PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
