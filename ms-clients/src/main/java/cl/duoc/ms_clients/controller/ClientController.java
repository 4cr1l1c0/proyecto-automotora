package cl.duoc.ms_clients.controller;

import cl.duoc.ms_clients.assembler.ClientModelAssembler;
import cl.duoc.ms_clients.dto.ClientRequestDto;
import cl.duoc.ms_clients.dto.ClientResponseDto;
import cl.duoc.ms_clients.service.ClientService;
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
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes de la automotora")
public class ClientController {

    private final ClientService service;
    private final ClientModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Obtiene la lista completa de clientes registrados")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<ClientResponseDto>>> findAll() {
        logger.info("GET /api/v1/clients");
        List<EntityModel<ClientResponseDto>> clients = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ClientResponseDto>> collectionModel =
                CollectionModel.of(clients, linkTo(methodOn(ClientController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cliente por id", description = "Obtiene un cliente a partir de su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<EntityModel<ClientResponseDto>> findById(
            @Parameter(description = "Identificador del cliente", required = true) @PathVariable Long id) {
        try {
            ClientResponseDto client = service.findById(id);
            if (client == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(client));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un cliente", description = "Registra un nuevo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente")
    public ResponseEntity<EntityModel<ClientResponseDto>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del cliente a crear", required = true)
            @Valid @RequestBody ClientRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.create(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<EntityModel<ClientResponseDto>> update(
            @Parameter(description = "Identificador del cliente", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados del cliente", required = true)
            @Valid @RequestBody ClientRequestDto dto) {
        dto.setId(id);
        ClientResponseDto updated = service.update(dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identificador del cliente", required = true) @PathVariable Long id) {
        if (service.deleteById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
