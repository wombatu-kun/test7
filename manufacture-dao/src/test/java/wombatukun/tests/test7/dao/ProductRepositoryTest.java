package wombatukun.tests.test7.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import wombatukun.tests.test7.entity.Product;
import wombatukun.tests.test7.entity.Stuff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

	private static final String PRODUCT_NAME = "salad";

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StuffRepository stuffRepository;

	@Test
	@Sql({"/clean.sql"})
	public void findByNameTest() {
		List<Product> productList = productRepository.findAll();
		assertEquals(0, productList.size());
		Map<Stuff, Integer> stuffMap = new HashMap<>();
		Stuff carrot = stuffRepository.save(new Stuff("carrot", 10d, 5L));
		stuffMap.put(carrot, 2);
		Stuff potato = stuffRepository.save(new Stuff("potato", 20d, 2L));
		stuffMap.put(potato, 1);
		Product product = new Product(PRODUCT_NAME, stuffMap);
		productRepository.save(product);

		product = productRepository.findByName(PRODUCT_NAME);
		assertNotNull(product);
		assertNotNull(product.getComposition());
		assertEquals(2, product.getComposition().entrySet().size());
		product.getComposition().entrySet().forEach( e -> {
			Stuff st = e.getKey();
			switch(e.getValue()) {
				case 2:
					assertEqualsStuff(carrot, st);
					break;
				case 1:
					assertEqualsStuff(potato, st);
					break;
				default:
					fail("Unexpected amount of " + st.getName() + " = " + e.getValue());
			}
		});

		product = productRepository.findByName("qwerty");
		assertNull(product);
	}

	private void assertEqualsStuff(Stuff expected, Stuff actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getAmount(), actual.getAmount());
		assertEquals(expected.getCost(), actual.getCost());
	}
}
