package wombatukun.tests.test7.validators;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wombatukun.tests.test7.dto.requests.SupplierRequest;
import wombatukun.tests.test7.entity.Stuff;
import wombatukun.tests.test7.exceptions.ResourceNotFoundException;
import wombatukun.tests.test7.exceptions.ValidationException;
import wombatukun.tests.test7.messages.MessageService;
import wombatukun.tests.test7.messages.Messages;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplierRequestValidatorTest {

	@Autowired
	private SupplierRequestValidator supplierRequestValidator;
	@Autowired
	private MessageService messageService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void nullRequestTest() {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.REQUEST_IS_NULL));
		supplierRequestValidator.validate(null);
	}

	@Test
	public void emptyNameTest() {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.STUFF_NAME_NULL));
		SupplierRequest req = new SupplierRequest(null, null, null);
		supplierRequestValidator.validate(req);
	}

	@Test
	public void nullAmountTest() {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.AMOUNT_IS_INVALID, "null"));
		SupplierRequest req = new SupplierRequest("qwe", null, null);
		supplierRequestValidator.validate(req);
	}

	@Test
	public void invalidAmountTest() {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.AMOUNT_IS_INVALID, 0));
		SupplierRequest req = new SupplierRequest("qwe", 0, null);
		supplierRequestValidator.validate(req);
	}

	@Test
	public void nullCostTest() {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.COST_IS_INVALID, "null"));
		SupplierRequest req = new SupplierRequest("qwe", 1, null);
		supplierRequestValidator.validate(req);
	}

	@Test
	public void invalidCostTest() {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.COST_IS_INVALID, -0.5));
		SupplierRequest req = new SupplierRequest("qwe", 1, -0.5);
		supplierRequestValidator.validate(req);
	}

	@Test
	public void stuffNotFoundTest() {
		expectedEx.expect(ResourceNotFoundException.class);
		expectedEx.expectMessage(messageService.getMessage(Messages.STUFF_NOT_FOUND, "qwe"));
		SupplierRequest req = new SupplierRequest("qwe", 1, 1d);
		supplierRequestValidator.validate(req);
	}

	@Test
	public void okTest() {
		SupplierRequest req = new SupplierRequest("stuff1", 1, 1d);
		Stuff stuff = supplierRequestValidator.validate(req);
		assertNotNull(stuff);
	}

}
