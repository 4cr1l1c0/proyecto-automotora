package cl.duoc.ms_branches.assembler;

import cl.duoc.ms_branches.controller.BranchController;
import cl.duoc.ms_branches.dto.BranchResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("unchecked")
public class BranchModelAssembler extends RepresentationModelAssemblerSupport<BranchResponseDto, EntityModel<BranchResponseDto>> {

    public BranchModelAssembler() {
        super(BranchController.class, (Class<EntityModel<BranchResponseDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<BranchResponseDto> toModel(BranchResponseDto branch) {
        return EntityModel.of(branch,
                linkTo(methodOn(BranchController.class).findById(branch.getId())).withSelfRel(),
                linkTo(methodOn(BranchController.class).findAll()).withRel("branches")
        );
    }
}
