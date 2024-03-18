# Cook's Corner

Cook's Corner is a web application designed for food enthusiasts to explore, create, and share their favorite recipes. This platform allows users to manage recipes, ingredients, and user profiles, enhancing the cooking experience with a community-driven approach.

## Features

- **User Authentication**: Secure login and registration system with JWT for handling user sessions.
- **Recipe Management**: Users can add, update, and delete recipes. Each recipe includes details such as ingredients, preparation steps, and images.
- **Search Functionality**: Efficient search feature to find recipes by name, category, or ingredients.
- **User Profiles**: Users can create and edit their profiles, follow other users, and save their favorite recipes.

## Technologies

- **Spring Boot**: For creating the RESTful backend services.
- **PostgreSQL**: As the relational database for storing user and recipe data.
- **Spring Security**: For handling authentication and authorization.
- **Docker**: For containerizing the application and its environment.
- **Swagger**: For API documentation.

## Getting Started

### Prerequisites

- JDK 11 or later
- Maven
- Docker
- Docker Compose

### Setup and Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/utkoleg/cookscorner.git
   cd cookscorner
   ```

2. **Start the PostgreSQL database**

   Use Docker Compose to start a PostgreSQL container.

   ```bash
   docker-compose up -d
   ```

3. **Configure application**

   Update the `src/main/resources/application.properties` file with your database and email settings.

4. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

   The application will be accessible at `http://localhost:8080`.

## Accessing the API Documentation

Once the application is running, you can access the Swagger UI to test the API endpoints at `http://localhost:8080/swagger-ui.html`.

## Contributing

We welcome contributions from the community. If you'd like to contribute, please fork the repository and use a feature branch. Pull requests are warmly welcome.

## Contact

Your Name - Mun_2015@bk.ru

Project Link: [https://github.com/utkoleg/cookscorner](https://github.com/utkoleg/cookscorner)
```
