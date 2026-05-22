package cl.duoc.ms_sales.repository;

import cl.duoc.ms_sales.model.SaleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<SaleModel, Long> {
}
