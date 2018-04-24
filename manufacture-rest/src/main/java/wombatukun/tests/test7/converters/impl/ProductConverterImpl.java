package wombatukun.tests.test7.converters.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wombatukun.tests.test7.converters.ProductConverter;
import wombatukun.tests.test7.converters.StuffConverter;
import wombatukun.tests.test7.dto.responses.ProductDto;
import wombatukun.tests.test7.dto.responses.StuffDto;
import wombatukun.tests.test7.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductConverterImpl implements ProductConverter {

	@Autowired
	private StuffConverter stuffConverter;

	@Override
	public ProductDto productToProductDto(Product product) {
		List<StuffDto> stuffList = product.getComposition().entrySet().stream().map(e -> {
			StuffDto dto = stuffConverter.stuffToStuffDto(e.getKey());
			dto.setAmount(Long.valueOf(e.getValue()));
			return dto;
		}).collect(Collectors.toList());
		return new ProductDto(product.getName(), stuffList);
	}
}
