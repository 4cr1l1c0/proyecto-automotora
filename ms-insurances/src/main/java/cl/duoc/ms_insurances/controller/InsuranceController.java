package cl.duoc.ms_insurances.controller;

import cl.duoc.ms_insurances.assembler.InsuranceModelAssembler;
import cl.duoc.ms_insurances.dto.InsuranceRequestDto;
import cl.duoc.ms_insurances.dto.InsuranceResponseDto;
import cl.duoc.ms_insurances.service.InsuranceService;
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
@RequestMapping("/api/v1/insurances")
@RequiredArgsConstructor
@Tag(name = "Seguros", description = "Operaciones relacionadas con los seguros de vehículos")
public class InsuranceController {

    private final InsuranceService service;
    private final InsuranceModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    @Operation(summary = "Obtener todos los seguros", description = "Obtiene la lista completa de seguros registrados")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
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
    @Operation(summary = "Obtener un seguro por id", description = "Obtiene un seguro a partir de su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguro encontrado"),
            @ApiResponse(responseCode = "404", description = "Seguro no encontrado")
    })
    public ResponseEntity<EntityModel<InsuranceResponseDto>> findById(
            @Parameter(description = "Identificador del seguro", required = true) @PathVariable Long id) {
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
    @Operation(summary = "Crear un seguro", description = "Registra un nuevo seguro")
    @ApiResponse(responseCode = "201", description = "Seguro creado exitosamente")
    public ResponseEntity<EntityModel<InsuranceResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del seguro a crear", required = true)
            @Valid @RequestBody InsuranceRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.create(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un seguro", description = "Actualiza los datos de un seguro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguro actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Seguro no encontrado")
    })
    public ResponseEntity<EntityModel<InsuranceResponseDto>> update(
            @Parameter(description = "Identificador del seguro", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados del seguro", required = true)
            @Valid @RequestBody InsuranceRequestDto dto) {
        dto.setId(id);
        InsuranceResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un seguro", description = "Elimina un seguro por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Seguro eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Seguro no encontrado")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identificador del seguro", required = true) @PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
