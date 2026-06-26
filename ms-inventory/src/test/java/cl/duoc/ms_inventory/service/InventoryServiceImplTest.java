package cl.duoc.ms_inventory.service;

import cl.duoc.ms_inventory.dto.InventoryRequestDto;
import cl.duoc.ms_inventory.dto.InventoryResponseDto;
import cl.duoc.ms_inventory.exception.ResourceNotFoundException;
import cl.duoc.ms_inventory.exception.ServiceUnavailableException;
import cl.duoc.ms_inventory.feign.VehicleDto;
import cl.duoc.ms_inventory.feign.VehicleFeignClient;
import cl.duoc.ms_inventory.model.InventoryItem;
import cl.duoc.ms_inventory.repository.InventoryRepository;
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
class InventoryServiceImplTest {

    @Mock
    InventoryRepository repository;

    @Mock
    VehicleFeignClient vehicleFeignClient;

    @InjectMocks
    InventoryServiceImpl service;

    private InventoryItem inventoryItem;
    private InventoryRequestDto inventoryRequest;
    private VehicleDto vehicleDto;

    @BeforeEach
    void setUp() {
        inventoryItem = new InventoryItem(1L, 1L, 5, 2,
                "Bodega Central", LocalDate.of(2024, 1, 20));

        inventoryRequest = new InventoryRequestDto(null, 1L, 5, 2,
                "Bodega Central", LocalDate.of(2024, 1, 20));

        vehicleDto = new VehicleDto(1L, "1HGCM82633A123456", "Toyota", "Corolla",
                2022, "Blanco", 15000000.0, "disponible");
    }

    @Test
    void findById_WhenExists_ReturnsDtoWithVehicle() {
        when(repository.findById(1L)).thenReturn(Optional.of(inventoryItem));
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);

        InventoryResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(5, result.getQuantity());
        assertNotNull(result.getVehicle());
        assertEquals("Toyota", result.getVehicle().getBrand());
    }

    @Test
    void findById_WhenVehicleUnavailable_ReturnsDtoWithNullVehicle() {
        when(repository.findById(1L)).thenReturn(Optional.of(inventoryItem));
        when(vehicleFeignClient.findById(1L)).thenThrow(new ServiceUnavailableException("Servicio no disponible"));

        InventoryResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        assertNull(result.getVehicle());
    }

    @Test
    void findById_WhenNotExists_ReturnsNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        InventoryResponseDto result = service.findById(99L);

        assertNull(result);
    }

    @Test
    void findAll_ReturnsListWithVehicle() {
        when(repository.findAll()).thenReturn(List.of(inventoryItem));
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);

        List<InventoryResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Bodega Central", result.get(0).getLocation());
        assertNotNull(result.get(0).getVehicle());
    }

    @Test
    void findAll_WhenEmpty_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<InventoryResponseDto> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void create_WhenVehicleExists_SavesAndReturnsDto() {
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(repository.save(any(InventoryItem.class))).thenReturn(inventoryItem);

        InventoryResponseDto result = service.create(inventoryRequest);

        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        verify(repository, times(1)).save(any(InventoryItem.class));
    }

    @Test
    void create_WhenVehicleNotExists_ThrowsResourceNotFoundException() {
        when(vehicleFeignClient.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(inventoryRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void update_WhenVehicleExists_SavesAndReturnsDto() {
        inventoryRequest.setId(1L);
        when(vehicleFeignClient.findById(1L)).thenReturn(vehicleDto);
        when(repository.save(any(InventoryItem.class))).thenReturn(inventoryItem);

        InventoryResponseDto result = service.update(inventoryRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(InventoryItem.class));
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
