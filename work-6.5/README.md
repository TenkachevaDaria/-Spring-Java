# Практическая работа №6.5
### Сборка и запуск кода
Запустите docker образ Kafka:
```
sudo docker run -d --name=kafka -p 9092:9092 apache/kafka
```

Запустите order-service
```
./gradlew order-service:bootRun
```

Запустите order-status-service
```
./gradlew order-status-service:bootRun
```

Отправьте post-запрос на эндпоинт ``localhost:8080/order`` с параметрами ``product`` и ``quantity``