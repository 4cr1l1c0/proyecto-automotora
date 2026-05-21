package cl.duoc.ms_suppliers.repository;

import cl.duoc.ms_suppliers.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
