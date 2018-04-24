package wombatukun.tests.test7.validators;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wombatukun.tests.test7.dto.requests.ConsumerRequest;
import wombatukun.tests.test7.entity.Product;
import wombatukun.tests.test7.exceptions.ResourceNotFoundException;
import wombatukun.tests.test7.exceptions.ValidationException;
import wombatukun.tests.test7.messages.MessageService;
import wombatukun.tests.test7.messages.Messages;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerRequestValidatorTest {

	@Autowired
	private ConsumerRequestValidator consumerRequestValidator;
	@Autowired
	private MessageService messageService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void nullRequestTest() {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.REQUEST_IS_NULL));
		consumerRequestValidator.validate(null);
	}

	@Test
	public void emptyNameTest() {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.PRODUCT_NAME_NULL));
		ConsumerRequest req = new ConsumerRequest(null, null);
		consumerRequestValidator.validate(req);
	}

	@Test
	public void nullAmountTest() {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.AMOUNT_IS_INVALID, "null"));
		ConsumerRequest req = new ConsumerRequest("qwe", null);
		consumerRequestValidator.validate(req);
	}

	@Test
	public void invalidAmountTest() {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.AMOUNT_IS_INVALID, 0));
		ConsumerRequest req = new ConsumerRequest("qwe", 0);
		consumerRequestValidator.validate(req);
	}

	@Test
	public void stuffNotFoundTest() {
		expectedEx.expect(ResourceNotFoundException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.PRODUCT_NOT_FOUND, "qwe"));
		ConsumerRequest req = new ConsumerRequest("qwe", 1);
		consumerRequestValidator.validate(req);
	}

	@Test
	public void okTest() {
		ConsumerRequest req = new ConsumerRequest("prod1", 5);
		Product product = consumerRequestValidator.validate(req);
		assertNotNull(product);
	}

}
