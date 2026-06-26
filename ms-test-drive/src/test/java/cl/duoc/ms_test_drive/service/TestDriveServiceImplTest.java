package cl.duoc.ms_test_drive.service;

import cl.duoc.ms_test_drive.dto.TestDriveRequestDto;
import cl.duoc.ms_test_drive.dto.TestDriveResponseDto;
import cl.duoc.ms_test_drive.exception.ResourceNotFoundException;
import cl.duoc.ms_test_drive.exception.ServiceUnavailableException;
import cl.duoc.ms_test_drive.feign.ClientDto;
import cl.duoc.ms_test_drive.feign.ClientFeignClient;
import cl.duoc.ms_test_drive.feign.VehicleDto;
import cl.duoc.ms_test_drive.feign.VehicleFeignClient;
import cl.duoc.ms_test_drive.model.TestDriveVisit;
import cl.duoc.ms_test_drive.repository.TestDriveRepository;
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
class TestDriveServiceImplTest {

    @Mock
    TestDriveRepository repository;

    @Mock
    ClientFeignClient clientFeignClient;

    @Mock
    VehicleFeignClient vehicleFeignClient;

    @InjectMocks
    TestDriveServiceImpl service;

    private TestDriveVisit testDriveVisit;
    private TestDriveRequestDto testDriveRequest;
    private ClientDto clientDto;
    private VehicleDto vehicleDto;

    @BeforeEach
    void setUp() {
        testDriveVisit = new TestDriveVisit(1L, 1L, 1L,
                LocalDate.of(2024, 3, 5),
                "Cliente interesado en compra", "agendado");

        testDriveRequest = new TestDriveRequestDto(null, 1L, 1L,
                LocalDate.of(2024, 3, 5),
                "Cliente interesado en compra", "agendado");

        clientDto = new ClientDto(1L, "12345678-9", "Juan", "Pérez",
                "González", "juan@mail.com", "912345678");

        vehicleDto = new VehicleDto(1L, "1HGCM82633A123456", "Toyota", "Corolla",
                2022, "Blanco", 15000000.0, "disponible");
    }

    @Test
    void findById_WhenExists_ReturnsDtoWithClientAndVehicle() {
        when(repository.findById(1L)).thenReturn(Optional.of(testDriveVisit));
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);

        TestDriveResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("agendado", result.getStatus());
        assertNotNull(result.getClient());
        assertNotNull(result.getVehicle());
    }

    @Test
    void findById_WhenClientUnavailable_ReturnsDtoWithNullClient() {
        when(repository.findById(1L)).thenReturn(Optional.of(testDriveVisit));
        when(clientFeignClient.findById(1L)).thenThrow(new ServiceUnavailableException("Servicio no disponible"));
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);

        TestDriveResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertNull(result.getClient());
        assertNotNull(result.getVehicle());
    }

    @Test
    void findById_WhenVehicleUnavailable_ReturnsDtoWithNullVehicle() {
        when(repository.findById(1L)).thenReturn(Optional.of(testDriveVisit));
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(vehicleFeignClient.findById(1L)).thenThrow(new ServiceUnavailableException("Servicio no disponible"));

        TestDriveResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getClient());
        assertNull(result.getVehicle());
    }

    @Test
    void findById_WhenNotExists_ReturnsNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        TestDriveResponseDto result = service.findById(99L);

        assertNull(result);
    }

    @Test
    void findAll_ReturnsListWithRelatedData() {
        when(repository.findAll()).thenReturn(List.of(testDriveVisit));
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);

        List<TestDriveResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("agendado", result.get(0).getStatus());
        assertNotNull(result.get(0).getClient());
        assertNotNull(result.get(0).getVehicle());
    }

    @Test
    void findAll_WhenEmpty_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<TestDriveResponseDto> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void create_WhenAllExist_SavesAndReturnsDto() {
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(repository.save(any(TestDriveVisit.class))).thenReturn(testDriveVisit);

        TestDriveResponseDto result = service.create(testDriveRequest);

        assertNotNull(result);
        assertEquals("agendado", result.getStatus());
        verify(repository, times(1)).save(any(TestDriveVisit.class));
    }

    @Test
    void create_WhenClientNotExists_ThrowsResourceNotFoundException() {
        when(clientFeignClient.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(testDriveRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void create_WhenVehicleNotExists_ThrowsResourceNotFoundException() {
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(vehicleFeignClient.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(testDriveRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void update_WhenAllExist_SavesAndReturnsDto() {
        testDriveRequest.setId(1L);
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(repository.save(any(TestDriveVisit.class))).thenReturn(testDriveVisit);

        TestDriveResponseDto result = service.update(testDriveRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(TestDriveVisit.class));
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
