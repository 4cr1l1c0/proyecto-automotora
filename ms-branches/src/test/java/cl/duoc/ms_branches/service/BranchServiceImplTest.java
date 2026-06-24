package cl.duoc.ms_branches.service;

import cl.duoc.ms_branches.dto.BranchRequestDto;
import cl.duoc.ms_branches.dto.BranchResponseDto;
import cl.duoc.ms_branches.model.Branch;
import cl.duoc.ms_branches.repository.BranchRepository;
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
class BranchServiceImplTest {

    @Mock
    BranchRepository repository;

    @InjectMocks
    BranchServiceImpl service;

    private Branch branch;
    private BranchRequestDto branchRequest;

    @BeforeEach
    void setUp() {
        branch = new Branch(1L, "Sucursal Centro", "Av. Libertador 100",
                "222345678", "Región Metropolitana", "Santiago", true);

        branchRequest = new BranchRequestDto(null, "Sucursal Centro", "Av. Libertador 100",
                "222345678", "Región Metropolitana", "Santiago", true);
    }

    @Test
    void findById_WhenExists_ReturnsDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(branch));

        BranchResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Sucursal Centro", result.getName());
        assertEquals("Santiago", result.getCity());
    }

    @Test
    void findById_WhenNotExists_ReturnsNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        BranchResponseDto result = service.findById(99L);

        assertNull(result);
    }

    @Test
    void findAll_ReturnsListOfDtos() {
        when(repository.findAll()).thenReturn(List.of(branch));

        List<BranchResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Región Metropolitana", result.get(0).getRegion());
    }

    @Test
    void findAll_WhenEmpty_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<BranchResponseDto> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void create_SavesAndReturnsDto() {
        when(repository.save(any(Branch.class))).thenReturn(branch);

        BranchResponseDto result = service.create(branchRequest);

        assertNotNull(result);
        assertEquals("Sucursal Centro", result.getName());
        verify(repository, times(1)).save(any(Branch.class));
    }

    @Test
    void update_SavesAndReturnsDto() {
        branchRequest.setId(1L);
        when(repository.save(any(Branch.class))).thenReturn(branch);

        BranchResponseDto result = service.update(branchRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(Branch.class));
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
