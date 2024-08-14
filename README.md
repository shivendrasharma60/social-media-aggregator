# Social Media Aggregator

This project is a social media aggregator that consolidates feeds from various social media platforms. The application is built using Spring Boot and provides APIs to manage users, influencers, and social media feeds. It also features Swagger documentation for easy API exploration.

## Prerequisites

- **Java 17**: Make sure you have Java 17 installed on your system.
- **Maven 3.8+**: Ensure you have Maven installed for dependency management and building the project.

## Project Structure

- **Java Version**: 17
- **Spring Boot Version**: 3.3.2
- **Dependencies**:
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - H2 Database
  - Lombok
  - RestFB (for Facebook API integration)
  - Twitter4j (for Twitter API integration)
  - Springdoc OpenAPI (for Swagger documentation)
  - Mockito (for unit testing)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-username/social-media-aggregator.git
cd social-media-aggregator


### Build the project
mvn clean install

### Run the application
mvn spring-boot:run

#Swagger URL
http://localhost:8080/myapp/swagger-ui.html

#H2 Database URL
http://localhost:8080/myapp/h2-console

### Default Credentials:
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: password
