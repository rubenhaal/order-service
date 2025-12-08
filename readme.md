## 🌍 Languages
- [English](#English)
- [Deutsch](#Deutsch)


## English

## Order-service

`order-service` is a backend microservice for a fictional e-commerce platform.  
Its responsibility is to manage standard customer orders for an online shop: 
receiving new orders, exposing their status, and acting as the main entry point 
for order-related operations.

## Business context and responsibilities

Within a typical e-commerce architecture, `order-service` would:

- Expose REST APIs to:
    - create a new order for a customer
    - retrieve order details by id
    - list orders for a given customer
    - update order status (for example, CREATED, PAID, SHIPPED, CANCELLED)

- Coordinate with other microservices such as:
    - payment-service (to validate and confirm payments)
    - inventory-service (to reserve and release stock)
    - notification-service (to send emails or messages to the customer)

## Technologies

Java 21  
Spring Boot (Web, Actuator)  
Spring Cloud Config Client  
Lombok  
springdoc-openapi (Swagger UI)  
JUnit 5, Spring Boot Test

## Configuration

This service reads part of its configuration from a Config Server.

Application name: order-service

```yaml

# Expected properties on the Config Server (for example in order-service.yml):
order-service:
  welcome-message: "Hello from Config Server"
  environment: "dev"
```
Client configuration in the service application.yml:

```yaml

server:
  port: 8081

spring:
  application:
    name: order-service
  config:
    import: "optional:configserver:http://localhost:8888"

management:
  endpoints:
    web:
      exposure:
        include: health,info
  endpoint:
    health:
      show-details: always
```
## Observability

### Structured JSON logs
The service outputs structured logs in JSON format (Logback + logstash encoder).  
Each log entry includes common fields (timestamp, level, logger, thread) and custom fields such as `app` and `env`.

### Request correlation (X-Trace-Id)

Each incoming HTTP request is assigned a trace identifier:

- If the client sends `X-Trace-Id`, the service reuses it.
- If it is missing, the service generates a new one.
- The value is returned in the response header `X-Trace-Id`.

This enables log correlation across requests and services.

### Metrics (Actuator)

Metrics are exposed via Spring Boot Actuator:

- `GET /actuator/metrics`
- `GET /actuator/metrics/http.server.requests`

Example (PowerShell):

```powershell
curl.exe -i http://localhost:8081/api/order/demo
curl.exe -i -H "X-Trace-Id: demo-123" http://localhost:8081/api/order/demo
```

## Run locally

Requirements:

- Java 21
- Maven or Gradle
- Config Server running at http://localhost:8888 serving configuration for order-service

Steps:

- Start the Config Server.
- Build the application:
- Run the service:

Open-api service :

http://localhost:8081/swagger-ui/index.html
http://localhost:8081/v3/api-docs

## Deutsch

## Order-service

`order-service` ist ein Backend-Microservice für eine fiktive E-Commerce-Plattform.
Seine Aufgabe ist es, Standardbestellungen für einen Online-Shop zu verwalten:
neue Bestellungen entgegenzunehmen, ihren Status bereitzustellen und als zentraler 
Einstiegspunkt für alle bestellbezogenen Vorgänge zu dienen.


## Business context and responsibilities

In einer typischen E-Commerce-Architektur würde der`order-service`:

- REST APIs bereitstellen, um:
  - eine neue Bestellung für einen Kunden anzulegen
  - Bestelldetails anhand der ID abzurufen
  - Bestellungen für einen bestimmten Kunden aufzulisten
  - den Bestellstatus zu aktualisieren (zum Beispiel CREATED, PAID, SHIPPED, CANCELLED)

- Mit anderen Microservices zusammenarbeiten, zum Beispiel:
  - payment-service (um Zahlungen zu prüfen und zu bestätigen)
  - inventory-service (um Lagerbestand zu reservieren und freizugeben)
  - notification-service (um E-Mails oder Nachrichten an den Kunden zu senden)

## Technologien

Java 21  
Spring Boot (Web, Actuator)  
Spring Cloud Config Client  
Lombok  
springdoc-openapi (Swagger UI)  
JUnit 5, Spring Boot Test


## Konfiguration

Dieser Service liest einen Teil seiner Konfiguration von einem Config Server.

Anwendungsname: order-service

```yaml

# Erwartete Eigenschaften auf dem Config Server (zum Beispiel in order-service.yml):
order-service:
  welcome-message: "Hello from Config Server"
  environment: "dev"
```
Client-Konfiguration in der application.yml des Dienstes:

```yaml

server:
  port: 8081

spring:
  application:
    name: order-service
  config:
    import: "optional:configserver:http://localhost:8888"

management:
  endpoints:
    web:
      exposure:
        include: health,info
  endpoint:
    health:
      show-details: always
```


## Beobachtbarkeit

### Strukturierte JSON-Logs
Der Service gibt strukturierte Logs im JSON-Format aus (Logback + logstash encoder).  
Jeder Log-Eintrag enthält gängige Felder (Zeitstempel, Level, Logger, Thread) sowie benutzerdefinierte Felder wie `app` und `env`.

### Request-Korrelation (X-Trace-Id)

Jede eingehende HTTP-Anfrage erhält eine Trace-ID:

- Wenn der Client `X-Trace-Id` sendet, übernimmt der Service diesen Wert.
- Wenn der Header fehlt, erzeugt der Service eine neue Trace-ID.
- Der Wert wird im Response-Header `X-Trace-Id` zurückgegeben.

Damit lassen sich Logs über Requests und Services hinweg korrelieren.

### Metriken (Actuator)

Metriken werden über Spring Boot Actuator bereitgestellt:

- `GET /actuator/metrics`
- `GET /actuator/metrics/http.server.requests`

Beispiel  (PowerShell):

```powershell
curl.exe -i http://localhost:8081/api/order/demo
curl.exe -i -H "X-Trace-Id: demo-123" http://localhost:8081/api/order/demo
```

## Lokal ausführen

Requirements:

- Java 21
- Maven or Gradle
- Ein Config Server läuft unter http://localhost:8888 und stellt die Konfiguration für order-service bereit.

Schritte:

- Den Config Server starten.
- Die Anwendung bauen
- Den Service starten

Open-api Service :

http://localhost:8081/swagger-ui/index.html
http://localhost:8081/v3/api-docs