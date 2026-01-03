# Movie Management System

A Spring Boot REST API for managing movies with full CRUD operations, built using Java 17, Maven, Spring Web, Spring Data JPA, and H2 database.

## Features

- ✅ Complete CRUD operations for movies
- ✅ Search movies by genre, director, release year, and rating
- ✅ Input validation and exception handling
- ✅ H2 in-memory database for development
- ✅ Comprehensive unit tests with JUnit 5 and Mockito
- ✅ Clean architecture with layered structure
- ✅ RESTful API design following best practices

## Technology Stack

- **Java**: 17
- **Build Tool**: Maven
- **Framework**: Spring Boot 3.2.0
- **Web**: Spring Web
- **Data**: Spring Data JPA
- **Database**: H2 (In-Memory)
- **Testing**: JUnit 5, Mockito
- **Validation**: Jakarta Validation

## Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use Maven Wrapper)
- IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

## Project Structure

```
src/
├── main/
│   ├── java/com/mms/
│   │   ├── MovieManagementSystemApplication.java
│   │   ├── controller/
│   │   │   └── MovieController.java
│   │   ├── entity/
│   │   │   └── Movie.java
│   │   ├── exception/
│   │   │   ├── MovieNotFoundException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── repository/
│   │   │   └── MovieRepository.java
│   │   └── service/
│   │       └── MovieService.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/mms/
        └── service/
            └── MovieServiceTest.java
```

## Installation & Setup

1. **Clone or download the project**

2. **Navigate to the project directory**
   ```bash
   cd MMS
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   Or run the main class `MovieManagementSystemApplication` from your IDE.

5. **Verify the application is running**
   - The application will start on `http://localhost:8080`
   - Check the console for "Started MovieManagementSystemApplication"

## API Endpoints

Base URL: `http://localhost:8080/api/movies`

### Get All Movies
- **GET** `/api/movies`
- **Description**: Retrieve all movies
- **Response**: `200 OK` - List of movies

### Get Movie by ID
- **GET** `/api/movies/{id}`
- **Description**: Retrieve a movie by its ID
- **Path Parameters**: 
  - `id` (Long) - Movie ID
- **Response**: 
  - `200 OK` - Movie object
  - `404 Not Found` - Movie not found

### Create Movie
- **POST** `/api/movies`
- **Description**: Create a new movie
- **Request Body** (JSON):
  ```json
  {
    "title": "The Matrix",
    "genre": "Sci-Fi",
    "director": "Wachowski Brothers",
    "releaseYear": 1999,
    "rating": 8.7
  }
  ```
- **Response**: 
  - `201 Created` - Created movie object
  - `400 Bad Request` - Validation errors

### Update Movie
- **PUT** `/api/movies/{id}`
- **Description**: Update an existing movie
- **Path Parameters**: 
  - `id` (Long) - Movie ID
- **Request Body** (JSON): Same as Create Movie (all fields optional)
- **Response**: 
  - `200 OK` - Updated movie object
  - `404 Not Found` - Movie not found
  - `400 Bad Request` - Validation errors

### Delete Movie
- **DELETE** `/api/movies/{id}`
- **Description**: Delete a movie by ID
- **Path Parameters**: 
  - `id` (Long) - Movie ID
- **Response**: 
  - `204 No Content` - Successfully deleted
  - `404 Not Found` - Movie not found

### Get Movies by Genre
- **GET** `/api/movies/genre/{genre}`
- **Description**: Retrieve all movies of a specific genre
- **Path Parameters**: 
  - `genre` (String) - Movie genre
- **Response**: `200 OK` - List of movies

### Get Movies by Director
- **GET** `/api/movies/director/{director}`
- **Description**: Retrieve all movies by a specific director
- **Path Parameters**: 
  - `director` (String) - Director name
- **Response**: `200 OK` - List of movies

### Get Movies by Release Year
- **GET** `/api/movies/year/{releaseYear}`
- **Description**: Retrieve all movies released in a specific year
- **Path Parameters**: 
  - `releaseYear` (Integer) - Release year
- **Response**: `200 OK` - List of movies

### Get Movies by Minimum Rating
- **GET** `/api/movies/rating/{rating}`
- **Description**: Retrieve all movies with rating greater than or equal to specified value
- **Path Parameters**: 
  - `rating` (Double) - Minimum rating (0.0 - 10.0)
- **Response**: `200 OK` - List of movies

