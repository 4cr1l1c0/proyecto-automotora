package cl.duoc.ms_sales.controller;

import cl.duoc.ms_sales.assembler.SaleModelAssembler;
import cl.duoc.ms_sales.dto.SaleRequestDto;
import cl.duoc.ms_sales.dto.SaleResponseDto;
import cl.duoc.ms_sales.service.SaleService;
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
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
@Tag(name = "Ventas", description = "Operaciones relacionadas con las ventas de vehículos")
public class SaleController {

    private final SaleService service;
    private final SaleModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    @Operation(summary = "Obtener todas las ventas", description = "Obtiene la lista completa de ventas registradas")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<SaleResponseDto>>> findAll() {
        logger.info("GET /api/v1/sales");
        List<EntityModel<SaleResponseDto>> sales = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<SaleResponseDto>> collectionModel =
                CollectionModel.of(sales,
                        linkTo(methodOn(SaleController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una venta por id", description = "Obtiene una venta a partir de su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<EntityModel<SaleResponseDto>> findById(
            @Parameter(description = "Identificador de la venta", required = true) @PathVariable Long id) {
        try {
            SaleResponseDto sale = service.findById(id);
            if (sale == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(sale));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear una venta", description = "Registra una nueva venta")
    @ApiResponse(responseCode = "201", description = "Venta creada exitosamente")
    public ResponseEntity<EntityModel<SaleResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la venta a crear", required = true)
            @Valid @RequestBody SaleRequestDto dto) {
        SaleResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una venta", description = "Actualiza los datos de una venta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<EntityModel<SaleResponseDto>> update(
            @Parameter(description = "Identificador de la venta", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados de la venta", required = true)
            @Valid @RequestBody SaleRequestDto dto) {
        dto.setId(id);
        SaleResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una venta", description = "Elimina una venta por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identificador de la venta", required = true) @PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}

