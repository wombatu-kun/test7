package wombatukun.tests.test7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wombatukun.tests.test7.entity.Product;

/**
 * Репозиторий продуктов
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * Возвращает продукт по названию
	 * @param name навзание
	 * @return сущность
	 */
	Product findByName(String name);

}
