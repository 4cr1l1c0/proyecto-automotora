package cl.duoc.ms_suppliers.service;

import cl.duoc.ms_suppliers.dto.SupplierRequestDto;
import cl.duoc.ms_suppliers.dto.SupplierResponseDto;
import cl.duoc.ms_suppliers.model.Supplier;
import cl.duoc.ms_suppliers.repository.SupplierRepository;
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
class SupplierServiceImplTest {

    @Mock
    SupplierRepository repository;

    @InjectMocks
    SupplierServiceImpl service;

    private Supplier supplier;
    private SupplierRequestDto supplierRequest;

    @BeforeEach
    void setUp() {
        supplier = new Supplier(1L, "Proveedor Toyota SA", "76543210-K",
                "contacto@toyota.cl", "222987654", "Av. Industrial 500",
                "Carlos Muñoz", true);

        supplierRequest = new SupplierRequestDto(null, "Proveedor Toyota SA", "76543210-K",
                "contacto@toyota.cl", "222987654", "Av. Industrial 500",
                "Carlos Muñoz", true);
    }

    @Test
    void findById_WhenExists_ReturnsDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(supplier));

        SupplierResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Proveedor Toyota SA", result.getName());
        assertEquals("Carlos Muñoz", result.getContactName());
    }

    @Test
    void findById_WhenNotExists_ReturnsNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        SupplierResponseDto result = service.findById(99L);

        assertNull(result);
    }

    @Test
    void findAll_ReturnsListOfDtos() {
        when(repository.findAll()).thenReturn(List.of(supplier));

        List<SupplierResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("76543210-K", result.get(0).getRut());
    }

    @Test
    void findAll_WhenEmpty_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<SupplierResponseDto> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void create_SavesAndReturnsDto() {
        when(repository.save(any(Supplier.class))).thenReturn(supplier);

        SupplierResponseDto result = service.create(supplierRequest);

        assertNotNull(result);
        assertEquals("Proveedor Toyota SA", result.getName());
        verify(repository, times(1)).save(any(Supplier.class));
    }

    @Test
    void update_SavesAndReturnsDto() {
        supplierRequest.setId(1L);
        when(repository.save(any(Supplier.class))).thenReturn(supplier);

        SupplierResponseDto result = service.update(supplierRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(Supplier.class));
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
