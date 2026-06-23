package cl.duoc.ms_delivery.assembler;

import cl.duoc.ms_delivery.controller.DeliveryController;
import cl.duoc.ms_delivery.dto.DeliveryResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("unchecked")
public class DeliveryModelAssembler extends RepresentationModelAssemblerSupport<DeliveryResponseDto, EntityModel<DeliveryResponseDto>> {

    public DeliveryModelAssembler() {
        super(DeliveryController.class, (Class<EntityModel<DeliveryResponseDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<DeliveryResponseDto> toModel(DeliveryResponseDto delivery) {
        return EntityModel.of(delivery,
                linkTo(methodOn(DeliveryController.class).findById(delivery.getId())).withSelfRel(),
                linkTo(methodOn(DeliveryController.class).findAll()).withRel("deliveries")
        );
    }
}
