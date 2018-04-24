package wombatukun.tests.test7.converters;

import wombatukun.tests.test7.dto.responses.SaleDto;
import wombatukun.tests.test7.entity.Sale;

public interface SaleConverter {

	SaleDto saleToSaleDto(Sale sale);

}
