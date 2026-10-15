# Expense Tracker API

A Spring Boot REST API for managing expenses, built to practise Maven, PostgreSQL, Docker, GitHub, and Jenkins CI/CD.

## Technology stack

- Java 21 and Spring Boot
- Maven
- PostgreSQL
- Docker and Docker Compose
- Jenkins Pipeline
- Spring Boot Actuator

## API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/expenses` | List expenses |
| GET | `/api/expenses?category=Food` | Filter by category |
| GET | `/api/expenses/{id}` | Get one expense |
| POST | `/api/expenses` | Create an expense |
| PUT | `/api/expenses/{id}` | Update an expense |
| DELETE | `/api/expenses/{id}` | Delete an expense |
| GET | `/api/expenses/summary/monthly?year=2026&month=10` | Monthly total |
| GET | `/actuator/health` | Application health |

## Run tests

```bash
mvn clean verify
```

## Run with Docker Compose

Set a strong local development password in your shell. Replace the example value with your own password; do not commit it to Git.

```bash
export DB_PASSWORD='replace-with-a-strong-password'
```

Build the Docker image:

```bash
docker build -t expense-tracker-api:local .
```

Start the application and database:

```bash
docker compose up -d
docker compose ps
```

Check application health:

```bash
curl http://localhost:8081/actuator/health
```

Stop the containers:

```bash
docker compose down
```

The PostgreSQL database uses a named Docker volume so its data persists when containers are removed. Removing that volume deletes the database data.

## CI/CD

The Jenkins pipeline checks out the source code, runs Maven verification and tests, builds and verifies a versioned Docker image, and deploys the application using Docker Compose.

Jenkins obtains the database password from a stored credential. The API and PostgreSQL run as Compose services.
