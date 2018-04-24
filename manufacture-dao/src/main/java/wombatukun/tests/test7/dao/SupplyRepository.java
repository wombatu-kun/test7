package wombatukun.tests.test7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wombatukun.tests.test7.entity.Supply;

import java.util.Date;
import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

	List<Supply> findByDateAfterOrderByDateDesc(Date start);
	List<Supply> findByDateBetweenOrderByDateDesc(Date start, Date end);

}
