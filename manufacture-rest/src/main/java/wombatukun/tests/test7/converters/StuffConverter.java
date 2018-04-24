package wombatukun.tests.test7.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wombatukun.tests.test7.dto.responses.StuffDto;
import wombatukun.tests.test7.entity.Stuff;

@Mapper(componentModel = "spring")
public interface StuffConverter {

	@Mapping(source = "name", target = "stuffName")
	StuffDto stuffToStuffDto(Stuff stuff);

}
