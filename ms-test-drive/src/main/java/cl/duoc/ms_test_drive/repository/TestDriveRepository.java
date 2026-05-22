package cl.duoc.ms_test_drive.repository;

import cl.duoc.ms_test_drive.model.TestDriveVisit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDriveRepository extends JpaRepository<TestDriveVisit, Long> {
}
