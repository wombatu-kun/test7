package wombatukun.tests.test7.services;

import wombatukun.tests.test7.dto.responses.ProductDto;

import java.util.List;

public interface ProductService {

	List<ProductDto> getAllProducts();
	ProductDto getProductByName(String name);

}
