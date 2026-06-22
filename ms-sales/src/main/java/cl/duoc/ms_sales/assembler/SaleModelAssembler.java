package cl.duoc.ms_sales.assembler;

import cl.duoc.ms_sales.controller.SaleController;
import cl.duoc.ms_sales.dto.SaleResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("unchecked")
public class SaleModelAssembler extends RepresentationModelAssemblerSupport<SaleResponseDto, EntityModel<SaleResponseDto>> {

    private static final String CLIENTS_BASE_URL = "http://localhost:9001/api/v1/clients";
    private static final String VEHICLES_BASE_URL = "http://localhost:9004/api/v1/vehicles";
    private static final String EMPLOYEES_BASE_URL = "http://localhost:9003/api/v1/employees";

    public SaleModelAssembler() {
        super(SaleController.class, (Class<EntityModel<SaleResponseDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<SaleResponseDto> toModel(SaleResponseDto sale) {
        return EntityModel.of(sale,
                linkTo(methodOn(SaleController.class).findById(sale.getId())).withSelfRel(),
                linkTo(methodOn(SaleController.class).findAll()).withRel("sales"),
                Link.of(CLIENTS_BASE_URL + "/" + sale.getClientId(), "client"),
                Link.of(VEHICLES_BASE_URL + "/" + sale.getVehicleId(), "vehicle"),
                Link.of(EMPLOYEES_BASE_URL + "/" + sale.getEmployeeId(), "employee")
        );
    }
}