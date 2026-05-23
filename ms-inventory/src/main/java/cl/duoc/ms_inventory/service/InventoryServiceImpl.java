package cl.duoc.ms_inventory.service;

import cl.duoc.ms_inventory.dto.InventoryRequestDto;
import cl.duoc.ms_inventory.dto.InventoryResponseDto;
import cl.duoc.ms_inventory.exception.ResourceNotFoundException;
import cl.duoc.ms_inventory.feign.VehicleDto;
import cl.duoc.ms_inventory.feign.VehicleFeignClient;
import cl.duoc.ms_inventory.model.InventoryItem;
import cl.duoc.ms_inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;
    private final VehicleFeignClient vehicleFeignClient;

    private InventoryResponseDto toDto(InventoryItem entity) {
        return new InventoryResponseDto(
                entity.getId(),
                entity.getVehicleId(),
                entity.getQuantity(),
                entity.getMinQuantity(),
                entity.getLocation(),
                entity.getLastUpdated(),
                null
        );
    }

    private InventoryItem toEntity(InventoryRequestDto dto) {
        return new InventoryItem(
                dto.getId(),
                dto.getVehicleId(),
                dto.getQuantity(),
                dto.getMinQuantity(),
                dto.getLocation(),
                dto.getLastUpdated()
        );
    }

    private void validateForeignKeys(InventoryRequestDto dto) {
        VehicleDto vehicle = null;
        try { vehicle = vehicleFeignClient.findById(dto.getVehicleId()); } catch (Exception ignored) {}
        if (vehicle == null) throw new ResourceNotFoundException("Vehículo con id " + dto.getVehicleId() + " no existe");
    }

    @Override
    public InventoryResponseDto findById(Long id) {
        return repository.findById(id).map(item -> {
            InventoryResponseDto dto = toDto(item);
            try {
                dto.setVehicle(vehicleFeignClient.findById(item.getVehicleId()));
            } catch (Exception e) {
                log.warn("Could not fetch vehicle data for inventory item {}: {}", id, e.getMessage());
            }
            return dto;
        }).orElse(null);
    }

    @Override
    public List<InventoryResponseDto> findAll() {
        log.info("Fetching all inventory items");
        return repository.findAll().stream().map(item -> {
            InventoryResponseDto dto = toDto(item);
            try {
                dto.setVehicle(vehicleFeignClient.findById(item.getVehicleId()));
            } catch (Exception e) {
                log.warn("Could not fetch vehicle data for inventory item {}: {}", item.getId(), e.getMessage());
            }
            return dto;
        }).toList();
    }

    @Override
    public InventoryResponseDto create(InventoryRequestDto dto) {
        validateForeignKeys(dto);
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public InventoryResponseDto update(InventoryRequestDto dto) {
        validateForeignKeys(dto);
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
