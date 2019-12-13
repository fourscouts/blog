package nl.fourscouts.blog.replays.repositories;

import nl.fourscouts.blog.replays.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderHistoryRepository extends JpaRepository<OrderHistoryItem, Long> {
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update OrderHistoryItem set status = :status where reference = :reference")
	int updateStatus(@Param("reference") String reference, @Param("status") Status status);

	Optional<OrderHistoryItem> findByReference(String reference);
}
