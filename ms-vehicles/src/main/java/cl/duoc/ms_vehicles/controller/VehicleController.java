package cl.duoc.ms_vehicles.controller;

import cl.duoc.ms_vehicles.assembler.VehicleModelAssembler;
import cl.duoc.ms_vehicles.dto.VehicleRequestDto;
import cl.duoc.ms_vehicles.dto.VehicleResponseDto;
import cl.duoc.ms_vehicles.service.VehicleService;
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
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehículos", description = "Operaciones relacionadas con el catálogo de vehículos")
public class VehicleController {

    private final VehicleService service;
    private final VehicleModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    @Operation(summary = "Obtener todos los vehículos", description = "Obtiene la lista completa de vehículos registrados")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<VehicleResponseDto>>> findAll() {
        logger.info("GET /api/v1/vehicles");
        List<EntityModel<VehicleResponseDto>> vehicles = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<VehicleResponseDto>> collectionModel =
                CollectionModel.of(vehicles, linkTo(methodOn(VehicleController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un vehículo por id", description = "Obtiene un vehículo a partir de su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    public ResponseEntity<EntityModel<VehicleResponseDto>> findById(
            @Parameter(description = "Identificador del vehículo", required = true) @PathVariable Long id) {
        try {
            VehicleResponseDto vehicle = service.findById(id);
            if (vehicle == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(vehicle));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un vehículo", description = "Registra un nuevo vehículo en el catálogo")
    @ApiResponse(responseCode = "201", description = "Vehículo creado exitosamente")
    public ResponseEntity<VehicleResponseDto> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del vehículo a crear", required = true)
            @Valid @RequestBody VehicleRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un vehículo", description = "Actualiza los datos de un vehículo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    public ResponseEntity<VehicleResponseDto> update(
            @Parameter(description = "Identificador del vehículo", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados del vehículo", required = true)
            @Valid @RequestBody VehicleRequestDto dto) {
        dto.setId(id);
        VehicleResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un vehículo", description = "Elimina un vehículo por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehículo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identificador del vehículo", required = true) @PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
