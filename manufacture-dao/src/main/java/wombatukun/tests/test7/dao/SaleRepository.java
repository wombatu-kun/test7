package wombatukun.tests.test7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wombatukun.tests.test7.entity.Sale;

import java.util.Date;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	List<Sale> findByDateAfterOrderByDateDesc(Date start);
	List<Sale> findByDateBetweenOrderByDateDesc(Date start, Date end);

}
