package cl.duoc.ms_ensurances.repository;

import cl.duoc.ms_ensurances.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
