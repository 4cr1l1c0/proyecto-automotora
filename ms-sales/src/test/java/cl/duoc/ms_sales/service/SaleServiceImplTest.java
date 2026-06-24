package cl.duoc.ms_sales.service;

import cl.duoc.ms_sales.dto.SaleRequestDto;
import cl.duoc.ms_sales.dto.SaleResponseDto;
import cl.duoc.ms_sales.exception.ResourceNotFoundException;
import cl.duoc.ms_sales.exception.ServiceUnavailableException;
import cl.duoc.ms_sales.feign.ClientDto;
import cl.duoc.ms_sales.feign.ClientFeignClient;
import cl.duoc.ms_sales.feign.EmployeeDto;
import cl.duoc.ms_sales.feign.EmployeeFeignClient;
import cl.duoc.ms_sales.feign.VehicleDto;
import cl.duoc.ms_sales.feign.VehicleFeignClient;
import cl.duoc.ms_sales.model.SaleModel;
import cl.duoc.ms_sales.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {

    @Mock
    SaleRepository repository;

    @Mock
    ClientFeignClient clientFeignClient;

    @Mock
    VehicleFeignClient vehicleFeignClient;

    @Mock
    EmployeeFeignClient employeeFeignClient;

    @InjectMocks
    SaleServiceImpl service;

    private SaleModel saleModel;
    private SaleRequestDto saleRequest;
    private ClientDto clientDto;
    private VehicleDto vehicleDto;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        saleModel = new SaleModel(1L, LocalDate.of(2024, 1, 15),
                12605042.0, 2394957.98, 15000000.0,
                "contado", 1L, 1L, 1L);

        saleRequest = new SaleRequestDto(null, LocalDate.of(2024, 1, 15),
                12605042.0, 2394957.98, 15000000.0,
                "contado", 1L, 1L, 1L);

        clientDto = new ClientDto(1L, "12345678-9", "Juan", "Pérez", "González",
                "juan@mail.com", "912345678");

        vehicleDto = new VehicleDto(1L, "1HGCM82633A123456", "Toyota", "Corolla",
                2022, "Blanco", 15000000.0, "disponible");

        employeeDto = new EmployeeDto(1L, "María", "López", "98765432-1", "Vendedora");
    }

    @Test
    void findById_WhenExists_ReturnsDtoWithClient() {
        when(repository.findById(1L)).thenReturn(Optional.of(saleModel));
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);

        SaleResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(15000000.0, result.getTotal());
        assertNotNull(result.getClient());
        assertEquals("Juan", result.getClient().getPrimerNombre());
    }

    @Test
    void findById_WhenClientUnavailable_ReturnsDtoWithNullClient() {
        when(repository.findById(1L)).thenReturn(Optional.of(saleModel));
        when(clientFeignClient.findById(1L)).thenThrow(new ServiceUnavailableException("Servicio no disponible"));

        SaleResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNull(result.getClient());
    }

    @Test
    void findById_WhenNotExists_ReturnsNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        SaleResponseDto result = service.findById(99L);

        assertNull(result);
    }

    @Test
    void findAll_ReturnsListWithClient() {
        when(repository.findAll()).thenReturn(List.of(saleModel));
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);

        List<SaleResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("contado", result.get(0).getPaymentType());
        assertNotNull(result.get(0).getClient());
    }

    @Test
    void findAll_WhenEmpty_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<SaleResponseDto> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void create_WhenAllExist_SavesAndReturnsDto() {
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(employeeFeignClient.findById(1L)).thenReturn(employeeDto);
        when(repository.save(any(SaleModel.class))).thenReturn(saleModel);

        SaleResponseDto result = service.create(saleRequest);

        assertNotNull(result);
        assertEquals(15000000.0, result.getTotal());
        verify(repository, times(1)).save(any(SaleModel.class));
    }

    @Test
    void create_WhenClientNotExists_ThrowsResourceNotFoundException() {
        when(clientFeignClient.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(saleRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void create_WhenVehicleNotExists_ThrowsResourceNotFoundException() {
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(vehicleFeignClient.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(saleRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void create_WhenEmployeeNotExists_ThrowsResourceNotFoundException() {
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(employeeFeignClient.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(saleRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void update_WhenAllExist_SavesAndReturnsDto() {
        saleRequest.setId(1L);
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(employeeFeignClient.findById(1L)).thenReturn(employeeDto);
        when(repository.save(any(SaleModel.class))).thenReturn(saleModel);

        SaleResponseDto result = service.update(saleRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(SaleModel.class));
    }

    @Test
    void deleteById_WhenExists_ReturnsTrue() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        boolean result = service.deleteById(1L);

        assertTrue(result);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_WhenNotExists_ReturnsFalse() {
        when(repository.existsById(99L)).thenReturn(false);

        boolean result = service.deleteById(99L);

        assertFalse(result);
        verify(repository, never()).deleteById(any());
    }
}
