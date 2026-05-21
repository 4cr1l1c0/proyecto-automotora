package cl.duoc.ms_inventory.service;

import cl.duoc.ms_inventory.dto.InventoryRequestDto;
import cl.duoc.ms_inventory.dto.InventoryResponseDto;

import java.util.List;

public interface InventoryService {
    InventoryResponseDto findById(Long id);
    List<InventoryResponseDto> findAll();
    InventoryResponseDto create(InventoryRequestDto dto);
    InventoryResponseDto update(InventoryRequestDto dto);
    boolean deleteById(Long id);
}
