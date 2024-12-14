# Практическая работа #8.10
### Сборка и запуск кода
Для сборки кода используется Gradle.
Вы можете запускать код, используя любую среду разработки,
поддерживающую Gradle, например, Intellij IDEA, открыв текущий проект и выбрав задачу *bootRun*.

Возможен запуск из терминала или консоли:

Linux & MacOS:
``
./gradlew <tasks или news>:bootRun
``

Windows:``
gradlew <tasks или news>:bootRun
``

### MongoDB
Для корректной работы tasks необходимо запустить MongoDB:
```bash
sudo docker run -d -p 27017:27017 mongo:latest
```
