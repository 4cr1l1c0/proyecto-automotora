package cl.duoc.ms_employees.controller;

import cl.duoc.ms_employees.assembler.EmployeeModelAssembler;
import cl.duoc.ms_employees.dto.EmployeeRequestDto;
import cl.duoc.ms_employees.dto.EmployeeResponseDto;
import cl.duoc.ms_employees.service.EmployeeService;
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
public class EmployeeController {

    private final EmployeeService service;
    private final EmployeeModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
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
    public ResponseEntity<EntityModel<EmployeeResponseDto>> findById(@PathVariable Long id) {
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
    public ResponseEntity<EntityModel<EmployeeResponseDto>> create(@Valid @RequestBody EmployeeRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.create(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EmployeeResponseDto>> update(@PathVariable Long id, @Valid @RequestBody EmployeeRequestDto dto) {
        dto.setId(id);
        EmployeeResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
