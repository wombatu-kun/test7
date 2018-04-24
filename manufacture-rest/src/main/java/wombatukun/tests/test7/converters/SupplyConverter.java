package wombatukun.tests.test7.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import wombatukun.tests.test7.dto.responses.SupplyDto;
import wombatukun.tests.test7.entity.Supply;

/**
 * Конвретер информации о закупке материала
 */
@Mapper(componentModel = "spring")
public interface SupplyConverter {

	@Mappings({
		@Mapping(target = "stuffName", source = "stuff.name"),
		@Mapping(target = "totalCost", expression = "java( supply.getCost() * supply.getAmount() )")
	})
	SupplyDto supplyToSupplyDto(Supply supply);

}
