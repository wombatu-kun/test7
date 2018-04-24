package wombatukun.tests.test7.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wombatukun.tests.test7.converters.StuffConverter;
import wombatukun.tests.test7.dao.StuffRepository;
import wombatukun.tests.test7.dto.responses.StuffDto;
import wombatukun.tests.test7.entity.Stuff;
import wombatukun.tests.test7.exceptions.ResourceNotFoundException;
import wombatukun.tests.test7.messages.MessageService;
import wombatukun.tests.test7.messages.Messages;
import wombatukun.tests.test7.services.StuffService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StuffServiceImpl implements StuffService{

	@Autowired
	private StuffRepository stuffRepository;
	@Autowired
	private StuffConverter stuffConverter;
	@Autowired
	private MessageService messageService;

	@Override
	public List<StuffDto> getAllStuff() {
		List<Stuff> stuffList = stuffRepository.findAll();
		return stuffList.stream().map(stuffConverter::stuffToStuffDto).collect(Collectors.toList());
	}

	@Override
	public StuffDto getStuffByName(String name) {
		Stuff stuff = stuffRepository.findByName(name);
		if (stuff == null) {
			throw new ResourceNotFoundException(messageService.getMessage(Messages.STUFF_NOT_FOUND, name));
		}
		return stuffConverter.stuffToStuffDto(stuff);
	}
}
