package wombatukun.tests.test7.services;

import wombatukun.tests.test7.dto.responses.ProductDto;

import java.util.List;

/**
 * Сервис для работы с продуктами
 */
public interface ProductService {

	/**
	 * Возвращает весь ассоримент продкутов
	 * @return список производимых продуктов
	 */
	List<ProductDto> getAllProducts();

	/**
	 * Возвращает продукт по названию
	 * @param name название продукта
	 * @return информация о запрошенном продукте
	 */
	ProductDto getProductByName(String name);

}
