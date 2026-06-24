package cl.duoc.ms_employees.service;

import cl.duoc.ms_employees.dto.EmployeeRequestDto;
import cl.duoc.ms_employees.dto.EmployeeResponseDto;
import cl.duoc.ms_employees.exception.ResourceNotFoundException;
import cl.duoc.ms_employees.exception.ServiceUnavailableException;
import cl.duoc.ms_employees.feign.BranchDto;
import cl.duoc.ms_employees.feign.BranchFeignClient;
import cl.duoc.ms_employees.model.Employee;
import cl.duoc.ms_employees.repository.EmployeeRepository;
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
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository repository;

    @Mock
    BranchFeignClient branchFeignClient;

    @InjectMocks
    EmployeeServiceImpl service;

    private Employee employee;
    private EmployeeRequestDto employeeRequest;
    private BranchDto branchDto;

    @BeforeEach
    void setUp() {
        employee = new Employee(1L, "María", "López", "98765432-1",
                "maria@mail.com", "987654321", "Vendedora",
                800000.0, LocalDate.of(2020, 3, 1), true, 1L);

        employeeRequest = new EmployeeRequestDto(null, "María", "López", "98765432-1",
                "maria@mail.com", "987654321", "Vendedora",
                800000.0, LocalDate.of(2020, 3, 1), true, 1L);

        branchDto = new BranchDto(1L, "Sucursal Centro", "Av. Libertador 100", "Santiago", "RM");
    }

    @Test
    void findById_WhenExists_ReturnsDtoWithBranch() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        when(branchFeignClient.findById(1L)).thenReturn(branchDto);

        EmployeeResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("María", result.getFirstName());
        assertNotNull(result.getBranch());
        assertEquals("Sucursal Centro", result.getBranch().getName());
    }

    @Test
    void findById_WhenBranchUnavailable_ReturnsDtoWithNullBranch() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        when(branchFeignClient.findById(1L)).thenThrow(new ServiceUnavailableException("Servicio no disponible"));

        EmployeeResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals("María", result.getFirstName());
        assertNull(result.getBranch());
    }

    @Test
    void findById_WhenNotExists_ReturnsNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        EmployeeResponseDto result = service.findById(99L);

        assertNull(result);
    }

    @Test
    void findAll_ReturnsListWithBranch() {
        when(repository.findAll()).thenReturn(List.of(employee));
        when(branchFeignClient.findById(1L)).thenReturn(branchDto);

        List<EmployeeResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("López", result.get(0).getLastName());
        assertNotNull(result.get(0).getBranch());
    }

    @Test
    void findAll_WhenEmpty_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<EmployeeResponseDto> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void create_WhenBranchExists_SavesAndReturnsDto() {
        when(branchFeignClient.findById(1L)).thenReturn(branchDto);
        when(repository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponseDto result = service.create(employeeRequest);

        assertNotNull(result);
        assertEquals("María", result.getFirstName());
        verify(repository, times(1)).save(any(Employee.class));
    }

    @Test
    void create_WhenBranchNotExists_ThrowsResourceNotFoundException() {
        when(branchFeignClient.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(employeeRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void update_WhenBranchExists_SavesAndReturnsDto() {
        employeeRequest.setId(1L);
        when(branchFeignClient.findById(1L)).thenReturn(branchDto);
        when(repository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponseDto result = service.update(employeeRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(Employee.class));
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
