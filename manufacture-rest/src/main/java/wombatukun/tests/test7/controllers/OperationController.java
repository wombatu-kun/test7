package wombatukun.tests.test7.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wombatukun.tests.test7.dto.requests.ConsumerRequest;
import wombatukun.tests.test7.dto.requests.SupplierRequest;
import wombatukun.tests.test7.dto.responses.ErrorDto;
import wombatukun.tests.test7.dto.responses.SaleDto;
import wombatukun.tests.test7.dto.responses.SupplyDto;
import wombatukun.tests.test7.exceptions.ResourceNotFoundException;
import wombatukun.tests.test7.exceptions.ValidationException;
import wombatukun.tests.test7.services.OperationService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationController {
	private static final Logger LOG = LoggerFactory.getLogger(OperationController.class);

	@Autowired
	private OperationService operationService;

	@RequestMapping(value = "/sales", method = RequestMethod.POST, produces = "application/json")
	public SaleDto saleAction(@RequestBody ConsumerRequest consumerRequest) {
		return operationService.operationSale(consumerRequest);
	}

	@RequestMapping(value = "/supplies", method = RequestMethod.POST, produces = "application/json")
	public SupplyDto supplyAction(@RequestBody SupplierRequest supplierRequest) {
		return operationService.operationSupply(supplierRequest);
	}

	@RequestMapping(value = "/sales", method = RequestMethod.GET, produces = "application/json")
	public List<SaleDto> getAllSales() {
		return operationService.getSaleHistory(null, null);
	}

	@RequestMapping(value = "/sales/{from}", method = RequestMethod.GET, produces = "application/json")
	public List<SaleDto> getSalesFrom(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date from) {
		return operationService.getSaleHistory(from, null);
	}

	@RequestMapping(value = "/sales/{from}/{to}", method = RequestMethod.GET, produces = "application/json")
	public List<SaleDto> getSalesFromTo(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
											 @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
		return operationService.getSaleHistory(from, to);
	}

	@RequestMapping(value = "/supplies", method = RequestMethod.GET, produces = "application/json")
	public List<SupplyDto> getAllSupplies() {
		return operationService.getSupplyHistory(null, null);
	}

	@RequestMapping(value = "/supplies/{from}", method = RequestMethod.GET, produces = "application/json")
	public List<SupplyDto> getSuppliesFrom(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date from) {
		return operationService.getSupplyHistory(from, null);
	}

	@RequestMapping(value = "/supplies/{from}/{to}", method = RequestMethod.GET, produces = "application/json")
	public List<SupplyDto> getSuppliesFromTo(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
											 @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
		return operationService.getSupplyHistory(from, to);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDto resourceNotFound(ResourceNotFoundException e) {
		return new ErrorDto(404, e.getMessage());
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDto badRequest(ValidationException e) {
		return new ErrorDto(400, e.getMessage());
	}

}
