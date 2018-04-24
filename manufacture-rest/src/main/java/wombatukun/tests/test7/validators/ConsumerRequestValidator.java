package wombatukun.tests.test7.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wombatukun.tests.test7.dao.ProductRepository;
import wombatukun.tests.test7.dto.requests.ConsumerRequest;
import wombatukun.tests.test7.entity.Product;
import wombatukun.tests.test7.exceptions.ResourceNotFoundException;
import wombatukun.tests.test7.exceptions.ValidationException;
import wombatukun.tests.test7.messages.MessageService;
import wombatukun.tests.test7.messages.Messages;

@Component
public class ConsumerRequestValidator {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private MessageService messageService;

	public Product validate(ConsumerRequest request) {
		if (request == null) {
			throw new ValidationException(messageService.getMessage(Messages.REQUEST_IS_NULL));
		}
		if (StringUtils.isBlank(request.getProductName())) {
			throw new ValidationException(messageService.getMessage(Messages.PRODUCT_NAME_NULL));
		}
		Integer amount = request.getAmount();
		if (amount == null || amount <= 0) {
			throw new ValidationException(messageService.getMessage(Messages.AMOUNT_IS_INVALID, amount));
		}
		Product product = productRepository.findByName(request.getProductName());
		if (product == null) {
			throw new ResourceNotFoundException(messageService.getMessage(Messages.PRODUCT_NOT_FOUND, request.getProductName()));
		}
		return product;
	}
}
