package cl.duoc.ms_clients.assembler;

import cl.duoc.ms_clients.controller.ClientController;
import cl.duoc.ms_clients.dto.ClientResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("unchecked")
public class ClientModelAssembler extends RepresentationModelAssemblerSupport<ClientResponseDto, EntityModel<ClientResponseDto>> {

    public ClientModelAssembler() {
        super(ClientController.class, (Class<EntityModel<ClientResponseDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<ClientResponseDto> toModel(ClientResponseDto client) {
        return EntityModel.of(client,
                linkTo(methodOn(ClientController.class).findById(client.getId())).withSelfRel(),
                linkTo(methodOn(ClientController.class).findAll()).withRel("clients")
        );
    }
}
