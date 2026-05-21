package cl.duoc.ms_inventory.repository;

import cl.duoc.ms_inventory.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
}
