package cl.duoc.ms_vehicles.service;

import cl.duoc.ms_vehicles.dto.VehicleRequestDto;
import cl.duoc.ms_vehicles.dto.VehicleResponseDto;
import cl.duoc.ms_vehicles.model.Vehicle;
import cl.duoc.ms_vehicles.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    VehicleRepository repository;

    @InjectMocks
    VehicleServiceImpl service;

    private Vehicle vehicle;
    private VehicleRequestDto vehicleRequest;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle(1L, "1HGCM82633A123456", "Toyota", "Corolla",
                2022, "Blanco", 15000000.0, "disponible", "sedan", 0);

        vehicleRequest = new VehicleRequestDto(null, "1HGCM82633A123456", "Toyota", "Corolla",
                2022, "Blanco", 15000000.0, "disponible", "sedan", 0);
    }

    @Test
    void findById_WhenExists_ReturnsDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(vehicle));

        VehicleResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Toyota", result.getBrand());
        assertEquals("Corolla", result.getModel());
    }

    @Test
    void findById_WhenNotExists_ReturnsNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        VehicleResponseDto result = service.findById(99L);

        assertNull(result);
    }

    @Test
    void findAll_ReturnsListOfDtos() {
        when(repository.findAll()).thenReturn(List.of(vehicle));

        List<VehicleResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("1HGCM82633A123456", result.get(0).getVin());
    }

    @Test
    void findAll_WhenEmpty_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<VehicleResponseDto> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void create_SavesAndReturnsDto() {
        when(repository.save(any(Vehicle.class))).thenReturn(vehicle);

        VehicleResponseDto result = service.create(vehicleRequest);

        assertNotNull(result);
        assertEquals("Toyota", result.getBrand());
        verify(repository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void update_SavesAndReturnsDto() {
        vehicleRequest.setId(1L);
        when(repository.save(any(Vehicle.class))).thenReturn(vehicle);

        VehicleResponseDto result = service.update(vehicleRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(Vehicle.class));
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
