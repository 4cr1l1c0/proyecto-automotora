package cl.duoc.ms_insurances.service;

import cl.duoc.ms_insurances.dto.InsuranceRequestDto;
import cl.duoc.ms_insurances.dto.InsuranceResponseDto;
import cl.duoc.ms_insurances.exception.ResourceNotFoundException;
import cl.duoc.ms_insurances.feign.ClientDto;
import cl.duoc.ms_insurances.feign.ClientFeignClient;
import cl.duoc.ms_insurances.feign.VehicleDto;
import cl.duoc.ms_insurances.feign.VehicleFeignClient;
import cl.duoc.ms_insurances.model.Insurance;
import cl.duoc.ms_insurances.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository repository;
    private final ClientFeignClient clientFeignClient;
    private final VehicleFeignClient vehicleFeignClient;

    private InsuranceResponseDto toDto(Insurance entity) {
        return new InsuranceResponseDto(
                entity.getId(),
                entity.getPolicyNumber(),
                entity.getType(),
                entity.getCoverageAmount(),
                entity.getPremium(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getVehicleId(),
                entity.getClientId(),
                entity.getActive(),
                null,
                null
        );
    }

    private Insurance toEntity(InsuranceRequestDto dto) {
        return new Insurance(
                dto.getId(),
                dto.getPolicyNumber(),
                dto.getType(),
                dto.getCoverageAmount(),
                dto.getPremium(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getVehicleId(),
                dto.getClientId(),
                dto.getActive()
        );
    }

    private void validateForeignKeys(InsuranceRequestDto dto) {
        VehicleDto vehicle = null;
        try { vehicle = vehicleFeignClient.findById(dto.getVehicleId()); } catch (Exception ignored) {}
        if (vehicle == null) throw new ResourceNotFoundException("Vehículo con id " + dto.getVehicleId() + " no existe");

        ClientDto client = null;
        try { client = clientFeignClient.findById(dto.getClientId()); } catch (Exception ignored) {}
        if (client == null) throw new ResourceNotFoundException("Cliente con id " + dto.getClientId() + " no existe");
    }

    @Override
    public InsuranceResponseDto findById(Long id) {
        return repository.findById(id).map(insurance -> {
            InsuranceResponseDto dto = toDto(insurance);
            try {
                dto.setVehicle(vehicleFeignClient.findById(insurance.getVehicleId()));
            } catch (Exception e) {
                log.warn("Could not fetch vehicle data for insurance {}: {}", id, e.getMessage());
            }
            try {
                dto.setClient(clientFeignClient.findById(insurance.getClientId()));
            } catch (Exception e) {
                log.warn("Could not fetch client data for insurance {}: {}", id, e.getMessage());
            }
            return dto;
        }).orElse(null);
    }

    @Override
    public List<InsuranceResponseDto> findAll() {
        log.info("Fetching all insurances");
        return repository.findAll().stream().map(insurance -> {
            InsuranceResponseDto dto = toDto(insurance);
            try {
                dto.setVehicle(vehicleFeignClient.findById(insurance.getVehicleId()));
            } catch (Exception e) {
                log.warn("Could not fetch vehicle data for insurance {}: {}", insurance.getId(), e.getMessage());
            }
            try {
                dto.setClient(clientFeignClient.findById(insurance.getClientId()));
            } catch (Exception e) {
                log.warn("Could not fetch client data for insurance {}: {}", insurance.getId(), e.getMessage());
            }
            return dto;
        }).toList();
    }

    @Override
    public InsuranceResponseDto create(InsuranceRequestDto dto) {
        validateForeignKeys(dto);
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public InsuranceResponseDto update(InsuranceRequestDto dto) {
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
