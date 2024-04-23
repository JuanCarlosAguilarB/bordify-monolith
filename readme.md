# Bordify

### Description
Bordify is a monolithic Trello-style application designed to manage tasks and projects efficiently. It is built using Spring Boot and PostgreSQL, and offers Docker support, thus facilitating its deployment and scalability. This project serves as a basis for future refactoring and as an example of monolithic architecture in modern applications.

### Features
- User authentication with JWT
- Project and task management.
- CRUDs
- Projections and DTOs
- Spring Boot
- PostgreSQL database.
- Redis
- Swagger
- Docker and Docker Compose support
-  Monolith built with Spring Boot.

* Added CI/CD pipeline with GitHub Actions for automated testing and deployment. 👌

### Project Structure

- **bordify-api**: Spring Boot application that serves as the backend for the Bordify application.
```plaintext
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com.bordify/
    │   │       ├── models/
    │   │       ├── services/
    │   │       ├── repositories/
    │   │       ├── dtos/
    │   │       ├── config/
    │   │       ├── utils/
    │   │       ├── controllers/
    │   │       └── BordifyApplication.java
    │   └── resources/
    │       ├── static
    │       └── templates
    └── test/
        └── java/
            └── com.bordify/
                └── BordifyApplicationTest.java
```

### Installation

- **Local configuration without Docker**
1. Clone the repository:
```bash
git clone git@github.com:JuanCarlosAguilarB/bordify-monolith.git
```

2. Navigate to the project directory:
```bash
cd bordify-monolith
```
3. Set the required environment variables for the database and any other necessary settings in`src/main/resources/application.properties.`
4. Run the application:

```bash
./gradlew bootRun
```
* **Using Docker Compose**

1. Ensure that Docker and Docker Compose are installed on your machine.
2. Execute the following command to build the Docker images and run the containers:
```bash
docker-compose up
```
* **Access the application at `http://localhost:8080`.**






















