package wombatukun.tests.test7.services;

import wombatukun.tests.test7.dto.responses.StuffDto;

import java.util.List;

public interface StuffService {

	List<StuffDto> getAllStuff();

	StuffDto getStuffByName(String name);

}
