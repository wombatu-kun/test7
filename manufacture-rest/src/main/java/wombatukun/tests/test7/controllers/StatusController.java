package wombatukun.tests.test7.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wombatukun.tests.test7.dto.responses.AccountResponse;
import wombatukun.tests.test7.dto.responses.ErrorDto;
import wombatukun.tests.test7.dto.responses.ProductDto;
import wombatukun.tests.test7.dto.responses.StuffDto;
import wombatukun.tests.test7.exceptions.ResourceNotFoundException;
import wombatukun.tests.test7.services.OperationService;
import wombatukun.tests.test7.services.ProductService;
import wombatukun.tests.test7.services.StuffService;

import java.util.List;

/**
 * Контроллер обработки статусных запросов
 */
@RestController
@RequestMapping("/status")
public class StatusController {
	private static final Logger LOG = LoggerFactory.getLogger(StatusController.class);

	@Autowired
	private StuffService stuffService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OperationService operationService;

	@RequestMapping(value = "/account", method = RequestMethod.GET, produces = "application/json")
	public AccountResponse getAccountSum() {
		return operationService.getAccountSum();
	}

	@RequestMapping(value = "/stuff", method = RequestMethod.GET, produces = "application/json")
	public List<StuffDto> getStuffAmount() {
		return stuffService.getAllStuff();
	}

	@RequestMapping(value = "/stuff/{name}", method = RequestMethod.GET, produces = "application/json")
	public StuffDto getStuffByName(@PathVariable String name) {
		return stuffService.getStuffByName(name);
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET, produces = "application/json")
	public List<ProductDto> getAllProducts() {
		return productService.getAllProducts();
	}

	@RequestMapping(value = "/products/{name}", method = RequestMethod.GET, produces = "application/json")
	public ProductDto getProductByName(@PathVariable String name) {
		return productService.getProductByName(name);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDto resourceNotFound(ResourceNotFoundException e) {
		return new ErrorDto(404, e.getMessage());
	}

}
