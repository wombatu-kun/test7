package wombatukun.tests.test7.services;

import wombatukun.tests.test7.dto.responses.StuffDto;

import java.util.List;

/**
 * Сервис для работы с материалами
 */
public interface StuffService {

	/**
	 * Возвращает весь список материалов, которые известны на производстве (не обязательно задействованы в продуктах)
	 * @return список материалов
	 */
	List<StuffDto> getAllStuff();

	/**
	 * Возвращает материал по названию
	 * @param name название материала
	 * @return информация о запрошенном материале
	 */
	StuffDto getStuffByName(String name);

}
