## 🌍 Languages
- [English](#English)
- [Deutsch](#Deutsch)


## English

## Order-service

`order-service` is a backend microservice for a fictional e-commerce platform. Its responsibility is to manage the lifecycle of confirmed customer orders after 
checkout: storing newly confirmed orders, exposing their status, and acting as a stable backend API for other services and backoffice tools.

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
    - checkout-service (to receive confirmed orders via HTTP once the cart is checked out)
  

## Technologies

Java 21  
Spring Boot (Web, Actuator)  
Spring Cloud Config Client  
Lombok  
springdoc-openapi (Swagger UI)  
JUnit 5, Spring Boot Test

## Business 

The lifecycle of an order is modelled via the `OrderStatus` enum.  
For this service, the initial set of states is:

- `CONFIRMED` :: The order has been accepted after a successful checkout
- `PAID` :: The payment has been fully settled or captured
- `SHIPPED` :: The order has been handed over to the carrier
- `CANCELLED` :: The order has been cancelled before shipping

More states (for example `DELIVERED`, `RETURNED`) can be added later if the business requirements evolve.

#### Order factory method

Instead of constructing orders directly via a public constructor, the domain exposes a factory method:

```
Order order = Order.createNew(customerId, items);
```
This factory method centralises the rules for creating a new order:
- generates a new UUID for the order id
- sets the initial OrderStatus (for example CONFIRMED)
- calculates totalAmount from the list of OrderItems
- sets createdAt to the current time

By keeping this logic inside the Order class, the service ensures that every 
newly created order respects the same invariants, and the application code 
using the domain remains simple and expressive.

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

## Local CI pipeline(emulated)
This project includes a simple local pipeline to emulate a CI/CD setup (build, test, and run with different profiles) without needing Jenkins or Azure DevOps.

The scripts are located in the `ci/` folder and are intended to be run from the project root.
Example commands (PowerShell on Windows):

Build only:

```powershell
.\ci\build.ps1
```
Run all pipeline steps (build + test):

```
.\ci\pipeline.ps1
```
Run the service with the dev profile:
```
.\ci\run-dev.ps1
```

Run the service with the prod profile:
```
.\ci\run-prod.ps1
```
The scripts use the environment variable SPRING_PROFILES_ACTIVE to select the active profile (for example dev or prod). This simulates how different environments would be configured in a real CI/CD pipeline.

## Deutsch

## Order-service

`order-service` ist ein Backend-Microservice für eine fiktive E-Commerce-Plattform.
Seine Aufgabe ist es, den Leebenszyklus bestätigter Kundenbestellungen nach dem Checkout zu
verwalten: neu bestätigte Bestellungen zu speichern, ihre Status bereitzustellen und als
stabile Backend-API für andere Services und Backoffice-Toos zu dienen.


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
  - chekcout-service (um bestätigte Bestellungen per HTTP zu erhalten, sobald der Warenkorb ausgechekt wurde)

## Technologien

Java 21  
Spring Boot (Web, Actuator)  
Spring Cloud Config Client  
Lombok  
springdoc-openapi (Swagger UI)  
JUnit 5, Spring Boot Test

## Geschäft

Der Lebenszyklus einer Bestellung wird über das Enum `OrderStatus` modelliert.  
Für diesen Service ist der initiale Satz von Zuständen:

- `CONFIRMED` :: die Bestellung wurde nach einem erfolgreichen Checkout angenommen
- `PAID` :: die Zahlung wurde vollständig abgewickelt bzw. belastet
- `SHIPPED` :: die Bestellung wurde an den Transportdienst übergeben
- `CANCELLED` :: die Bestellung wurde vor dem Versand storniert

Weitere Zustände (zum Beispiel `DELIVERED`, `RETURNED`) können später ergänzt werden, wenn sich die Geschäftsanforderungen weiterentwickeln.

#### Factory-Methode in Order

Anstatt Bestellungen direkt über einen öffentlichen Konstruktor zu erzeugen, stellt die Domäne eine Factory-Methode bereit:

```
Order order = Order.createNew(customerId, items);
```
Diese Factory-Methode bündelt die Regeln für das Anlegen einer neuen Bestellung:
- Erzeugt eine neue UUID für die Bestell-ID
- Setzt den initialen OrderStatus (zum Beispiel CONFIRMED)
- Berechnet totalAmount aus der Liste der OrderItems 
- Setzt createdAt auf den aktuellen Zeitpunkt

Durch diese Logik innerhalb der Klasse Order wird sichergestellt, 
dass jede neu erzeugte Bestellung die gleichen Invarianten erfüllt und der 
Anwendungscode, der die Domäne verwendet, einfach und aussagekräftig bleibt.


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

Beispiel (PowerShell):

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

## Lokale CI-Pipeline (simuliert)

Dieses Projekt enthält eine einfache lokale Pipeline, um ein CI/CD-Setup zu simulieren (Build, Tests und Start mit unterschiedlichen Profilen), ohne Jenkins oder Azure DevOps zu benötigen.

Die Skripte befinden sich im Ordner `ci/` und sollen aus dem Projektwurzelverzeichnis ausgeführt werden.

Beispielbefehle (PowerShell unter Windows):

Nur Build:

```
.\ci\build.ps1
```
Komplette Pipeline ausführen (Build + Tests):
```
.\ci\pipeline.ps1
```
Service mit dem Profil dev starten:
```
.\ci\run-dev.ps1
```

Service mit dem Profil prod starten:
```
.\ci\run-prod.ps1
```

Die Skripte verwenden die Umgebungsvariable SPRING_PROFILES_ACTIVE, um das aktive Profil auszuwählen (z. B. dev oder prod). Dadurch wird simuliert, wie unterschiedliche Umgebungen in einer realen CI/CD-Pipeline konfiguriert würden.
