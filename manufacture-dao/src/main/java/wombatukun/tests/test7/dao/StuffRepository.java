package wombatukun.tests.test7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wombatukun.tests.test7.entity.Stuff;

/**
 * Репозиторий материалов
 */
@Repository
public interface StuffRepository extends JpaRepository<Stuff, Long> {

	/**
	 * Возвращает материал по названию
	 * @param name название
	 * @return сущность
	 */
	Stuff findByName(String name);

}
