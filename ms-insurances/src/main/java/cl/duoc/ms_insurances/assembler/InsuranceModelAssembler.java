package cl.duoc.ms_insurances.assembler;

import cl.duoc.ms_insurances.controller.InsuranceController;
import cl.duoc.ms_insurances.dto.InsuranceResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("unchecked")
public class InsuranceModelAssembler extends RepresentationModelAssemblerSupport<InsuranceResponseDto, EntityModel<InsuranceResponseDto>> {

    public InsuranceModelAssembler() {
        super(InsuranceController.class, (Class<EntityModel<InsuranceResponseDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<InsuranceResponseDto> toModel(InsuranceResponseDto insurance) {
        return EntityModel.of(insurance,
                linkTo(methodOn(InsuranceController.class).findById(insurance.getId())).withSelfRel(),
                linkTo(methodOn(InsuranceController.class).findAll()).withRel("insurances")
        );
    }
}
