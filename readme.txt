Инструкция

Конфигурирование перед сборкой:
1) В manufacture-rest/src/main/resources отредактировать data.sql, указав что и из чего будет производиться (по аналогии с тем, что там написано, задать наборы материалов, продуктов и составы продуктов).
2) В manufacture-rest/src/main/resources отредактировать config.properties (параметры можно будет переопределить и после сборки):
    # номер порта, на котором будет висеть сервис
    server.port=8888
    # язык сообщений об ошибках (есть локализации для ru_RU и en_US)
    default.locale=ru_RU
    # начальное количество денег на счёте
    initial.account.sum=720000


Сборка:
В корне проекта выполнить команду: mvn clean package

Все необходимые для запуска файлы появятся в папке manufacture-rest/target, а именно:
1) manufacture.jar - запускаемый jar;
2) lib/ - папка с библиотечными зависимостями;
3) config.properties - конфиг для переопределения параметров запуска;
4) manufacture-ws.zip - дистрибутив, включающий 3 пункта выше и исходники проекта.


Запуск:
1) После сборки перейти в папку manufacture-rest/target
2) Отредактировать config.properties (если необходимо)
3) Выполнить команду: java -jar -Dconfig.file=config.properties manufacture.jar

* Для запуска сервиса не из target, нужно переместить в новое место manufacture.jar вместе с папкой lib и файлом config.properties.
** config.properties не обязательно должен лежать рядом с jar.
*** Если не задать -Dconfig.file или указать некорректный путь к конфигу, будет использована конфигурация по умолчанию (заданная в manufacture-rest/src/main/resources/config.properties перед сборкой).


Взаимодействие с сервисом:
GET /status/account - текущее состояние счёта
GET /status/stuff - текущие запасы материалов
GET /status/stuff/stuffName - информация о материале по названию
GET /status/products - ассортимент продуктов
GET /status/products/productName - информация о продукте по названию
GET /operations/supplies - вся история закупок материалов
GET /operations/supplies/2018-04-01 - история закупок, начиная с 1го апреля 2018 (дата может быть любой, главное формат)
GET /operations/supplies/2018-04-01/2018-05-01 - история закупок за период
GET /operations/sales - вся история продаж продуктов
GET /operations/sales/2018-04-01 - история продаж, начиная с 1го апреля 2018 (дата может быть любой, главное yyyy-MM-dd)
GET /operations/sales/2018-04-01/2018-05-01 - история продаж за период
POST /operations/supplies - закупка материала, принимается SupplierRequest
POST /operations/sales - продажа продукта, принимается ConsumerRequest

Сервис работает с транспортами в формате json, транспортные классы находятся в модуле manufacture-dto в пакете wombatukun.tests.test7.dto
Логи с запросами и ответами сервиса пишутся в папку logs рядом с manufacture.jar.