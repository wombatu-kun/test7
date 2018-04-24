package wombatukun.tests.test7.converters;

import wombatukun.tests.test7.dto.responses.ProductDto;
import wombatukun.tests.test7.entity.Product;

/**
 * Конвертер информации о продукте
 */
public interface ProductConverter {

	ProductDto productToProductDto(Product product);

}