## Example API Requests

### Create a Movie
```bash
curl -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Inception",
    "genre": "Sci-Fi",
    "director": "Christopher Nolan",
    "releaseYear": 2010,
    "rating": 8.8
  }'
```

### Get All Movies
```bash
curl -X GET http://localhost:8080/api/movies
```

### Get Movie by ID
```bash
curl -X GET http://localhost:8080/api/movies/1
```

### Update a Movie
```bash
curl -X PUT http://localhost:8080/api/movies/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Matrix Reloaded",
    "rating": 7.2
  }'
```

### Delete a Movie
```bash
curl -X DELETE http://localhost:8080/api/movies/1
```

### Get Movies by Genre
```bash
curl -X GET http://localhost:8080/api/movies/genre/Sci-Fi
```

## Movie Entity Schema

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | Auto-generated | Primary key |
| title | String | Required, 1-200 chars | Movie title |
| genre | String | Required, 1-100 chars | Movie genre |
| director | String | Required, 1-100 chars | Director name |
| releaseYear | Integer | Required, 1888-2100 | Release year |
| rating | Double | Optional, 0.0-10.0 | Movie rating |

## Database Access

### H2 Console

The H2 database console is enabled for development purposes.

1. **Access the console**: `http://localhost:8080/h2-console`
2. **JDBC URL**: `jdbc:h2:mem:moviedb`
3. **Username**: `sa`
4. **Password**: (leave empty)

## Testing

### Run Unit Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=MovieServiceTest
```

### Test Coverage

The project includes comprehensive unit tests for the `MovieService` class covering:
- ✅ Get all movies
- ✅ Get movie by ID (success and not found cases)
- ✅ Create movie (success and null validation)
- ✅ Update movie (success, not found, and null validation)
- ✅ Delete movie (success and not found cases)
- ✅ Search by genre, director, release year, and rating

## Error Handling

The API includes comprehensive error handling:

- **404 Not Found**: When a movie with the specified ID doesn't exist
- **400 Bad Request**: When validation fails (missing required fields, invalid values)
- **500 Internal Server Error**: For unexpected server errors

### Example Error Response
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Movie Not Found",
  "message": "Movie not found with id: 999"
}
```

## Validation Rules

- **Title**: Required, 1-200 characters
- **Genre**: Required, 1-100 characters
- **Director**: Required, 1-100 characters
- **Release Year**: Required, between 1888 and 2100
- **Rating**: Optional, between 0.0 and 10.0

## CI/CD with Jenkins

The project includes a Jenkinsfile for automated CI/CD pipeline.

### Jenkins Pipeline Stages

1. **Checkout** - Checks out the source code from SCM
2. **Build** - Compiles the application using Maven
3. **Test** - Runs unit tests and publishes test results
4. **Package** - Creates the JAR artifact
5. **Docker Build** - Builds Docker image with version and latest tags
6. **Docker Push** - Pushes images to Docker registry (only for main/master/develop branches)

### Setup Jenkins

1. **Install Required Plugins:**
   - Pipeline
   - Docker Pipeline
   - JUnit

2. **Configure Credentials:**
   - Add Docker registry credentials in Jenkins:
     - Go to Jenkins → Manage Jenkins → Credentials
     - Add credentials with ID: `docker-registry-credentials`
     - Add Docker registry URL as secret text with ID: `docker-registry-url`

3. **Create Pipeline Job:**
   - Create a new Pipeline job in Jenkins
   - Point it to your Git repository
   - Jenkins will automatically detect and use the `Jenkinsfile`

### Environment Variables

Update these in the Jenkinsfile or Jenkins job configuration:
- `DOCKER_REGISTRY`: Your Docker registry URL (e.g., `docker.io`, `registry.example.com`)
- `DOCKER_CREDENTIALS_ID`: Jenkins credentials ID for Docker registry login

### Simplified Version

For local testing or simpler setups, use `Jenkinsfile.simple` which doesn't require credential configuration.

## Development Notes

- The application uses an in-memory H2 database, so data is lost when the application restarts
- For production use, configure a persistent database (PostgreSQL, MySQL, etc.) in `application.properties`
- The API follows RESTful conventions with proper HTTP methods and status codes
- All service methods are transactional
- Exception handling is centralized in `GlobalExceptionHandler`

## License

This project is open source and available for educational purposes.

## Author

Movie Management System - Spring Boot REST API

