package cl.duoc.ms_inventory.assembler;

import cl.duoc.ms_inventory.controller.InventoryController;
import cl.duoc.ms_inventory.dto.InventoryResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("unchecked")
public class InventoryModelAssembler extends RepresentationModelAssemblerSupport<InventoryResponseDto,
        EntityModel<InventoryResponseDto>> {

    private static final String VEHICLES_BASE_URL = "http://localhost:9004/api/v1/vehicles";

    public InventoryModelAssembler() {
        super(InventoryController.class, (Class<EntityModel<InventoryResponseDto>>) (Class<?>)
                EntityModel.class);
    }

    @Override
    public EntityModel<InventoryResponseDto> toModel(InventoryResponseDto item) {
        return EntityModel.of(item,
                linkTo(methodOn(InventoryController.class).findById(item.getId())).withSelfRel(),
                linkTo(methodOn(InventoryController.class).findAll()).withRel("inventory"),
                Link.of(VEHICLES_BASE_URL + "/" + item.getVehicleId(), "vehicle")
        );
    }
}