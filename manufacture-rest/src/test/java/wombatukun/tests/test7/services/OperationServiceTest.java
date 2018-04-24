package wombatukun.tests.test7.services;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import wombatukun.tests.test7.dao.ProductRepository;
import wombatukun.tests.test7.dao.StuffRepository;
import wombatukun.tests.test7.dev.MethodOrder;
import wombatukun.tests.test7.dev.SpringOrderedRunner;
import wombatukun.tests.test7.dto.requests.ConsumerRequest;
import wombatukun.tests.test7.dto.requests.SupplierRequest;
import wombatukun.tests.test7.dto.responses.SaleDto;
import wombatukun.tests.test7.dto.responses.SupplyDto;
import wombatukun.tests.test7.entity.Product;
import wombatukun.tests.test7.entity.Stuff;
import wombatukun.tests.test7.entity.enums.Result;
import wombatukun.tests.test7.exceptions.ResourceNotFoundException;
import wombatukun.tests.test7.messages.MessageService;
import wombatukun.tests.test7.messages.Messages;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringOrderedRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(locations="classpath:test.properties")
public class OperationServiceTest {

	@Autowired
	private OperationService operationService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private StuffRepository stuffRepository;
	@Autowired
	private ProductRepository productRepository;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	@MethodOrder(order = 1)
	public void emptySupplyHistoryTest() {
		expectedEx.expect(ResourceNotFoundException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.SUPPLIES_NOT_FOUND));
		operationService.getSupplyHistory(null, null);
	}

	@Test
	@MethodOrder(order = 2)
	public void emptySaleHistoryTest() {
		expectedEx.expect(ResourceNotFoundException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.SALES_NOT_FOUND));
		operationService.getSaleHistory(null, null);
	}

	@Test
	@MethodOrder(order = 3)
	public void operationSupplySuccessTest() {
		Double sum = operationService.getAccountSum().getSum();
		Long stuffAmount = stuffRepository.findByName("stuff1").getAmount();
		SupplierRequest req = new SupplierRequest("stuff1", 10, 50d);
		SupplyDto dto = operationService.operationSupply(req);
		assertNotNull(dto);
		assertNotNull(dto.getDate());
		assertEquals(req.getStuffName(), dto.getStuffName());
		assertEquals(req.getAmount(), dto.getAmount());
		assertEquals(req.getCost(), dto.getCost());
		assertEquals(500, dto.getTotalCost(), 0.001);
		assertEquals(Result.SUCCESS.toString(), dto.getResult());
		sum = sum - dto.getTotalCost();
		assertEquals(sum, operationService.getAccountSum().getSum(), 0.001);
		assertEquals(stuffAmount + req.getAmount(), stuffRepository.findByName("stuff1").getAmount().longValue());
		stuffAmount = stuffRepository.findByName("stuff2").getAmount();
		req = new SupplierRequest("stuff2", 3, 2d);
		dto = operationService.operationSupply(req);
		assertNotNull(dto);
		assertNotNull(dto.getDate());
		assertEquals(req.getStuffName(), dto.getStuffName());
		assertEquals(req.getAmount(), dto.getAmount());
		assertEquals(req.getCost(), dto.getCost());
		assertEquals(6, dto.getTotalCost(), 0.001);
		assertEquals(Result.SUCCESS.toString(), dto.getResult());
		assertEquals(sum - dto.getTotalCost(), operationService.getAccountSum().getSum(), 0.001);
		assertEquals(stuffAmount + req.getAmount(), stuffRepository.findByName("stuff2").getAmount().longValue());
	}

	@Test
	@MethodOrder(order = 4)
	public void operationSupplyFailureTest() {
		Double sum = operationService.getAccountSum().getSum();
		Long stuffAmount = stuffRepository.findByName("stuff2").getAmount();
		SupplierRequest req = new SupplierRequest("stuff2", 1000, 800d);
		SupplyDto dto = operationService.operationSupply(req);
		assertNotNull(dto);
		assertNotNull(dto.getDate());
		assertEquals(req.getStuffName(), dto.getStuffName());
		assertEquals(req.getAmount(), dto.getAmount());
		assertEquals(req.getCost(), dto.getCost());
		assertEquals(800000, dto.getTotalCost(), 0.001);
		assertEquals(Result.FAILURE.toString() + ": " + messageService.getMessage(Messages.MONEY_NOT_ENOUGH), dto.getResult());
		assertEquals(sum, operationService.getAccountSum().getSum(), 0.001);
		assertEquals(stuffAmount, stuffRepository.findByName("stuff2").getAmount());
	}

	@Test
	@MethodOrder(order = 5)
	public void supplyHistoryTest() {
		List<SupplyDto> list = operationService.getSupplyHistory(null, null);
		assertEquals(3, list.size());
		SupplyDto dto = list.get(0);
		assertEquals("stuff1", dto.getStuffName());
		assertEquals(500d, dto.getTotalCost(), 0.001);
		assertEquals(Result.SUCCESS.toString(), dto.getResult());
		dto = list.get(1);
		assertEquals("stuff2", dto.getStuffName());
		assertEquals(6, dto.getTotalCost(), 0.001);
		assertEquals(Result.SUCCESS.toString(), dto.getResult());
		dto = list.get(2);
		assertEquals("stuff2", dto.getStuffName());
		assertEquals(800000, dto.getTotalCost(), 0.001);
		assertEquals(Result.FAILURE.toString(), dto.getResult());
	}

	@Test
	@MethodOrder(order = 6)
	public void operationSaleSuccessTest() {
		Double sum = operationService.getAccountSum().getSum();
		Product product = productRepository.findByName("prod1");
		ConsumerRequest req = new ConsumerRequest("prod1", 2);
		SaleDto dto = operationService.operationSale(req);
		assertNotNull(dto);
		assertNotNull(dto.getDate());
		assertEquals(req.getProductName(), dto.getProductName());
		assertEquals(req.getAmount(), dto.getAmount());
		assertEquals(30d, dto.getCost(), 0.001);
		assertEquals(60d, dto.getTotalCost(), 0.001);
		assertEquals(Result.SUCCESS.toString(), dto.getResult());
		assertEquals(sum + dto.getTotalCost(), operationService.getAccountSum().getSum(), 0.001);
		for(Map.Entry<Stuff, Integer> e : product.getComposition().entrySet()) {
			assertEquals(e.getKey().getAmount() - e.getValue()*req.getAmount(),
					stuffRepository.findByName(e.getKey().getName()).getAmount().longValue());
		}
	}

	@Test
	@MethodOrder(order = 7)
	public void operationSaleFailureTest() {
		Double sum = operationService.getAccountSum().getSum();
		Product product = productRepository.findByName("prod1");
		ConsumerRequest req = new ConsumerRequest("prod1", 2);
		SaleDto dto = operationService.operationSale(req);
		assertNotNull(dto);
		assertNotNull(dto.getDate());
		assertEquals(req.getProductName(), dto.getProductName());
		assertEquals(req.getAmount(), dto.getAmount());
		assertEquals(30d, dto.getCost(), 0.001);
		assertEquals(60d, dto.getTotalCost(), 0.001);
		assertEquals(Result.FAILURE.toString() + ": " + messageService.getMessage(Messages.STUFF_NOT_ENOUGH), dto.getResult());
		assertEquals(sum, operationService.getAccountSum().getSum(), 0.001);
		for(Map.Entry<Stuff, Integer> e : product.getComposition().entrySet()) {
			assertEquals(e.getKey().getAmount(), stuffRepository.findByName(e.getKey().getName()).getAmount());
		}
	}

	@Test
	@MethodOrder(order = 8)
	public void saleHistoryTest() {
		List<SaleDto> list = operationService.getSaleHistory(null, null);
		assertEquals(2, list.size());
		SaleDto dto = list.get(0);
		assertEquals("prod1", dto.getProductName());
		assertEquals(Result.SUCCESS.toString(), dto.getResult());
		dto = list.get(1);
		assertEquals("prod1", dto.getProductName());
		assertEquals(Result.FAILURE.toString(), dto.getResult());
	}



}
