package cl.duoc.ms_sales.service;

import cl.duoc.ms_sales.dto.SaleRequestDto;
import cl.duoc.ms_sales.dto.SaleResponseDto;

import java.util.List;

public interface SaleService {
    SaleResponseDto findById(Long id);
    List<SaleResponseDto> findAll();
    SaleResponseDto create(SaleRequestDto dto);
    SaleResponseDto update(SaleRequestDto dto);
    boolean deleteById(Long id);
}
