package cl.duoc.ms_employees.assembler;

import cl.duoc.ms_employees.controller.EmployeeController;
import cl.duoc.ms_employees.dto.EmployeeResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("unchecked")
public class EmployeeModelAssembler extends RepresentationModelAssemblerSupport<EmployeeResponseDto, EntityModel<EmployeeResponseDto>> {

    public EmployeeModelAssembler() {
        super(EmployeeController.class, (Class<EntityModel<EmployeeResponseDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<EmployeeResponseDto> toModel(EmployeeResponseDto employee) {
        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).findById(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees")
        );
    }
}
