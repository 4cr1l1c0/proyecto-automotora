package cl.duoc.ms_clients.repository;

import cl.duoc.ms_clients.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository
        extends JpaRepository<ClientModel, Long> {
}