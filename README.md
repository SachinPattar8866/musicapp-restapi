# MusicApp REST API

This is the backend REST API for MusicApp, a music streaming platform. Built with Java and Spring Boot, it provides endpoints for user authentication, music management, playlists, history tracking, and more.

## Features

* User registration and authentication (JWT-based)
* Music CRUD operations
* Playlist creation and management
* Like and history tracking
* Secure REST endpoints
* Docker support for easy deployment

---

## Technologies Used

* Java 17+
* Spring Boot
* Spring Security
* JPA/Hibernate
* MySQL/PostgreSQL (configurable)
* Docker
* Maven

---

## Getting Started

### Prerequisites

* Java 17 or higher
* Maven
* MySQL or PostgreSQL database

### Setup

1.  Clone the repository:

    ```bash
    git clone [https://github.com/](https://github.com/)<your-username>/musicapp-restapi.git
    cd musicapp-restapi
    ```

2.  Configure your database in `src/main/resources/application.properties`.

3.  Build and run the application:

    ```bash
    ./mvnw spring-boot:run
    ```

The API will be available at `http://localhost:8080`.

---

## Docker

To run the application using Docker:

```bash
docker build -t musicapp-restapi .
docker run -p 8080:8080 musicapp-restapi

## clear setup
 ``` download or clone it
 ``` remove the docker file it's needed while deploying
 ``` create mongodb database edit the application properties remove the last three lines presented in that
 ``` create a  jamendo account go to the setting you get the id of your acc paste it in your application.properties
 ``` run the youtubemusicBackendApplication file use intellij idea or vs code
 ``` check the api's in postman


API Documentation
Swagger/OpenAPI documentation is available at /swagger-ui.html (if enabled).

Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

License
This project is licensed under the MIT License. 
