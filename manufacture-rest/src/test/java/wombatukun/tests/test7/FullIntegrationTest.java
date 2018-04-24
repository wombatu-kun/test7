package wombatukun.tests.test7;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import wombatukun.tests.test7.dev.MethodOrder;
import wombatukun.tests.test7.dev.SpringOrderedRunner;
import wombatukun.tests.test7.dto.requests.ConsumerRequest;
import wombatukun.tests.test7.dto.requests.SupplierRequest;
import wombatukun.tests.test7.dto.responses.*;
import wombatukun.tests.test7.entity.enums.Result;
import wombatukun.tests.test7.messages.MessageService;
import wombatukun.tests.test7.messages.Messages;

import static org.junit.Assert.*;


@RunWith(SpringOrderedRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class FullIntegrationTest {
	private static final Logger LOG = LoggerFactory.getLogger(FullIntegrationTest.class);

	@Value("${initial.account.sum}")
	private double initialSum;
	private StuffDto[] initialStuff;

	@Autowired
	private MessageService messageService;

	@Autowired
	private TestRestTemplate restTemplate;

	//================== Инициализация компании

	//------------------ Проверка стартового капитала
	@Test
	@MethodOrder(order = 1)
	public void initialSumTest() {
		AccountResponse resp = restTemplate.getForObject("/status/account", AccountResponse.class);
		assertNotNull(resp);
		assertEquals(initialSum, resp.getSum(), 0.001);
	}

	//------------------ Проверка стартового состояния материалов: номенклатура есть, а количество по нулям
	@Test
	@MethodOrder(order = 2)
	public void initialStuffTest() {
		initialStuff = restTemplate.getForObject("/status/stuff", StuffDto[].class);
		assertNotNull(initialStuff);
		assertEquals(3, initialStuff.length);
		for (StuffDto stuffDto : initialStuff) {
			switch (stuffDto.getStuffName()) {
				case "stuff1":
					assertEquals(10d, stuffDto.getCost(), 0.001);
					assertEquals(0, stuffDto.getAmount().longValue());
					break;
				case "stuff2":
					assertEquals(20d, stuffDto.getCost(), 0.001);
					assertEquals(0, stuffDto.getAmount().longValue());
					break;
				case "stuff3":
					assertEquals(30d, stuffDto.getCost(), 0.001);
					assertEquals(0, stuffDto.getAmount().longValue());
					break;
				default:
					fail("some surprise stuff");
			}
		}
	}

	//------------------ Проверка получения валидного материала по имени
	@Test
	@MethodOrder(order = 3)
	public void concreteStuffTest() {
		StuffDto stuffDto = restTemplate.getForObject("/status/stuff/stuff2", StuffDto.class);
		assertNotNull(stuffDto);
		assertEquals("stuff2", stuffDto.getStuffName());
		assertEquals(20d, stuffDto.getCost(), 0.001);
		assertEquals(0, stuffDto.getAmount().longValue());
	}

	//------------------ Проверка получения информации о несуществующем материале
	@Test
	@MethodOrder(order = 4)
	public void unknownStuffTest() {
		ResponseEntity<ErrorDto> resp = restTemplate.getForEntity("/status/stuff/stuff23", ErrorDto.class);
		assertNotNull(resp);
		assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
		ErrorDto error = resp.getBody();
		assertNotNull(error);
		assertEquals(404, error.getCode());
		assertEquals(messageService.getMessage(Messages.STUFF_NOT_FOUND, "stuff23"), error.getMessage());
	}

	//------------------ Проверка получения всего ассортимента продукции
	@Test
	@MethodOrder(order = 5)
	public void initialProductTest() {
		ProductDto[] products = restTemplate.getForObject("/status/products", ProductDto[].class);
		assertNotNull(products);
		assertEquals(2, products.length);
		for (ProductDto productDto : products) {
			switch (productDto.getProductName()) {
				case "prod1":
					assertEquals(30d, productDto.getTotalCost(), 0.001);
					assertEquals(2, productDto.getComposition().size());
					break;
				case "prod2":
					assertEquals(80d, productDto.getTotalCost(), 0.001);
					assertEquals(2, productDto.getComposition().size());
					break;
				default:
					fail("some surprise product");
			}
		}
	}

	//------------------ Проверка получения информации о продукте по имени
	@Test
	@MethodOrder(order = 6)
	public void concreteProductTest() {
		ProductDto productDto = restTemplate.getForObject("/status/products/prod2", ProductDto.class);
		assertNotNull(productDto);
		assertEquals("prod2", productDto.getProductName());
		assertEquals(80d, productDto.getTotalCost(), 0.001);
		assertEquals(2, productDto.getComposition().size());
		for (StuffDto stuffDto : productDto.getComposition()) {
			switch (stuffDto.getStuffName()) {
				case "stuff2":
					assertEquals(20d, stuffDto.getCost(), 0.001);
					assertEquals(1, stuffDto.getAmount().longValue());
					break;
				case "stuff3":
					assertEquals(30d, stuffDto.getCost(), 0.001);
					assertEquals(2, stuffDto.getAmount().longValue());
					break;
				default:
					fail("some surprise stuff in composition");
			}
		}
	}

	//------------------ Проверка получения информации о несуществующем продукте
	@Test
	@MethodOrder(order = 7)
	public void unknownProductTest() {
		ResponseEntity<ErrorDto> resp = restTemplate.getForEntity("/status/products/prod502", ErrorDto.class);
		assertNotNull(resp);
		assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
		ErrorDto error = resp.getBody();
		assertNotNull(error);
		assertEquals(404, error.getCode());
		assertEquals(messageService.getMessage(Messages.PRODUCT_NOT_FOUND, "prod502"), error.getMessage());
	}

	//------------------ Проверка ответа при отсутствии продаж
	@Test
	@MethodOrder(order = 8)
	public void noSalesTest() {
		ResponseEntity<ErrorDto> resp = restTemplate.getForEntity("/operations/sales", ErrorDto.class);
		assertNotNull(resp);
		assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
		ErrorDto error = resp.getBody();
		assertNotNull(error);
		assertEquals(404, error.getCode());
		assertEquals(messageService.getMessage(Messages.SALES_NOT_FOUND), error.getMessage());
	}

	//------------------ Проверка ответа при отсутствии поставок
	@Test
	@MethodOrder(order = 9)
	public void noSuppliesTest() {
		ResponseEntity<ErrorDto> resp = restTemplate.getForEntity("/operations/supplies", ErrorDto.class);
		assertNotNull(resp);
		assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
		ErrorDto error = resp.getBody();
		assertNotNull(error);
		assertEquals(404, error.getCode());
		assertEquals(messageService.getMessage(Messages.SUPPLIES_NOT_FOUND), error.getMessage());
	}

	//------------------ Успешная закупка материалов
	@Test
	@MethodOrder(order = 10)
	public void operationSupplySuccessTest() {
		double sum = restTemplate.getForObject("/status/account", AccountResponse.class).getSum();
		SupplierRequest req = new SupplierRequest("stuff1", 10, 50d);
		SupplyDto dto = restTemplate.postForObject("/operations/supplies", req, SupplyDto.class);
		assertNotNull(dto);
		assertEquals(req.getStuffName(), dto.getStuffName());
		assertEquals(req.getAmount(), dto.getAmount());
		assertEquals(req.getCost(), dto.getCost());
		assertEquals(500, dto.getTotalCost(), 0.001);
		assertEquals(Result.SUCCESS.toString(), dto.getResult());
		req = new SupplierRequest("stuff2", 3, 2d);
		dto = restTemplate.postForObject("/operations/supplies", req, SupplyDto.class);
		assertNotNull(dto);
		assertEquals(Result.SUCCESS.toString(), dto.getResult());
		sum = sum - 506;
		//------------------ Проверка изменений
		AccountResponse resp = restTemplate.getForObject("/status/account", AccountResponse.class);
		assertNotNull(resp);
		assertEquals(sum, resp.getSum(), 0.001);
		StuffDto stuffDto = restTemplate.getForObject("/status/stuff/stuff1", StuffDto.class);
		assertNotNull(stuffDto);
		assertEquals(10, stuffDto.getAmount().longValue());
		stuffDto = restTemplate.getForObject("/status/stuff/stuff2", StuffDto.class);
		assertNotNull(stuffDto);
		assertEquals(3, stuffDto.getAmount().longValue());
	}

	//------------------ Облом закупки материалов
	@Test
	@MethodOrder(order = 11)
	public void operationSupplyFailureTest() {
		double sum = restTemplate.getForObject("/status/account", AccountResponse.class).getSum();
		SupplierRequest req = new SupplierRequest("stuff2", 1000, 800d);
		SupplyDto dto = restTemplate.postForObject("/operations/supplies", req, SupplyDto.class);
		assertNotNull(dto);
		assertEquals(req.getStuffName(), dto.getStuffName());
		assertEquals(req.getAmount(), dto.getAmount());
		assertEquals(req.getCost(), dto.getCost());
		assertEquals(800000, dto.getTotalCost(), 0.001);
		assertEquals(Result.FAILURE.toString() + ": " + messageService.getMessage(Messages.MONEY_NOT_ENOUGH), dto.getResult());
		//------------------ Проверка изменений
		AccountResponse resp = restTemplate.getForObject("/status/account", AccountResponse.class);
		assertNotNull(resp);
		assertEquals(sum, resp.getSum(), 0.001);
		StuffDto stuffDto = restTemplate.getForObject("/status/stuff/stuff2", StuffDto.class);
		assertNotNull(stuffDto);
		assertEquals(3, stuffDto.getAmount().longValue());
	}

	//------------------ Проверка получения истории поставок
	@Test
	@MethodOrder(order = 12)
	public void getSuppliesTest() {
		SupplyDto[] resp = restTemplate.getForObject("/operations/supplies/2018-04-01", SupplyDto[].class);
		assertNotNull(resp);
		assertEquals(3, resp.length);
		assertEquals("stuff2", resp[0].getStuffName());
		assertEquals(1000, resp[0].getAmount().longValue());
		assertEquals(800000, resp[0].getTotalCost(), 0.001);
		assertEquals(Result.FAILURE.toString(), resp[0].getResult());
		assertEquals("stuff2", resp[1].getStuffName());
		assertEquals(3, resp[1].getAmount().longValue());
		assertEquals(6, resp[1].getTotalCost(), 0.001);
		assertEquals(Result.SUCCESS.toString(), resp[1].getResult());
		assertEquals("stuff1", resp[2].getStuffName());
		assertEquals(10, resp[2].getAmount().longValue());
		assertEquals(50, resp[2].getCost(), 0.001);
		assertEquals(500d, resp[2].getTotalCost(), 0.001);
		assertEquals(Result.SUCCESS.toString(), resp[2].getResult());
	}

	//------------------ Успешная продажа продукта
	@Test
	@MethodOrder(order = 13)
	public void operationSaleSuccessTest() {
		double sum = restTemplate.getForObject("/status/account", AccountResponse.class).getSum();
		ConsumerRequest req = new ConsumerRequest("prod1", 2);
		SaleDto dto = restTemplate.postForObject("/operations/sales", req, SaleDto.class);
		assertNotNull(dto);
		assertEquals(req.getProductName(), dto.getProductName());
		assertEquals(req.getAmount(), dto.getAmount());
		assertEquals(60, dto.getTotalCost(), 0.001);
		assertEquals(Result.SUCCESS.toString(), dto.getResult());
		sum = sum + 60;
		//------------------ Проверка изменений
		AccountResponse resp = restTemplate.getForObject("/status/account", AccountResponse.class);
		assertNotNull(resp);
		assertEquals(sum, resp.getSum(), 0.001);
		StuffDto stuffDto = restTemplate.getForObject("/status/stuff/stuff1", StuffDto.class);
		assertNotNull(stuffDto);
		assertEquals(8, stuffDto.getAmount().longValue());
		stuffDto = restTemplate.getForObject("/status/stuff/stuff2", StuffDto.class);
		assertNotNull(stuffDto);
		assertEquals(1, stuffDto.getAmount().longValue());
	}

	//------------------ Облом продажи продукта
	@Test
	@MethodOrder(order = 14)
	public void operationSaleFailureTest() {
		double sum = restTemplate.getForObject("/status/account", AccountResponse.class).getSum();
		ConsumerRequest req = new ConsumerRequest("prod1", 2);
		SaleDto dto = restTemplate.postForObject("/operations/sales", req, SaleDto.class);
		assertNotNull(dto);
		assertEquals(req.getProductName(), dto.getProductName());
		assertEquals(req.getAmount(), dto.getAmount());
		assertEquals(60, dto.getTotalCost(), 0.001);
		assertEquals(Result.FAILURE.toString() + ": " + messageService.getMessage(Messages.STUFF_NOT_ENOUGH), dto.getResult());
		//------------------ Проверка изменений
		AccountResponse resp = restTemplate.getForObject("/status/account", AccountResponse.class);
		assertNotNull(resp);
		assertEquals(sum, resp.getSum(), 0.001);
		StuffDto stuffDto = restTemplate.getForObject("/status/stuff/stuff1", StuffDto.class);
		assertNotNull(stuffDto);
		assertEquals(8, stuffDto.getAmount().longValue());
		stuffDto = restTemplate.getForObject("/status/stuff/stuff2", StuffDto.class);
		assertNotNull(stuffDto);
		assertEquals(1, stuffDto.getAmount().longValue());
	}

	//------------------ Проверка получения истории продаж
	@Test
	@MethodOrder(order = 15)
	public void getSalesTest() {
		SaleDto[] resp = restTemplate.getForObject("/operations/sales/2018-04-01", SaleDto[].class);
		assertNotNull(resp);
		assertEquals(2, resp.length);
		assertEquals("prod1", resp[0].getProductName());
		assertEquals(2, resp[0].getAmount().longValue());
		assertEquals(30, resp[0].getCost(), 0.001);
		assertEquals(60, resp[0].getTotalCost(), 0.001);
		assertEquals(Result.FAILURE.toString(), resp[0].getResult());
		assertEquals("prod1", resp[1].getProductName());
		assertEquals(2, resp[1].getAmount().longValue());
		assertEquals(30, resp[1].getCost(), 0.001);
		assertEquals(60, resp[1].getTotalCost(), 0.001);
		assertEquals(Result.SUCCESS.toString(), resp[1].getResult());
	}

}
