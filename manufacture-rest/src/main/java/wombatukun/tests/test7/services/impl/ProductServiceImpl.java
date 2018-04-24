package wombatukun.tests.test7.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wombatukun.tests.test7.converters.ProductConverter;
import wombatukun.tests.test7.dao.ProductRepository;
import wombatukun.tests.test7.dto.responses.ProductDto;
import wombatukun.tests.test7.entity.Product;
import wombatukun.tests.test7.exceptions.ResourceNotFoundException;
import wombatukun.tests.test7.messages.MessageService;
import wombatukun.tests.test7.messages.Messages;
import wombatukun.tests.test7.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductConverter productConverter;
	@Autowired
	private MessageService messageService;

	@Override
	public List<ProductDto> getAllProducts() {
		List<Product> productList = productRepository.findAll();
		return productList.stream()
				.map(productConverter::productToProductDto)
				.collect(Collectors.toList());
	}

	@Override
	public ProductDto getProductByName(String name) {
		Product product = productRepository.findByName(name);
		if (product == null) {
			throw new ResourceNotFoundException(messageService.getMessage(Messages.PRODUCT_NOT_FOUND, name));
		}
		return productConverter.productToProductDto(product);
	}
}
