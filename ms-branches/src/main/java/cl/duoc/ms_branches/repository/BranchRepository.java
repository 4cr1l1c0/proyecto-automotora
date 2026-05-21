package cl.duoc.ms_branches.repository;

import cl.duoc.ms_branches.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
