package wombatukun.tests.test7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wombatukun.tests.test7.entity.Supply;

import java.util.Date;
import java.util.List;

/**
 * Репозиторий закупок материалов
 */
@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

	/**
	 * Возвращает список закупок материалов от заданной даты по настоящее время, в обратном опрядке
	 * @param start начальная дата
	 * @return список закупок
	 */
	List<Supply> findByDateAfterOrderByDateDesc(Date start);

	/**
	 * Возвращает список закупок материалов в заданном интервале, в обратном опрядке
	 * @param start начальная дата
	 * @param end конечная дата
	 * @return список закупок в заданном интервале
	 */
	List<Supply> findByDateBetweenOrderByDateDesc(Date start, Date end);

}
