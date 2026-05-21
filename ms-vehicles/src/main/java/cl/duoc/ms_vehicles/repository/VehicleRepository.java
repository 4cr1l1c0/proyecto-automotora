package cl.duoc.ms_vehicles.repository;

import cl.duoc.ms_vehicles.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
