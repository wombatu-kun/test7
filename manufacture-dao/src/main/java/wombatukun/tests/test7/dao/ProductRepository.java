package wombatukun.tests.test7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wombatukun.tests.test7.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByName(String name);

}
