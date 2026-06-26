package cl.duoc.ms_insurances.service;

import cl.duoc.ms_insurances.dto.InsuranceRequestDto;
import cl.duoc.ms_insurances.dto.InsuranceResponseDto;
import cl.duoc.ms_insurances.exception.ResourceNotFoundException;
import cl.duoc.ms_insurances.exception.ServiceUnavailableException;
import cl.duoc.ms_insurances.feign.ClientDto;
import cl.duoc.ms_insurances.feign.ClientFeignClient;
import cl.duoc.ms_insurances.feign.VehicleDto;
import cl.duoc.ms_insurances.feign.VehicleFeignClient;
import cl.duoc.ms_insurances.model.Insurance;
import cl.duoc.ms_insurances.repository.InsuranceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsuranceServiceImplTest {

    @Mock
    InsuranceRepository repository;

    @Mock
    ClientFeignClient clientFeignClient;

    @Mock
    VehicleFeignClient vehicleFeignClient;

    @InjectMocks
    InsuranceServiceImpl service;

    private Insurance insurance;
    private InsuranceRequestDto insuranceRequest;
    private VehicleDto vehicleDto;
    private ClientDto clientDto;

    @BeforeEach
    void setUp() {
        insurance = new Insurance(1L, "POL-001", "todo riesgo",
                new BigDecimal("50000000"), new BigDecimal("150000"),
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1),
                1L, 1L, true);

        insuranceRequest = new InsuranceRequestDto(null, "POL-001", "todo riesgo",
                new BigDecimal("50000000"), new BigDecimal("150000"),
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1),
                1L, 1L, true);

        vehicleDto = new VehicleDto(1L, "1HGCM82633A123456", "Toyota", "Corolla",
                2022, "Blanco", 15000000.0, "disponible", "sedan", 0);

        clientDto = new ClientDto(1L, "12345678-9", "Juan", "Carlos",
                "Pérez", "González", "juan@mail.com",
                "912345678", "Av. Principal 123",
                LocalDate.of(1990, 5, 15), true);
    }

    @Test
    void findById_WhenExists_ReturnsDtoWithVehicleAndClient() {
        when(repository.findById(1L)).thenReturn(Optional.of(insurance));
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);

        InsuranceResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("POL-001", result.getPolicyNumber());
        assertNotNull(result.getVehicle());
        assertNotNull(result.getClient());
    }

    @Test
    void findById_WhenVehicleUnavailable_ReturnsDtoWithNullVehicle() {
        when(repository.findById(1L)).thenReturn(Optional.of(insurance));
        when(vehicleFeignClient.findById(1L)).thenThrow(new ServiceUnavailableException("Servicio no disponible"));
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);

        InsuranceResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertNull(result.getVehicle());
        assertNotNull(result.getClient());
    }

    @Test
    void findById_WhenClientUnavailable_ReturnsDtoWithNullClient() {
        when(repository.findById(1L)).thenReturn(Optional.of(insurance));
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(clientFeignClient.findById(1L)).thenThrow(new ServiceUnavailableException("Servicio no disponible"));

        InsuranceResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getVehicle());
        assertNull(result.getClient());
    }

    @Test
    void findById_WhenNotExists_ReturnsNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        InsuranceResponseDto result = service.findById(99L);

        assertNull(result);
    }

    @Test
    void findAll_ReturnsListWithRelatedData() {
        when(repository.findAll()).thenReturn(List.of(insurance));
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);

        List<InsuranceResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("todo riesgo", result.get(0).getType());
        assertNotNull(result.get(0).getVehicle());
        assertNotNull(result.get(0).getClient());
    }

    @Test
    void findAll_WhenEmpty_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<InsuranceResponseDto> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void create_WhenAllExist_SavesAndReturnsDto() {
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(repository.save(any(Insurance.class))).thenReturn(insurance);

        InsuranceResponseDto result = service.create(insuranceRequest);

        assertNotNull(result);
        assertEquals("POL-001", result.getPolicyNumber());
        verify(repository, times(1)).save(any(Insurance.class));
    }

    @Test
    void create_WhenVehicleNotExists_ThrowsResourceNotFoundException() {
        when(vehicleFeignClient.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(insuranceRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void create_WhenClientNotExists_ThrowsResourceNotFoundException() {
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(clientFeignClient.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(insuranceRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void update_WhenAllExist_SavesAndReturnsDto() {
        insuranceRequest.setId(1L);
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(repository.save(any(Insurance.class))).thenReturn(insurance);

        InsuranceResponseDto result = service.update(insuranceRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(Insurance.class));
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
