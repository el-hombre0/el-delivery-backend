# El-Delivery
## _Сервис  по вызову мобильной зарядной станции для электромобиля_

### Локальная сборка
1. Запустить СУБД PostgreSQL в Docker-контейнере ``` docker run -it -d -e POSTGRES_DB=eldelivery POSTGRES_PASSWORD=none --net=host postgres:14-alpine3.19 ```
2. Собрать серверную часть веб-приложения с помощью Gradle ``` gradle build ```
3. Запустить серверную часть ``` java -jar build/libs/server-0.0.1-SNAPSHOT.jar ```
4. Установить зависимости для клиентской части веб-приложения ``` npm install ```
5. Запустить клиентскую часть ``` npm run start ```
6. Перейти по адресу http://localhost:3000/