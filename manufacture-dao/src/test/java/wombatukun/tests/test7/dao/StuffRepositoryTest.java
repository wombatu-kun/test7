package wombatukun.tests.test7.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import wombatukun.tests.test7.entity.Stuff;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StuffRepositoryTest {

	private static final String NAME = "carrot";
	private static final double COST = 10d;
	private static final long AMOUNT = 5L;

	@Autowired
	private StuffRepository stuffRepository;

	@Test
	@Sql({"/clean.sql"})
	public void findByNameTest() {
		List<Stuff> stuffList = stuffRepository.findAll();
		assertEquals(0, stuffList.size());
		stuffRepository.save(new Stuff(NAME, COST, AMOUNT));
		Stuff stuff = stuffRepository.findByName(NAME);
		assertNotNull(stuff);
		assertNotNull(stuff.getId());
		assertEquals(Double.valueOf(COST), stuff.getCost());
		assertEquals(Long.valueOf(AMOUNT), stuff.getAmount());
	}
}
