package cl.duoc.ms_delivery.service;

import cl.duoc.ms_delivery.dto.DeliveryRequestDto;
import cl.duoc.ms_delivery.dto.DeliveryResponseDto;
import cl.duoc.ms_delivery.exception.ResourceNotFoundException;
import cl.duoc.ms_delivery.exception.ServiceUnavailableException;
import cl.duoc.ms_delivery.feign.ClientDto;
import cl.duoc.ms_delivery.feign.ClientFeignClient;
import cl.duoc.ms_delivery.feign.SaleDto;
import cl.duoc.ms_delivery.feign.SaleFeignClient;
import cl.duoc.ms_delivery.model.Delivery;
import cl.duoc.ms_delivery.repository.DeliveryRepository;
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
class DeliveryServiceImplTest {

    @Mock
    DeliveryRepository repository;

    @Mock
    SaleFeignClient saleFeignClient;

    @Mock
    ClientFeignClient clientFeignClient;

    @InjectMocks
    DeliveryServiceImpl service;

    private Delivery delivery;
    private DeliveryRequestDto deliveryRequest;
    private SaleDto saleDto;
    private ClientDto clientDto;

    @BeforeEach
    void setUp() {
        delivery = new Delivery(1L, 1L, 1L,
                LocalDate.of(2024, 2, 10),
                "Av. Siempre Viva 742", "pendiente", "Sin observaciones");

        deliveryRequest = new DeliveryRequestDto(null, 1L, 1L,
                LocalDate.of(2024, 2, 10),
                "Av. Siempre Viva 742", "pendiente", "Sin observaciones");

        saleDto = new SaleDto(1L, LocalDate.of(2024, 1, 15),
                15000000.0, "contado", 1L, 1L, 1L);

        clientDto = new ClientDto(1L, "12345678-9", "Juan", "Pérez",
                "González", "juan@mail.com", "912345678");
    }

    @Test
    void findById_WhenExists_ReturnsDtoWithSaleAndClient() {
        when(repository.findById(1L)).thenReturn(Optional.of(delivery));
        when(saleFeignClient.findById(1L)).thenReturn(saleDto);
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);

        DeliveryResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("pendiente", result.getStatus());
        assertNotNull(result.getSale());
        assertNotNull(result.getClient());
    }

    @Test
    void findById_WhenSaleUnavailable_ReturnsDtoWithNullSale() {
        when(repository.findById(1L)).thenReturn(Optional.of(delivery));
        when(saleFeignClient.findById(1L)).thenThrow(new ServiceUnavailableException("Servicio no disponible"));
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);

        DeliveryResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertNull(result.getSale());
        assertNotNull(result.getClient());
    }

    @Test
    void findById_WhenClientUnavailable_ReturnsDtoWithNullClient() {
        when(repository.findById(1L)).thenReturn(Optional.of(delivery));
        when(saleFeignClient.findById(1L)).thenReturn(saleDto);
        when(clientFeignClient.findById(1L)).thenThrow(new ServiceUnavailableException("Servicio no disponible"));

        DeliveryResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getSale());
        assertNull(result.getClient());
    }

    @Test
    void findById_WhenNotExists_ReturnsNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        DeliveryResponseDto result = service.findById(99L);

        assertNull(result);
    }

    @Test
    void findAll_ReturnsListWithRelatedData() {
        when(repository.findAll()).thenReturn(List.of(delivery));
        when(saleFeignClient.findById(1L)).thenReturn(saleDto);
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);

        List<DeliveryResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Av. Siempre Viva 742", result.get(0).getDeliveryAddress());
        assertNotNull(result.get(0).getSale());
        assertNotNull(result.get(0).getClient());
    }

    @Test
    void findAll_WhenEmpty_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<DeliveryResponseDto> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void create_WhenAllExist_SavesAndReturnsDto() {
        when(saleFeignClient.findById(1L)).thenReturn(saleDto);
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(repository.save(any(Delivery.class))).thenReturn(delivery);

        DeliveryResponseDto result = service.create(deliveryRequest);

        assertNotNull(result);
        assertEquals("pendiente", result.getStatus());
        verify(repository, times(1)).save(any(Delivery.class));
    }

    @Test
    void create_WhenSaleNotExists_ThrowsResourceNotFoundException() {
        when(saleFeignClient.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(deliveryRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void create_WhenClientNotExists_ThrowsResourceNotFoundException() {
        when(saleFeignClient.findById(1L)).thenReturn(saleDto);
        when(clientFeignClient.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(deliveryRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void update_WhenAllExist_SavesAndReturnsDto() {
        deliveryRequest.setId(1L);
        when(saleFeignClient.findById(1L)).thenReturn(saleDto);
        when(clientFeignClient.findById(1L)).thenReturn(clientDto);
        when(repository.save(any(Delivery.class))).thenReturn(delivery);

        DeliveryResponseDto result = service.update(deliveryRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(Delivery.class));
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
