package wombatukun.tests.test7.converters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wombatukun.tests.test7.dto.responses.SaleDto;
import wombatukun.tests.test7.entity.Product;
import wombatukun.tests.test7.entity.Sale;
import wombatukun.tests.test7.entity.Stuff;
import wombatukun.tests.test7.entity.enums.Result;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaleConverterTest {

	@Autowired
	private SaleConverter saleConverter;

	@Test
	public void convertTest() {
		Stuff stuff = new Stuff("stu1", 10d, 100L);
		stuff.setId(1L);
		Map<Stuff, Integer> stuffMap = new HashMap<>();
		stuffMap.put(stuff, 2);
		stuff = new Stuff("stu2", 20d, 200L);
		stuffMap.put(stuff, 7);
		Product product = new Product("pro1", stuffMap);
		Sale sale = new Sale(new Date(), product, 2, Result.FAILURE);
		SaleDto dto = saleConverter.saleToSaleDto(sale);
		assertNotNull(dto);
		assertEquals(sale.getDate(), dto.getDate());
		assertEquals(sale.getAmount(), dto.getAmount());
		assertEquals(sale.getProduct().getName(), dto.getProductName());
		assertEquals(320, dto.getTotalCost(), 0.001d);
		assertEquals(sale.getResult().toString(), dto.getResult());
	}

}
