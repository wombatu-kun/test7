package wombatukun.tests.test7.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import wombatukun.tests.test7.entity.Product;
import wombatukun.tests.test7.entity.Sale;
import wombatukun.tests.test7.entity.Stuff;
import wombatukun.tests.test7.entity.enums.Result;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaleRepositoryTest {

	private static final String PRODUCT_NAME = "salad";

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StuffRepository stuffRepository;
	@Autowired
	private SaleRepository saleRepository;

	@Test
	@Sql({"/clean.sql"})
	public void saveAndGetTest() {
		Map<Stuff, Integer> stuffMap = new HashMap<>();
		Stuff carrot = stuffRepository.save(new Stuff("carrot", 10d, 5L));
		stuffMap.put(carrot, 2);
		Stuff potato = stuffRepository.save(new Stuff("potato", 20d, 2L));
		stuffMap.put(potato, 1);
		Product product = new Product(PRODUCT_NAME, stuffMap);
		product = productRepository.save(product);

		List<Sale> saleList = saleRepository.findAll();
		assertEquals(0, saleList.size());
		Sale sale = saleRepository.save(new Sale(new Date(), product, 3, Result.SUCCESS));
		saleList = saleRepository.findAll();
		assertEquals(1, saleList.size());
		Sale found = saleList.get(0);
		assertEquals(sale.getId(), found.getId());
		assertEquals(sale.getProduct().getId(), found.getProduct().getId());
		assertEquals(sale.getProduct().getName(), found.getProduct().getName());
		assertNotNull(found.getProduct().getComposition());
		assertEquals(2, found.getProduct().getComposition().size());
		assertEquals(sale.getAmount(), found.getAmount());
		assertEquals(sale.getResult(), found.getResult());
		assertNotNull(found.getDate());

		LocalDateTime date = LocalDateTime.now();
		saleList = saleRepository.findByDateAfterOrderByDateDesc(Date.from(date.atZone(ZoneId.systemDefault()).plusDays(1).toInstant()));
		assertEquals(0, saleList.size());
		saleList = saleRepository.findByDateBetweenOrderByDateDesc(Date.from(date.atZone(ZoneId.systemDefault()).minusDays(2).toInstant()),
				Date.from(date.atZone(ZoneId.systemDefault()).minusDays(1).toInstant()));
		assertEquals(0, saleList.size());
		saleList = saleRepository.findByDateBetweenOrderByDateDesc(Date.from(date.atZone(ZoneId.systemDefault()).minusDays(2).toInstant()),
				Date.from(date.atZone(ZoneId.systemDefault()).plusDays(1).toInstant()));
		assertEquals(1, saleList.size());
	}
}
