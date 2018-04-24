package wombatukun.tests.test7.services;

import wombatukun.tests.test7.dto.requests.ConsumerRequest;
import wombatukun.tests.test7.dto.requests.SupplierRequest;
import wombatukun.tests.test7.dto.responses.AccountResponse;
import wombatukun.tests.test7.dto.responses.SaleDto;
import wombatukun.tests.test7.dto.responses.SupplyDto;

import java.util.Date;
import java.util.List;

public interface OperationService {

	SaleDto operationSale(ConsumerRequest request);

	SupplyDto operationSupply(SupplierRequest request);

	AccountResponse getAccountSum();

	List<SupplyDto> getSupplyHistory(Date from, Date to);

	List<SaleDto> getSaleHistory(Date from, Date to);
}
