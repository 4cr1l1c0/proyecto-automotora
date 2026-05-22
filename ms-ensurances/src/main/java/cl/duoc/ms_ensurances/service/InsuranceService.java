package cl.duoc.ms_ensurances.service;

import cl.duoc.ms_ensurances.dto.InsuranceRequestDto;
import cl.duoc.ms_ensurances.dto.InsuranceResponseDto;

import java.util.List;

public interface InsuranceService {
    InsuranceResponseDto findById(Long id);
    List<InsuranceResponseDto> findAll();
    InsuranceResponseDto create(InsuranceRequestDto dto);
    InsuranceResponseDto update(InsuranceRequestDto dto);
    boolean deleteById(Long id);
}
