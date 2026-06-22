package cl.duoc.ms_test_drive.assembler;

import cl.duoc.ms_test_drive.controller.TestDriveController;
import cl.duoc.ms_test_drive.dto.TestDriveResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
@SuppressWarnings("unchecked")
public class TestDriveModelAssembler extends RepresentationModelAssemblerSupport<TestDriveResponseDto, EntityModel<TestDriveResponseDto>> {

    private static final String VEHICLES_BASE_URL = "http://localhost:9004/api/v1/vehicles";
    private static final String CLIENTS_BASE_URL = "http://localhost:9001/api/v1/clients";

    public TestDriveModelAssembler() {
        super(TestDriveController.class, (Class<EntityModel<TestDriveResponseDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<TestDriveResponseDto> toModel(TestDriveResponseDto testDrive) {
        return EntityModel.of(testDrive,
                linkTo(methodOn(TestDriveController.class).findById(testDrive.getId())).withSelfRel(),
                linkTo(methodOn(TestDriveController.class).findAll()).withRel("test-drives"),
                Link.of(VEHICLES_BASE_URL + "/" + testDrive.getVehicleId(), "vehicle"),
                Link.of(CLIENTS_BASE_URL + "/" + testDrive.getClientId(), "client")
        );
    }
}