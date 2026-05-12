# Lyftix Build Log

## Project Goal

Lyftix is a self-hosted personal analytics platform.

It will track coding activity, workouts, daily check-ins, GitHub activity, and server/system metrics.

The goal is to build a real long-running platform that runs on a home server and exposes useful dashboards and insights.

---

## Tech Stack

- Spring Boot — backend API
- PostgreSQL — database
- Docker — containerized infrastructure
- Python — background workers later
- Prometheus — system metrics later
- Grafana — dashboards later
- Tailscale — private server access later
- HP laptop — home server later
- Mac — development machine

---

## Development Setup

### Development machine

MacBook

Used for:

- coding
- testing
- Git commits
- running local Docker containers

### Server machine

HP laptop

Will be used later as the home server.

Used for:

- running Lyftix continuously
- hosting Docker containers
- accessing dashboards privately

---

## Step 1 — Created Project Repository

Created the main project folder:

```bash
mkdir lyftix
cd lyftix
git init

This created one Git repository for the whole platform.

Project structure:

lyftix/
├── backend/
├── infrastructure/
├── workers/
└── README.md

Important decision:

We are using one repo, not separate repos.

This is a monorepo-style setup where backend, workers, and infrastructure live together.

Step 2 — Created Base Folders

Created:

mkdir infrastructure
mkdir workers
touch README.md
touch infrastructure/.gitkeep
touch workers/.gitkeep

The backend folder was not manually created. It was generated later by Spring Initializr.

Step 3 — Installed Docker Desktop

Docker Desktop is required on Mac before Docker commands work.

Verified Docker with:

docker --version
docker compose version

Important note:

Docker Desktop must be open/running when using Docker commands.

Step 4 — Added PostgreSQL with Docker Compose

Created:

infrastructure/docker-compose.yml

Added PostgreSQL service:

services:
  postgres:
    image: postgres:16
    container_name: lyftix-postgres
    environment:
      POSTGRES_DB: lyftix
      POSTGRES_USER: lyftix_user
      POSTGRES_PASSWORD: lyftix_password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:

Started PostgreSQL from inside the infrastructure folder:

cd ~/lyftix/infrastructure
docker compose up -d

Checked running containers:

docker ps

Important lesson:

docker compose up -d must be run in the folder where docker-compose.yml exists.

Step 5 — Created Spring Boot Backend

Generated backend using Spring Initializr.

Settings:

Group: com.lyftix
Artifact: backend
Package: com.lyftix.backend
Java: 21
Build: Maven

Dependencies:

Spring Web
Spring Data JPA
PostgreSQL Driver
Validation
Spring Boot Actuator

Moved generated backend folder into:

~/lyftix/backend

Final structure:

lyftix/
├── backend/
│   ├── pom.xml
│   ├── mvnw
│   ├── src/
│   └── ...
├── infrastructure/
├── workers/
└── README.md
Step 6 — Opened Project Correctly

Opened the whole project in VS Code:

cd ~/lyftix
code .

Opened the whole lyftix folder in IntelliJ.

Important lesson:

Do not open only backend if you want IntelliJ to show the full monorepo structure.

Step 7 — Fixed Java Version

Set IntelliJ Project SDK to:

Java 21

Not Java 24.

Reason:

Java 21 is LTS and more stable for Spring Boot development.

Step 8 — Loaded Maven Project

In IntelliJ, Maven dependencies were red at first.

Fixed by:

opening backend/pom.xml
adding/loading it as a Maven project
reloading Maven dependencies

After Maven loaded correctly, Spring Boot imports turned green.

Step 9 — Connected Spring Boot to PostgreSQL

Updated:

backend/src/main/resources/application.properties

With:

spring.application.name=backend

spring.datasource.url=jdbc:postgresql://localhost:5432/lyftix
spring.datasource.username=lyftix_user
spring.datasource.password=lyftix_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

Important lesson:

Spring Boot needs database connection settings when JPA/PostgreSQL dependencies are installed.

Step 10 — Added Health Check Endpoint

Created package:

com.lyftix.backend.controller

Created:

HealthController.java

Code:

package com.lyftix.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public String healthCheck() {
        return "Lyftix API is running";
    }
}

Tested in browser:

http://localhost:8080/api/health

Expected response:

Lyftix API is running
Step 11 — Renamed Project from Lifytix to Lyftix

Renamed project folder to:

lyftix

Renamed Java package from:

com.lifytix.backend

to:

com.lyftix.backend

Used IntelliJ refactor rename for Java package.

Updated GitHub remote:

git remote set-url origin https://github.com/DSonOfSolomon/lyftix.git

Verified with:

git remote -v
Step 12 — Git Rules

Created .gitignore at root.

Important files to ignore:

.env
.idea/
.vscode/
target/
.DS_Store

Important lesson:

Do not commit .env files because they may contain secrets.

If .env was already staged:

git rm --cached infrastructure/.env


Current Status

Working so far:

Lyftix repo created
Spring Boot backend created
PostgreSQL running in Docker
backend connects to PostgreSQL
health endpoint works
Git remote renamed to Lyftix

Next feature:

Create the first real database-backed feature:

WorkoutMetric

Commit this documentation:

```bash
git add .
git commit -m "docs: add initial Lyftix build log"