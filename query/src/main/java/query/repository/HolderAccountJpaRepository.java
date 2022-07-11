package query.repository;

import query.entity.HolderAccountSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HolderAccountJpaRepository extends JpaRepository<HolderAccountSummary, String> {
    Optional<HolderAccountSummary> findByHolderId(String holderId);
}
