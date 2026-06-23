package cl.duoc.ms_test_drive.controller;

import cl.duoc.ms_test_drive.assembler.TestDriveModelAssembler;
import cl.duoc.ms_test_drive.dto.TestDriveRequestDto;
import cl.duoc.ms_test_drive.dto.TestDriveResponseDto;
import cl.duoc.ms_test_drive.service.TestDriveService;
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
@RequestMapping("/api/v1/test-drives")
@RequiredArgsConstructor
@Tag(name = "Test Drive", description = "Operaciones relacionadas con las pruebas de manejo")
public class TestDriveController {

    private final TestDriveService service;
    private final TestDriveModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    @Operation(summary = "Obtener todas las pruebas de manejo", description = "Obtiene la lista completa de pruebas de manejo registradas")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<TestDriveResponseDto>>> findAll() {
        logger.info("GET /api/v1/test-drives");
        List<EntityModel<TestDriveResponseDto>> testDrives = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TestDriveResponseDto>> collectionModel =
                CollectionModel.of(testDrives, linkTo(methodOn(TestDriveController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una prueba de manejo por id", description = "Obtiene una prueba de manejo a partir de su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prueba de manejo encontrada"),
            @ApiResponse(responseCode = "404", description = "Prueba de manejo no encontrada")
    })
    public ResponseEntity<EntityModel<TestDriveResponseDto>> findById(
            @Parameter(description = "Identificador de la prueba de manejo", required = true) @PathVariable Long id) {
        try {
            TestDriveResponseDto visit = service.findById(id);
            if (visit == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(visit));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear una prueba de manejo", description = "Registra una nueva prueba de manejo")
    @ApiResponse(responseCode = "201", description = "Prueba de manejo creada exitosamente")
    public ResponseEntity<EntityModel<TestDriveResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la prueba de manejo a crear", required = true)
            @Valid @RequestBody TestDriveRequestDto dto) {
        TestDriveResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una prueba de manejo", description = "Actualiza los datos de una prueba de manejo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prueba de manejo actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Prueba de manejo no encontrada")
    })
    public ResponseEntity<EntityModel<TestDriveResponseDto>> update(
            @Parameter(description = "Identificador de la prueba de manejo", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados de la prueba de manejo", required = true)
            @Valid @RequestBody TestDriveRequestDto dto) {
        dto.setId(id);
        TestDriveResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una prueba de manejo", description = "Elimina una prueba de manejo por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Prueba de manejo eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Prueba de manejo no encontrada")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identificador de la prueba de manejo", required = true) @PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}