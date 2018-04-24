package wombatukun.tests.test7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wombatukun.tests.test7.entity.Sale;

import java.util.Date;
import java.util.List;

/**
 * Репозиторий продаж продуктов
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	/**
	 * Возвращает список продаж продуктов от заданной даты по настоящее время, в обратном опрядке
	 * @param start начальная дата
	 * @return список продаж
	 */
	List<Sale> findByDateAfterOrderByDateDesc(Date start);

	/**
	 * Возвращает список продаж продуктов в заданном интервале, в обратном опрядке
	 * @param start начальная дата
	 * @param end конечная дата
	 * @return список продаж в заданном интервале
	 */
	List<Sale> findByDateBetweenOrderByDateDesc(Date start, Date end);

}
