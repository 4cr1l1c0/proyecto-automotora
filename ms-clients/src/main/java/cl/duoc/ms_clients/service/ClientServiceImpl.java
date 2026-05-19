package cl.duoc.ms_clients.service;

import cl.duoc.ms_clients.dto.ClientRequestDto;
import cl.duoc.ms_clients.dto.ClientResponseDto;
import cl.duoc.ms_clients.model.ClientModel;
import cl.duoc.ms_clients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    private ClientResponseDto toDto(ClientModel entity) {
        return new ClientResponseDto(
                entity.getId(),
                entity.getRut(),
                entity.getPrimerNombre(),
                entity.getSegundoNombre(),
                entity.getApellidoPaterno(),
                entity.getApellidoMaterno(),
                entity.getEmail(),
                entity.getTelefono(),
                entity.getDireccion(),
                entity.getFechaNacimiento(),
                entity.getActivoCliente()
        );
    }

    private ClientModel toEntity(ClientRequestDto dto) {
        return new ClientModel(
                dto.getId(),
                dto.getRut(),
                dto.getPrimerNombre(),
                dto.getSegundoNombre(),
                dto.getApellidoPaterno(),
                dto.getApellidoMaterno(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getDireccion(),
                dto.getFechaNacimiento(),
                dto.getActivoCliente()
        );
    }

    @Override
    public ClientResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<ClientResponseDto> findAll() {
        log.info("Fetching all clients");
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public ClientResponseDto create(ClientRequestDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public ClientResponseDto update(ClientRequestDto dto) {
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
