package wombatukun.tests.test7.messages.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import wombatukun.tests.test7.messages.MessageService;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

	@Value("${default.locale}")
	private Locale defaultLocale;
	@Autowired
	private MessageSource messageSource;

	@Override
	public String getMessage(String key, Object... params) {
		return messageSource.getMessage(key, params, defaultLocale);
	}
}
