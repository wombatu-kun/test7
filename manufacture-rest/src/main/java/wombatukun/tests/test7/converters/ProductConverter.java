package wombatukun.tests.test7.converters;

import wombatukun.tests.test7.dto.responses.ProductDto;
import wombatukun.tests.test7.entity.Product;

public interface ProductConverter {

	ProductDto productToProductDto(Product product);

}
