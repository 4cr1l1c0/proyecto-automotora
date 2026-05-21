package cl.duoc.ms_branches.service;

import cl.duoc.ms_branches.dto.BranchRequestDto;
import cl.duoc.ms_branches.dto.BranchResponseDto;

import java.util.List;

public interface BranchService {
    BranchResponseDto findById(Long id);
    List<BranchResponseDto> findAll();
    BranchResponseDto create(BranchRequestDto dto);
    BranchResponseDto update(BranchRequestDto dto);
    boolean deleteById(Long id);
}
