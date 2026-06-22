package cl.duoc.ms_vehicles.assembler;

import cl.duoc.ms_vehicles.controller.VehicleController;
import cl.duoc.ms_vehicles.dto.VehicleResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("unchecked")
public class VehicleModelAssembler extends RepresentationModelAssemblerSupport<VehicleResponseDto, EntityModel<VehicleResponseDto>> {

    public VehicleModelAssembler() {
        super(VehicleController.class, (Class<EntityModel<VehicleResponseDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<VehicleResponseDto> toModel(VehicleResponseDto vehicle) {
        return EntityModel.of(vehicle,
                linkTo(methodOn(VehicleController.class).findById(vehicle.getId())).withSelfRel(),
                linkTo(methodOn(VehicleController.class).findAll()).withRel("vehicles")
        );
    }
}