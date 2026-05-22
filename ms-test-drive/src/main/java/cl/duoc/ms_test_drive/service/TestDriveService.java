package cl.duoc.ms_test_drive.service;

import cl.duoc.ms_test_drive.dto.TestDriveRequestDto;
import cl.duoc.ms_test_drive.dto.TestDriveResponseDto;

import java.util.List;

public interface TestDriveService {
    TestDriveResponseDto findById(Long id);
    List<TestDriveResponseDto> findAll();
    TestDriveResponseDto create(TestDriveRequestDto dto);
    TestDriveResponseDto update(TestDriveRequestDto dto);
    boolean deleteById(Long id);
}
