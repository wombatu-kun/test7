package wombatukun.tests.test7.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wombatukun.tests.test7.converters.SaleConverter;
import wombatukun.tests.test7.converters.SupplyConverter;
import wombatukun.tests.test7.dao.ProductRepository;
import wombatukun.tests.test7.dao.SaleRepository;
import wombatukun.tests.test7.dao.StuffRepository;
import wombatukun.tests.test7.dao.SupplyRepository;
import wombatukun.tests.test7.dto.requests.ConsumerRequest;
import wombatukun.tests.test7.dto.requests.SupplierRequest;
import wombatukun.tests.test7.dto.responses.AccountResponse;
import wombatukun.tests.test7.dto.responses.SaleDto;
import wombatukun.tests.test7.dto.responses.SupplyDto;
import wombatukun.tests.test7.entity.Product;
import wombatukun.tests.test7.entity.Sale;
import wombatukun.tests.test7.entity.Stuff;
import wombatukun.tests.test7.entity.Supply;
import wombatukun.tests.test7.entity.enums.Result;
import wombatukun.tests.test7.exceptions.ResourceNotFoundException;
import wombatukun.tests.test7.messages.MessageService;
import wombatukun.tests.test7.messages.Messages;
import wombatukun.tests.test7.services.OperationService;
import wombatukun.tests.test7.validators.ConsumerRequestValidator;
import wombatukun.tests.test7.validators.SupplierRequestValidator;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {

	@Value("${initial.account.sum}")
	private double sum;

	@Autowired
	private SupplyRepository supplyRepository;
	@Autowired
	private SupplyConverter supplyConverter;
	@Autowired
	private SaleRepository saleRepository;
	@Autowired
	private SaleConverter saleConverter;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StuffRepository stuffRepository;
	@Autowired
	private MessageService messageService;
	@Autowired
	private SupplierRequestValidator supplierRequestValidator;
	@Autowired
	private ConsumerRequestValidator consumerRequestValidator;

	@Override
	public synchronized SaleDto operationSale(ConsumerRequest request) {
		Product product = consumerRequestValidator.validate(request);
		Sale sale = new Sale(new Date(), product, request.getAmount(), Result.SUCCESS);
		SaleDto dto = saleConverter.saleToSaleDto(sale);
		Set<Map.Entry<Stuff, Integer>> entrySet = product.getComposition().entrySet();
		Boolean enoughStuff = entrySet.stream().allMatch(e -> e.getKey().getAmount() >= e.getValue() * request.getAmount());
		if (enoughStuff) {
			sum = sum + dto.getTotalCost();
			product.getComposition().forEach((k, v) -> k.setAmount(k.getAmount() - v * request.getAmount()));
		} else {
			sale.setResult(Result.FAILURE);
			dto.setResult(Result.FAILURE + ": " + messageService.getMessage(Messages.STUFF_NOT_ENOUGH));
		}
		saleRepository.save(sale);
		return dto;
	}

	@Override
	public synchronized SupplyDto operationSupply(SupplierRequest request) {
		Stuff stuff = supplierRequestValidator.validate(request);
		Supply supply = new Supply(new Date(), stuff, request.getAmount(), request.getCost(), Result.SUCCESS);
		SupplyDto dto = supplyConverter.supplyToSupplyDto(supply);
		Double totalCost = request.getCost() * request.getAmount();
		if (sum >= totalCost) {
			sum = sum - totalCost;
			stuff.setAmount(stuff.getAmount() + request.getAmount());
		} else {
			supply.setResult(Result.FAILURE);
			dto.setResult(Result.FAILURE + ": " + messageService.getMessage(Messages.MONEY_NOT_ENOUGH));
		}
		supplyRepository.save(supply);
		return dto;
	}

	@Override
	public AccountResponse getAccountSum() {
		return new AccountResponse(sum, new Date());
	}

	@Override
	public List<SupplyDto> getSupplyHistory(Date from, Date to) {
		List<Supply> supplyList;
		if (from==null && to==null) {
			supplyList = supplyRepository.findAll();
		} else if (to == null) {
			supplyList = supplyRepository.findByDateAfterOrderByDateDesc(from);
		} else {
			supplyList = supplyRepository.findByDateBetweenOrderByDateDesc(from, to);
		}
		if (supplyList.isEmpty()) {
			throw new ResourceNotFoundException(messageService.getMessage(Messages.SUPPLIES_NOT_FOUND));
		}
		return supplyList.stream().map(supplyConverter::supplyToSupplyDto).collect(Collectors.toList());
	}

	@Override
	public List<SaleDto> getSaleHistory(Date from, Date to) {
		List<Sale> saleList;
		if (from==null && to==null) {
			saleList = saleRepository.findAll();
		} else if (to == null) {
			saleList = saleRepository.findByDateAfterOrderByDateDesc(from);
		} else {
			saleList = saleRepository.findByDateBetweenOrderByDateDesc(from, to);
		}
		if (saleList.isEmpty()) {
			throw new ResourceNotFoundException(messageService.getMessage(Messages.SALES_NOT_FOUND));
		}
		return saleList.stream().map(saleConverter::saleToSaleDto).collect(Collectors.toList());
	}

}
