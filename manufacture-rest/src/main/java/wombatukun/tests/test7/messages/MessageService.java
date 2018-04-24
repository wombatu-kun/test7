package wombatukun.tests.test7.messages;

/**
 * Сервис получения локализованных сообщений об ошибках
 */
public interface MessageService {

	/**
	 * Возвращает локалиованное в дефолтной локали сообщение по ключу
	 * @param key ключ
	 * @param params параметры для подстановки
	 * @return сообщение
	 */
	String getMessage(String key, Object... params);

}
