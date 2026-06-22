package cl.duoc.ms_suppliers.assembler;

import cl.duoc.ms_suppliers.controller.SupplierController;
import cl.duoc.ms_suppliers.dto.SupplierResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("unchecked")
public class SupplierModelAssembler extends RepresentationModelAssemblerSupport<SupplierResponseDto, EntityModel<SupplierResponseDto>> {

    public SupplierModelAssembler() {
        super(SupplierController.class, (Class<EntityModel<SupplierResponseDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<SupplierResponseDto> toModel(SupplierResponseDto supplier) {
        return EntityModel.of(supplier,
                linkTo(methodOn(SupplierController.class).findById(supplier.getId())).withSelfRel(),
                linkTo(methodOn(SupplierController.class).findAll()).withRel("suppliers")
        );
    }
}