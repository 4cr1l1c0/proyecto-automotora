package cl.duoc.ms_vehicles.service;

import cl.duoc.ms_vehicles.dto.VehicleRequestDto;
import cl.duoc.ms_vehicles.dto.VehicleResponseDto;

import java.util.List;

public interface VehicleService {
    VehicleResponseDto findById(Long id);
    List<VehicleResponseDto> findAll();
    VehicleResponseDto create(VehicleRequestDto dto);
    VehicleResponseDto update(VehicleRequestDto dto);
    boolean deleteById(Long id);
}
