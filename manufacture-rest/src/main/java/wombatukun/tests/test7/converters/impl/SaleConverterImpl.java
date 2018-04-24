package wombatukun.tests.test7.converters.impl;

import org.springframework.stereotype.Service;
import wombatukun.tests.test7.converters.SaleConverter;
import wombatukun.tests.test7.dto.responses.SaleDto;
import wombatukun.tests.test7.entity.Product;
import wombatukun.tests.test7.entity.Sale;

@Service
public class SaleConverterImpl implements SaleConverter {

	@Override
	public SaleDto saleToSaleDto(Sale sale) {
		Product product = sale.getProduct();
		Double productCost = product.getComposition().entrySet().stream()
				.mapToDouble(e -> e.getKey().getCost() * e.getValue()).sum();
		return new SaleDto(sale.getDate(), product.getName(), sale.getAmount(), productCost, sale.getResult().toString());
	}

}
