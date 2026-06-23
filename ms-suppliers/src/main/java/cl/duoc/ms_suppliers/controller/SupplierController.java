package cl.duoc.ms_suppliers.controller;

import cl.duoc.ms_suppliers.assembler.SupplierModelAssembler;
import cl.duoc.ms_suppliers.dto.SupplierRequestDto;
import cl.duoc.ms_suppliers.dto.SupplierResponseDto;
import cl.duoc.ms_suppliers.service.SupplierService;
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
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
@Tag(name = "Proveedores", description = "Operaciones relacionadas con los proveedores de la automotora")
public class SupplierController {

    private final SupplierService service;
    private final SupplierModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    @Operation(summary = "Obtener todos los proveedores", description = "Obtiene la lista completa de proveedores registrados")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
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
    @Operation(summary = "Obtener un proveedor por id", description = "Obtiene un proveedor a partir de su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<EntityModel<SupplierResponseDto>> findById(
            @Parameter(description = "Identificador del proveedor", required = true) @PathVariable Long id) {
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
    @Operation(summary = "Crear un proveedor", description = "Registra un nuevo proveedor")
    @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente")
    public ResponseEntity<EntityModel<SupplierResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del proveedor a crear", required = true)
            @Valid @RequestBody SupplierRequestDto dto) {
        SupplierResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un proveedor", description = "Actualiza los datos de un proveedor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<EntityModel<SupplierResponseDto>> update(
            @Parameter(description = "Identificador del proveedor", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados del proveedor", required = true)
            @Valid @RequestBody SupplierRequestDto dto) {
        dto.setId(id);
        SupplierResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un proveedor", description = "Elimina un proveedor por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Proveedor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identificador del proveedor", required = true) @PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}

