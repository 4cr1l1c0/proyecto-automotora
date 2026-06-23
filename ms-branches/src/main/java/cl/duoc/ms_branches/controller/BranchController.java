package cl.duoc.ms_branches.controller;

import cl.duoc.ms_branches.assembler.BranchModelAssembler;
import cl.duoc.ms_branches.dto.BranchRequestDto;
import cl.duoc.ms_branches.dto.BranchResponseDto;
import cl.duoc.ms_branches.service.BranchService;
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
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
@Tag(name = "Sucursales", description = "Operaciones relacionadas con las sucursales de la automotora")
public class BranchController {

    private final BranchService service;
    private final BranchModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    @Operation(summary = "Obtener todas las sucursales", description = "Obtiene la lista completa de sucursales registradas")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<BranchResponseDto>>> findAll() {
        logger.info("GET /api/v1/branches");
        List<EntityModel<BranchResponseDto>> branches = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<BranchResponseDto>> collectionModel =
                CollectionModel.of(branches, linkTo(methodOn(BranchController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una sucursal por id", description = "Obtiene una sucursal a partir de su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<EntityModel<BranchResponseDto>> findById(
            @Parameter(description = "Identificador de la sucursal", required = true) @PathVariable Long id) {
        try {
            BranchResponseDto branch = service.findById(id);
            if (branch == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(branch));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear una sucursal", description = "Registra una nueva sucursal")
    @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente")
    public ResponseEntity<EntityModel<BranchResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la sucursal a crear", required = true)
            @Valid @RequestBody BranchRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.create(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una sucursal", description = "Actualiza los datos de una sucursal existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<EntityModel<BranchResponseDto>> update(
            @Parameter(description = "Identificador de la sucursal", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados de la sucursal", required = true)
            @Valid @RequestBody BranchRequestDto dto) {
        dto.setId(id);
        BranchResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sucursal", description = "Elimina una sucursal por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucursal eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identificador de la sucursal", required = true) @PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
