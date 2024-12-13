# Практическая работа №7.6
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
### MongoDB
Для корректной работы программы необходимо запустить MongoDB:
```bash
sudo docker run -d -p 27017:27017 mongo:latest
```

### Программа
Программа слушает порт ``8080``.

Пользователь:
1. (GET) /user/{id}
2. (GET) /user
3. (POST) /user
4. (DELETE) /user/{id}
5. (PUT) /user/{id}

Задача:
1. (GET) /task
2. (GET) /task/{id}
3. (POST) /task - причём тело запроса должно быть в формате JSON
4. (PUT) /task/{id} - причём тело запроса должно быть в формате JSON
5. (PUT) /task/{taskId}/{observerId}
6. (DELETE) /task/{taskId}
