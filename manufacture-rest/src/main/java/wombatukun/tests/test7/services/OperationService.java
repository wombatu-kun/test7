package wombatukun.tests.test7.services;

import wombatukun.tests.test7.dto.requests.ConsumerRequest;
import wombatukun.tests.test7.dto.requests.SupplierRequest;
import wombatukun.tests.test7.dto.responses.AccountResponse;
import wombatukun.tests.test7.dto.responses.SaleDto;
import wombatukun.tests.test7.dto.responses.SupplyDto;

import java.util.Date;
import java.util.List;

/**
 * Сервис для работы с закупками/продажами и счётом
 */
public interface OperationService {

	/**
	 * Выполняет продажу продукта
	 * @param request запрос покупателя
	 * @return информация о продаже
	 */
	SaleDto operationSale(ConsumerRequest request);

	/**
	 * Выполняет закупку материала
	 * @param request запрос поставщика
	 * @return информация о закупке
	 */
	SupplyDto operationSupply(SupplierRequest request);

	/**
	 * Возвращает текущее состояние счёта
	 * @return сумма на счёте и текущее время
	 */
	AccountResponse getAccountSum();

	/**
	 * Возвращает историю закупок
	 * @param from начальная дата (может быть null, тогда вернётся вся история)
	 * @param to конечная дата (может быть null, тогда вернётся история от from по н.в.)
	 * @return список закупок в указанном диапазоне дат
	 */
	List<SupplyDto> getSupplyHistory(Date from, Date to);

	/**
	 * Возвращает испторию продаж продуктов
	 * @param from начальная дата (может быть null, тогда вернётся вся история)
	 * @param to конечная дата (может быть null, тогда вернётся история от from по н.в.)
	 * @return список продаж в указанном диапазоне дат
	 */
	List<SaleDto> getSaleHistory(Date from, Date to);

}
