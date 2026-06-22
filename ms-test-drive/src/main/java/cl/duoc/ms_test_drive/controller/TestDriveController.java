package cl.duoc.ms_test_drive.controller;

import cl.duoc.ms_test_drive.assembler.TestDriveModelAssembler;
import cl.duoc.ms_test_drive.dto.TestDriveRequestDto;
import cl.duoc.ms_test_drive.dto.TestDriveResponseDto;
import cl.duoc.ms_test_drive.service.TestDriveService;
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
public class TestDriveController {

    private final TestDriveService service;
    private final TestDriveModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
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
    public ResponseEntity<EntityModel<TestDriveResponseDto>> findById(@PathVariable Long id) {
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
    public ResponseEntity<EntityModel<TestDriveResponseDto>> create(@Valid @RequestBody TestDriveRequestDto dto) {
        TestDriveResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TestDriveResponseDto>> update(@PathVariable Long id, @Valid @RequestBody TestDriveRequestDto dto) {
        dto.setId(id);
        TestDriveResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}