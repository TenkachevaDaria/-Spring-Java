# Практическая работа №4.10
### Сборка и запуск кода
Для сборки кода используется Gradle.
Вы можете запускать код, используя любую среду разработки,
поддерживающую Gradle, например, Intellij IDEA, открыв текущий проект и выбрав задачу *bootRun*.

Возможен запуск из терминала или консоли:

Linux & MacOS:
``
./gradlew bootRun
``

Windows:``
gradlew bootRun
``

### Контейнеризация приложения
Вы можете создать образ docker, используя ``Dockerfile``, приведенный в данном проекте.
Прежде чем использовать ``Dockerfile`` вы обязаны создать jar-файл программы.
Используйте следующую команду взависимости от операционной системы:

Linux & MacOS: ``./gradlew bootJar``

Windows: ``gradlew bootJar``

Она создаст исполняемый файл ``work-2.7.jar`` в каталоге ``build/libs/``.
После этого создайте docker образ следующим образом (вызывайте из корневой папки проекта):

```bash
sudo docker build --build-arg JAR_FILE=build/libs/\*.jar -t springio/gs-spring-boot-docker .
``` 

Запустите образ:
```bash
sudo docker run -p 8080:8080 -t -i springio/gs-spring-boot-docker 
```

### Работа программы
Программа представляет собой веб-приложение, управляющее базой данных новостей, с графическим интерфесом.
Позволяет добавлять новые новости, редактировать существующие, удалять.
По умолчанию слушает порт 8080 и доступна по адресу ``http://localhost:8080``.

Эндпоинты (пользователь):
1. (GET) /api/person/{name} - возвращает пользователя по указанному имени
2. (GET) /api/person/all - возвращает всех пользователей.
3. (POST) /api/person - добавляет пользователя в базу данных (укажите name и password как параметр запроса).
4. (PUT) /api/person/{id} - редактирует пользователя (укажите name и password для их смены)
5. (DELETE) /api/person/{id} - удаляет пользователя по указанному id

Эндпоинты (категория):
1. (GET) /api/category/{id} - возвращает категорию по указанному id
2. (GET) /api/category/all - возвращает все категории.
3. (POST) /api/category - добавляет категорию в базу данных (укажите name в параметрах запроса).
4. (PUT) /api/person/{id} - редактирует категорию (укажите name для смены)
5. (DELETE) /api/person/{id} - удаляет категорию по указанному id

Эндпоинты (новость):
1. (GET) /api/news/{id} - возвращает новость по указанному id
2. (GET) /api/news/all - возвращает все новости (доступна пагинация и фильтрация: укажите в параметрах запроса categoryId или authorId для фильтрации).
3. (POST) /api/news - добавляет новость в базу данных (укажите name, categoryId, content в параметрах запроса, а также хедер Authorization: Basic param, где param - строка, закодированная в base64 в формате ``name:password``).
4. (PUT) /api/news/{id} - редактирует новость (укажите name, categoryId, content в параметрах запроса, а также хедер Authorization: Basic param, где param - строка, закодированная в base64 в формате ``name:password``).
5. (DELETE) /api/news/{id} - удаляет новость (укажите хедер Authorization: Basic param, где param - строка, закодированная в base64 в формате ``name:password``).

Эндпоинты (комментарий):
1. (GET) /api/comment/{id} - возвращает комментарий по указанному id
2. (GET) /api/comment/all/{newsId} - возвращает все комментарии, относящиеся к новости.
3. (POST) /api/comment - добавляет коммент в базу данных (укажите newsId, content в параметрах запроса, а также хедер Authorization: Basic param, где param - строка, закодированная в base64 в формате ``name:password``).
4. (PUT) /api/comment/{id} - редактирует коммент (укажите newsId, content в параметрах запроса, а также хедер Authorization: Basic param, где param - строка, закодированная в base64 в формате ``name:password``).
5. (DELETE) /api/comment/{id} - удаляет коммент (укажите хедер Authorization: Basic param, где param - строка, закодированная в base64 в формате ``name:password``).

Программа корректно обрабатывает пользовательский ввод.
В случае оставления поля пустым программа уведомит пользователя о неправильном заполнении данных.
В случае заполенения поля превышающей длинной символов этого поля программа также уведомит пользователя об ошибке.

### Конфигурация программы
В файле ```application.properties``` вы можете изменить параметры программы.

По умолчанию программа работает с базой данных H2, однако вы можете заменить её на MySQL.

#### H2
Если вы хотите использовать H2, то вы должны изменить данные параметры под свои или оставить так как есть:
```properties
spring.datasource.url=jdbc:h2:mem:db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=user
spring.datasource.password=
spring.h2.console.enabled=true
```

#### MySQL
Вы можете использовать локально установленную базу данных, мы воспользуемся развертыванием базы данных посредством docker.

Данная команда запускает контейнер MySQL c перенаправлением портов в локальной сети:
```bash
docker run -p 3307:3306 --name my-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_DATABASE=mydb -d mysql:latest
```

Получите доступ к терминалу MySQL:
```bash
docker exec -it my-mysql bash
```

Авторизуйтесь в MySQL:
```bash
mysql -u root -p
```

Выберите нашу базу данных под названием mydb:
```sql
use mydb;
```

Создайте в базе данных таблицу контактов:
```sql
CREATE TABLE contacts(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(32),
    last_name VARCHAR(32),
    email VARCHAR(320),
    phone VARCHAR(32)
);

INSERT INTO contacts(first_name, last_name, email, phone)
    VALUES('John', 'Doe', 'johndoe@mail.example', '89205553535'),
          ('Ann', 'Doe', 'anndoe@mail.example', '89206363636'),
          ('Daria', 'Tenckacheva', 'dariatenckacheva@mail.example', '89512145467');
```

Выйдите из окружения командой ``exit``,

В ``application.properties`` укажите следующее:
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/mydb
spring.datasource.username=root
spring.datasource.password=my-secret-pw
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
```

Запускайте программу локально на вашем коспьютере и вы увидите, что теперь используется mysql.

#### Конфигурация в ``applicaiton.properties``

``spring.datasource.url`` - название драйвера и базы данных
``spring.datasource.driverClassName`` - указание драйвера
``spring.datasource.username`` - имя пользователя
``spring.datasource.password`` - пароль пользователя
``spring.h2.console.enabled`` - включение консоли H2 доступной на ``/h2-console/``
