package cl.duoc.ms_delivery.repository;

import cl.duoc.ms_delivery.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
