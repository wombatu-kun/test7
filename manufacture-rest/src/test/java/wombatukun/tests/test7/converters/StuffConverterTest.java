package wombatukun.tests.test7.converters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wombatukun.tests.test7.dto.responses.StuffDto;
import wombatukun.tests.test7.entity.Stuff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StuffConverterTest {

	@Autowired
	private StuffConverter stuffConverter;

	@Test
	public void convertTest() {
		Stuff stuff = new Stuff("stu1", 10d, 100L);
		stuff.setId(1L);
		StuffDto dto = stuffConverter.stuffToStuffDto(stuff);
		assertNotNull(dto);
		assertEquals(stuff.getName(), dto.getStuffName());
		assertEquals(stuff.getCost(), dto.getCost());
		assertEquals(stuff.getAmount(), dto.getAmount());
	}

}
