package cl.duoc.ms_vehicles.service;

import cl.duoc.ms_vehicles.dto.VehicleRequestDto;
import cl.duoc.ms_vehicles.dto.VehicleResponseDto;
import cl.duoc.ms_vehicles.model.Vehicle;
import cl.duoc.ms_vehicles.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;

    private VehicleResponseDto toDto(Vehicle entity) {
        return new VehicleResponseDto(
                entity.getId(),
                entity.getVin(),
                entity.getBrand(),
                entity.getModel(),
                entity.getYear(),
                entity.getColor(),
                entity.getPrice(),
                entity.getStatus(),
                entity.getType(),
                entity.getMileage()
        );
    }

    private Vehicle toEntity(VehicleRequestDto dto) {
        return new Vehicle(
                dto.getId(),
                dto.getVin(),
                dto.getBrand(),
                dto.getModel(),
                dto.getYear(),
                dto.getColor(),
                dto.getPrice(),
                dto.getStatus(),
                dto.getType(),
                dto.getMileage()
        );
    }

    @Override
    public VehicleResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<VehicleResponseDto> findAll() {
        log.info("Fetching all vehicles");
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public VehicleResponseDto create(VehicleRequestDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public VehicleResponseDto update(VehicleRequestDto dto) {
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
