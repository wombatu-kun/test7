package wombatukun.tests.test7.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wombatukun.tests.test7.dao.StuffRepository;
import wombatukun.tests.test7.dto.requests.SupplierRequest;
import wombatukun.tests.test7.entity.Stuff;
import wombatukun.tests.test7.exceptions.ResourceNotFoundException;
import wombatukun.tests.test7.exceptions.ValidationException;
import wombatukun.tests.test7.messages.MessageService;
import wombatukun.tests.test7.messages.Messages;

@Component
public class SupplierRequestValidator {

	@Autowired
	private StuffRepository stuffRepository;
	@Autowired
	private MessageService messageService;

	public Stuff validate(SupplierRequest request) {
		if (request == null) {
			throw new ValidationException(messageService.getMessage(Messages.REQUEST_IS_NULL));
		}
		if (StringUtils.isBlank(request.getStuffName())) {
			throw new ValidationException(messageService.getMessage(Messages.STUFF_NAME_NULL));
		}
		Integer amount = request.getAmount();
		if (amount == null || amount <= 0) {
			throw new ValidationException(messageService.getMessage(Messages.AMOUNT_IS_INVALID, amount));
		}
		Double cost = request.getCost();
		if (cost == null || cost <= 0) {
			throw new ValidationException(messageService.getMessage(Messages.COST_IS_INVALID, cost));
		}
		Stuff stuff = stuffRepository.findByName(request.getStuffName());
		if (stuff == null) {
			throw new ResourceNotFoundException(messageService.getMessage(Messages.STUFF_NOT_FOUND, request.getStuffName()));
		}
		return stuff;
	}
}
