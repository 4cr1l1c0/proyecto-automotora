package cl.duoc.ms_delivery.service;

import cl.duoc.ms_delivery.dto.DeliveryRequestDto;
import cl.duoc.ms_delivery.dto.DeliveryResponseDto;

import java.util.List;

public interface DeliveryService {
    DeliveryResponseDto findById(Long id);
    List<DeliveryResponseDto> findAll();
    DeliveryResponseDto create(DeliveryRequestDto dto);
    DeliveryResponseDto update(DeliveryRequestDto dto);
    boolean deleteById(Long id);
}
