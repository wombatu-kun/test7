package wombatukun.tests.test7.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import wombatukun.tests.test7.entity.Stuff;
import wombatukun.tests.test7.entity.Supply;
import wombatukun.tests.test7.entity.enums.Result;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplyRepositoryTest {

	private static final String NAME = "carrot";
	private static final double COST = 10d;
	private static final long AMOUNT = 5L;

	@Autowired
	private SupplyRepository supplyRepository;
	@Autowired
	private StuffRepository stuffRepository;

	@Test
	@Sql({"/clean.sql"})
	public void saveAndGetTest() {
		stuffRepository.save(new Stuff(NAME, COST, AMOUNT));
		Stuff stuff = stuffRepository.findByName(NAME);

		List<Supply> supplyList = supplyRepository.findAll();
		assertEquals(0, supplyList.size());
		Supply supply = new Supply(new Date(), stuff, 7, COST-2, Result.FAILURE);
		supply = supplyRepository.save(supply);
		supplyList = supplyRepository.findAll();
		assertEquals(1, supplyList.size());
		Supply found = supplyList.get(0);
		assertEquals(supply.getId(), found.getId());
		assertEquals(supply.getStuff(), found.getStuff());
		assertEquals(supply.getStuff().getName(), found.getStuff().getName());
		assertEquals(supply.getAmount(), found.getAmount());
		assertEquals(supply.getCost(), found.getCost());
		assertEquals(supply.getResult(), found.getResult());
		assertNotNull(found.getDate());

		LocalDateTime date = LocalDateTime.now();
		supplyList = supplyRepository.findByDateAfterOrderByDateDesc(Date.from(date.atZone(ZoneId.systemDefault()).plusDays(1).toInstant()));
		assertEquals(0, supplyList.size());
		supplyList = supplyRepository.findByDateBetweenOrderByDateDesc(Date.from(date.atZone(ZoneId.systemDefault()).minusDays(2).toInstant()),
				Date.from(date.atZone(ZoneId.systemDefault()).minusDays(1).toInstant()));
		assertEquals(0, supplyList.size());
		supplyList = supplyRepository.findByDateBetweenOrderByDateDesc(Date.from(date.atZone(ZoneId.systemDefault()).minusDays(2).toInstant()),
				Date.from(date.atZone(ZoneId.systemDefault()).plusDays(1).toInstant()));
		assertEquals(1, supplyList.size());
	}
}
