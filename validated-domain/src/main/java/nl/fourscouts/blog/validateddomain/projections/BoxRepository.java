package nl.fourscouts.blog.validateddomain.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoxRepository extends JpaRepository<Box, String> {
	@Modifying(clearAutomatically = true)
	@Query("update Box set availableRoom = :availableRoom where boxId = :boxId")
	void update(@Param("boxId") String boxId, @Param("availableRoom") int availableRoom);
}
