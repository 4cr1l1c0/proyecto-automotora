package cl.duoc.ms_suppliers.service;

import cl.duoc.ms_suppliers.dto.SupplierRequestDto;
import cl.duoc.ms_suppliers.dto.SupplierResponseDto;

import java.util.List;

public interface SupplierService {
    SupplierResponseDto findById(Long id);
    List<SupplierResponseDto> findAll();
    SupplierResponseDto create(SupplierRequestDto dto);
    SupplierResponseDto update(SupplierRequestDto dto);
    boolean deleteById(Long id);
}
