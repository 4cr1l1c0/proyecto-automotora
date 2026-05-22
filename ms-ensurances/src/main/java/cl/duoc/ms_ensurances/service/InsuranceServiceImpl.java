package cl.duoc.ms_ensurances.service;

import cl.duoc.ms_ensurances.dto.InsuranceRequestDto;
import cl.duoc.ms_ensurances.dto.InsuranceResponseDto;
import cl.duoc.ms_ensurances.feign.ClientFeignClient;
import cl.duoc.ms_ensurances.feign.VehicleFeignClient;
import cl.duoc.ms_ensurances.model.Insurance;
import cl.duoc.ms_ensurances.repository.InsuranceRepository;
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
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public InsuranceResponseDto create(InsuranceRequestDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public InsuranceResponseDto update(InsuranceRequestDto dto) {
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
