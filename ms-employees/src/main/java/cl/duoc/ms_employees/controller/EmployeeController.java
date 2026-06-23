package cl.duoc.ms_employees.controller;

import cl.duoc.ms_employees.assembler.EmployeeModelAssembler;
import cl.duoc.ms_employees.dto.EmployeeRequestDto;
import cl.duoc.ms_employees.dto.EmployeeResponseDto;
import cl.duoc.ms_employees.service.EmployeeService;
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
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Tag(name = "Empleados", description = "Operaciones relacionadas con los empleados de la automotora")
public class EmployeeController {

    private final EmployeeService service;
    private final EmployeeModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    @Operation(summary = "Obtener todos los empleados", description = "Obtiene la lista completa de empleados registrados")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<EmployeeResponseDto>>> findAll() {
        logger.info("GET /api/v1/employees");
        List<EntityModel<EmployeeResponseDto>> employees = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<EmployeeResponseDto>> collectionModel =
                CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un empleado por id", description = "Obtiene un empleado a partir de su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EntityModel<EmployeeResponseDto>> findById(
            @Parameter(description = "Identificador del empleado", required = true) @PathVariable Long id) {
        try {
            EmployeeResponseDto employee = service.findById(id);
            if (employee == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(employee));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un empleado", description = "Registra un nuevo empleado")
    @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente")
    public ResponseEntity<EntityModel<EmployeeResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del empleado a crear", required = true)
            @Valid @RequestBody EmployeeRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.create(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado", description = "Actualiza los datos de un empleado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EntityModel<EmployeeResponseDto>> update(
            @Parameter(description = "Identificador del empleado", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados del empleado", required = true)
            @Valid @RequestBody EmployeeRequestDto dto) {
        dto.setId(id);
        EmployeeResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado", description = "Elimina un empleado por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empleado eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identificador del empleado", required = true) @PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
