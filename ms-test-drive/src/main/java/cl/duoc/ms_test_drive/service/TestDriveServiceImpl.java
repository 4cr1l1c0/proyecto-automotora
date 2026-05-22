package cl.duoc.ms_test_drive.service;

import cl.duoc.ms_test_drive.dto.TestDriveRequestDto;
import cl.duoc.ms_test_drive.dto.TestDriveResponseDto;
import cl.duoc.ms_test_drive.feign.ClientFeignClient;
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

    @Override
    public TestDriveResponseDto findById(Long id) {
        return repository.findById(id).map(visit -> {
            TestDriveResponseDto dto = toDto(visit);
            try {
                dto.setClient(clientFeignClient.findById(visit.getClientId()));
            } catch (Exception e) {
                log.warn("Could not fetch client data for test drive {}: {}", id, e.getMessage());
            }
            try {
                dto.setVehicle(vehicleFeignClient.findById(visit.getVehicleId()));
            } catch (Exception e) {
                log.warn("Could not fetch vehicle data for test drive {}: {}", id, e.getMessage());
            }
            return dto;
        }).orElse(null);
    }

    @Override
    public List<TestDriveResponseDto> findAll() {
        log.info("Fetching all test drive visits");
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public TestDriveResponseDto create(TestDriveRequestDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public TestDriveResponseDto update(TestDriveRequestDto dto) {
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
