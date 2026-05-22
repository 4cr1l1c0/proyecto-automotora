package cl.duoc.ms_employees.service;

import cl.duoc.ms_employees.dto.EmployeeRequestDto;
import cl.duoc.ms_employees.dto.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseDto findById(Long id);
    List<EmployeeResponseDto> findAll();
    EmployeeResponseDto create(EmployeeRequestDto dto);
    EmployeeResponseDto update(EmployeeRequestDto dto);
    boolean deleteById(Long id);
}
