package wombatukun.tests.test7.converters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wombatukun.tests.test7.dto.responses.SupplyDto;
import wombatukun.tests.test7.entity.Stuff;
import wombatukun.tests.test7.entity.Supply;
import wombatukun.tests.test7.entity.enums.Result;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplyConverterTest {

	@Autowired
	private SupplyConverter supplyConverter;

	@Test
	public void convertTest() {
		Stuff stuff = new Stuff("stu1", 10d, 100L);
		stuff.setId(1L);
		Supply supply = new Supply(new Date(), stuff, 5, 23d, Result.SUCCESS);
		SupplyDto dto = supplyConverter.supplyToSupplyDto(supply);
		assertNotNull(dto);
		assertEquals(supply.getDate(), dto.getDate());
		assertEquals(supply.getCost(), dto.getCost());
		assertEquals(supply.getAmount(), dto.getAmount());
		assertEquals(supply.getStuff().getName(), dto.getStuffName());
		assertEquals(supply.getAmount() * supply.getCost(), dto.getTotalCost(), 0.001d);
		assertEquals(supply.getResult().toString(), dto.getResult());
	}

}
