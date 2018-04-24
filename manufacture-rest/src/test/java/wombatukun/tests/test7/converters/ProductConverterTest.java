package wombatukun.tests.test7.converters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wombatukun.tests.test7.dto.responses.ProductDto;
import wombatukun.tests.test7.dto.responses.StuffDto;
import wombatukun.tests.test7.entity.Product;
import wombatukun.tests.test7.entity.Stuff;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductConverterTest {

	@Autowired
	private ProductConverter productConverter;

	@Test
	public void convertTest() {
		Stuff stuff = new Stuff("stu1", 10d, 100L);
		stuff.setId(1L);
		Map<Stuff, Integer> stuffMap = new HashMap<>();
		stuffMap.put(stuff, 2);
		stuff = new Stuff("stu2", 20d, 200L);
		stuffMap.put(stuff, 7);
		Product product = new Product("pro1", stuffMap);
		ProductDto dto = productConverter.productToProductDto(product);
		assertNotNull(dto);
		assertEquals(product.getName(), dto.getProductName());
		assertEquals(160, dto.getTotalCost(), 0.001d);
		assertEquals(2, dto.getComposition().size());
		for (StuffDto stuffDto : dto.getComposition()) {
			switch (stuffDto.getStuffName()) {
				case "stu1":
					assertEquals(10d, stuffDto.getCost(), 0.001);
					assertEquals(2, stuffDto.getAmount().longValue());
					break;
				case "stu2":
					assertEquals(20d, stuffDto.getCost(), 0.001);
					assertEquals(7, stuffDto.getAmount().longValue());
					break;
				default:
					fail("some surprise stuff");
			}
		}
	}

}
