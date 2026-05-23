package cl.duoc.ms_employees.service;

import cl.duoc.ms_employees.dto.EmployeeRequestDto;
import cl.duoc.ms_employees.dto.EmployeeResponseDto;
import cl.duoc.ms_employees.exception.ResourceNotFoundException;
import cl.duoc.ms_employees.feign.BranchDto;
import cl.duoc.ms_employees.feign.BranchFeignClient;
import cl.duoc.ms_employees.model.Employee;
import cl.duoc.ms_employees.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final BranchFeignClient branchFeignClient;

    private EmployeeResponseDto toDto(Employee entity) {
        return new EmployeeResponseDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getRut(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getPosition(),
                entity.getSalary(),
                entity.getHireDate(),
                entity.getActive(),
                entity.getBranchId(),
                null
        );
    }

    private Employee toEntity(EmployeeRequestDto dto) {
        return new Employee(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getRut(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getPosition(),
                dto.getSalary(),
                dto.getHireDate(),
                dto.getActive(),
                dto.getBranchId()
        );
    }

    private void validateForeignKeys(EmployeeRequestDto dto) {
        BranchDto branch = null;
        try { branch = branchFeignClient.findById(dto.getBranchId()); } catch (Exception ignored) {}
        if (branch == null) throw new ResourceNotFoundException("Sucursal con id " + dto.getBranchId() + " no existe");
    }

    @Override
    public EmployeeResponseDto findById(Long id) {
        return repository.findById(id).map(employee -> {
            EmployeeResponseDto dto = toDto(employee);
            try {
                dto.setBranch(branchFeignClient.findById(employee.getBranchId()));
            } catch (Exception e) {
                log.warn("Could not fetch branch data for employee {}: {}", id, e.getMessage());
            }
            return dto;
        }).orElse(null);
    }

    @Override
    public List<EmployeeResponseDto> findAll() {
        log.info("Fetching all employees");
        return repository.findAll().stream().map(employee -> {
            EmployeeResponseDto dto = toDto(employee);
            try {
                dto.setBranch(branchFeignClient.findById(employee.getBranchId()));
            } catch (Exception e) {
                log.warn("Could not fetch branch data for employee {}: {}", employee.getId(), e.getMessage());
            }
            return dto;
        }).toList();
    }

    @Override
    public EmployeeResponseDto create(EmployeeRequestDto dto) {
        validateForeignKeys(dto);
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public EmployeeResponseDto update(EmployeeRequestDto dto) {
        validateForeignKeys(dto);
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
