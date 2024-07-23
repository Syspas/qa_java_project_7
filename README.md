# Sprint_7 Project

Этот проект разработан для автоматизированного тестирования API учебного сервиса Яндекс.
Самокат в рамках финального проекта 7 спринта.

## О проекте

Цель проекта — проведение автоматизированного тестирования API сервиса Яндекс.
Самокат с использованием Java, Maven, JUnit 5 и RestAssured. 
В проекте также используется Allure для генерации отчетов о выполненных тестах.

## Подготовка к тестированию

Прежде чем приступить к написанию тестов, необходимо выполнить следующие шаги:

1. Создание Maven проекта:

   ```shell
   mvn archetype:generate -DgroupId=com.example -DartifactId=Sprint_7 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
   ```

2. Добавление зависимостей в файл pom.xml:
   - JUnit 4
   - RestAssured
   - Allure (для генерации отчетов)

3. Ручное тестирование в Postman:
   - Изучите API вручную, используя документацию: [qa-scooter.praktikum-services.ru/docs/](http://qa-scooter.praktikum-services.ru/docs/)

4. Настройка проекта:
   - Создайте необходимые классы для тестирования.
   - Настройте окружение для работы с RestAssured и JUnit.

## Генерация отчетов Allure

Для создания отчетов Allure выполните следующие действия после выполнения тестов:

1. Сгенерируйте отчет Allure:

   ```shell
   mvn allure:report
   ```

2. Добавьте отчет в репозиторий (не добавляйте всю папку target):

   ```shell
   git add -f ./target/allure-results/.
   git commit -m "Add Allure report"
   git push
   ```

### Просмотр отчета в веб-версии прогона

Для просмотра отчета в веб-версии выполненных тестов используйте следующую команду:

```shell
allure serve target\allure-results
```

### Запуск тестов

Чтобы запустить тесты, выполните команду:

```shell
mvn clean test
```