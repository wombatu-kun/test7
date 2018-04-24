Инструкция

Конфигурирование:
1) В manufacture-rest/src/main/resources отредактировать config.properties (при необходимости):
    # номер порта, накотором будет висеть сервис
    server.port=8888
    # язык для сообщений об ошибках (есть локализации для ru_RU и en_US)
    default.locale=ru_RU
    # начальное количество денег на счёте
    initial.account.sum=720000
2) В manufacture-rest/src/main/resources отредактировать data.sql (по аналогии с тем, что там написано,
задать набор материалов, продуктов и составы продуктов).

Сборка:
В корне проекта выполнить команду: mvn package

Запуск:
1) После сборки перейти в папку manufacture-rest/target;
2) Выполнить команду: java -jar manufacture.jar
* Для запуска сервиса не из target, нужно переместить в новое место manufacture.jar вместе с папкой lib

Взаимодействие с сервисом:
GET /status/account - текущее состояние счёта
GET /status/stuff - текущие запасы материалов
GET /status/stuff/stuffName - информация о материале по названию
GET /status/products - ассортимент продуктов
GET /status/products/productName - информация о продукте по названию
GET /operations/supplies - вся история закупок
GET /operations/supplies/2018-04-01 - история закупок, начаиная с 1го апреля 2018 (дата может быть любой, главное формат)
GET /operations/supplies/2018-04-01/2018-05-01 - история закупок за период
GET /operations/sales - вся история продаж
GET /operations/sales/2018-04-01 - история продаж, начаиная с 1го апреля 2018 (дата может быть любой, главное формат)
GET /operations/sales/2018-04-01/2018-05-01 - история продаж за период
POST /operations/supplies - закупка материала
POST /operations/sales - продажа продукта
Сервис работает с транспортами в формате json, транспортые классы находятся в модуле manufacture-dto.