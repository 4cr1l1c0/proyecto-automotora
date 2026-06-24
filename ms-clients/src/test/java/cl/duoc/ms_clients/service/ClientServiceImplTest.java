package cl.duoc.ms_clients.service;

import cl.duoc.ms_clients.dto.ClientRequestDto;
import cl.duoc.ms_clients.dto.ClientResponseDto;
import cl.duoc.ms_clients.model.ClientModel;
import cl.duoc.ms_clients.repository.ClientRepository;
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
class ClientServiceImplTest {

    @Mock
    ClientRepository repository;

    @InjectMocks
    ClientServiceImpl service;

    private ClientModel clientModel;
    private ClientRequestDto clientRequest;

    @BeforeEach
    void setUp() {
        clientModel = new ClientModel(
                1L, "12345678-9", "Juan", "Carlos",
                "Pérez", "González", "juan@mail.com",
                "912345678", "Av. Principal 123",
                LocalDate.of(1990, 5, 15), true
        );

        clientRequest = new ClientRequestDto(
                null, "12345678-9", "Juan", "Carlos",
                "Pérez", "González", "juan@mail.com",
                "912345678", "Av. Principal 123",
                LocalDate.of(1990, 5, 15), true
        );
    }

    @Test
    void findById_WhenExists_ReturnsDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(clientModel));

        ClientResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getPrimerNombre());
        assertEquals("12345678-9", result.getRut());
    }

    @Test
    void findById_WhenNotExists_ReturnsNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ClientResponseDto result = service.findById(99L);

        assertNull(result);
    }

    @Test
    void findAll_ReturnsListOfDtos() {
        when(repository.findAll()).thenReturn(List.of(clientModel));

        List<ClientResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Pérez", result.get(0).getApellidoPaterno());
    }

    @Test
    void findAll_WhenEmpty_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<ClientResponseDto> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void create_SavesAndReturnsDto() {
        when(repository.save(any(ClientModel.class))).thenReturn(clientModel);

        ClientResponseDto result = service.create(clientRequest);

        assertNotNull(result);
        assertEquals("juan@mail.com", result.getEmail());
        verify(repository, times(1)).save(any(ClientModel.class));
    }

    @Test
    void update_SavesAndReturnsDto() {
        clientRequest.setId(1L);
        when(repository.save(any(ClientModel.class))).thenReturn(clientModel);

        ClientResponseDto result = service.update(clientRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(ClientModel.class));
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
