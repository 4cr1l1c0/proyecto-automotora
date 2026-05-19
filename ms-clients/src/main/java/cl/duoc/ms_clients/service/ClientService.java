package cl.duoc.ms_clients.service;

import cl.duoc.ms_clients.dto.ClientRequestDto;
import cl.duoc.ms_clients.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {
    ClientResponseDto findById(Long id);
    List<ClientResponseDto> findAll();
    ClientResponseDto create(ClientRequestDto dto);
    ClientResponseDto update(ClientRequestDto dto);
    boolean deleteById(Long id);
}
