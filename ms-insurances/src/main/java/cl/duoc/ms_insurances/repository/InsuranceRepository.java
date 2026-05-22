package cl.duoc.ms_insurances.repository;

import cl.duoc.ms_insurances.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
