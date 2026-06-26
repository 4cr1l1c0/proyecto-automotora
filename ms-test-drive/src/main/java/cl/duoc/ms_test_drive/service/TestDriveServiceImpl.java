package cl.duoc.ms_test_drive.service;

import cl.duoc.ms_test_drive.dto.TestDriveRequestDto;
import cl.duoc.ms_test_drive.dto.TestDriveResponseDto;
import cl.duoc.ms_test_drive.exception.ResourceNotFoundException;
import cl.duoc.ms_test_drive.exception.ServiceUnavailableException;
import cl.duoc.ms_test_drive.feign.ClientDto;
import cl.duoc.ms_test_drive.feign.ClientFeignClient;
import cl.duoc.ms_test_drive.feign.VehicleDto;
import cl.duoc.ms_test_drive.feign.VehicleFeignClient;
import cl.duoc.ms_test_drive.model.TestDriveVisit;
import cl.duoc.ms_test_drive.repository.TestDriveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestDriveServiceImpl implements TestDriveService {

    private final TestDriveRepository repository;
    private final ClientFeignClient clientFeignClient;
    private final VehicleFeignClient vehicleFeignClient;

    private TestDriveResponseDto toDto(TestDriveVisit entity) {
        return new TestDriveResponseDto(
                entity.getId(),
                entity.getClientId(),
                entity.getVehicleId(),
                entity.getVisitDate(),
                entity.getNotes(),
                entity.getStatus(),
                null,
                null
        );
    }

    private TestDriveVisit toEntity(TestDriveRequestDto dto) {
        return new TestDriveVisit(
                dto.getId(),
                dto.getClientId(),
                dto.getVehicleId(),
                dto.getVisitDate(),
                dto.getNotes(),
                dto.getStatus()
        );
    }

    private void validateForeignKeys(TestDriveRequestDto dto) {
        ClientDto client = clientFeignClient.findById(dto.getClientId());
        if (client == null) throw new ResourceNotFoundException("Cliente con id " + dto.getClientId() + " no existe");

        VehicleDto vehicle = vehicleFeignClient.findById(dto.getVehicleId());
        if (vehicle == null) throw new ResourceNotFoundException("Vehículo con id " + dto.getVehicleId() + " no existe");
    }

    @Override
    public TestDriveResponseDto findById(Long id) {
        return repository.findById(id).map(visit -> {
            TestDriveResponseDto dto = toDto(visit);
            try {
                dto.setClient(clientFeignClient.findById(visit.getClientId()));
            } catch (ServiceUnavailableException e) {
                log.warn("Servicio de clientes no disponible para test drive {}: {}", id, e.getMessage());
            }
            try {
                dto.setVehicle(vehicleFeignClient.findById(visit.getVehicleId()));
            } catch (ServiceUnavailableException e) {
                log.warn("Servicio de vehículos no disponible para test drive {}: {}", id, e.getMessage());
            }
            return dto;
        }).orElse(null);
    }

    @Override
    public List<TestDriveResponseDto> findAll() {
        log.info("Fetching all test drive visits");
        return repository.findAll().stream().map(visit -> {
            TestDriveResponseDto dto = toDto(visit);
            try {
                dto.setClient(clientFeignClient.findById(visit.getClientId()));
            } catch (ServiceUnavailableException e) {
                log.warn("Servicio de clientes no disponible para test drive {}: {}", visit.getId(), e.getMessage());
            }
            try {
                dto.setVehicle(vehicleFeignClient.findById(visit.getVehicleId()));
            } catch (ServiceUnavailableException e) {
                log.warn("Servicio de vehículos no disponible para test drive {}: {}", visit.getId(), e.getMessage());
            }
            return dto;
        }).toList();
    }

    @Override
    public TestDriveResponseDto create(TestDriveRequestDto dto) {
        validateForeignKeys(dto);
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public TestDriveResponseDto update(TestDriveRequestDto dto) {
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
