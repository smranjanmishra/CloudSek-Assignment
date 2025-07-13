# Post-Comments Service

A robust RESTful API service built with Spring Boot that allows users to create posts and comment on them. This project demonstrates modern Java development practices with a clean layered architecture.

## 🚀 Features

### Core Functionality
- ✅ **Create Posts** - Users can create text-based posts with title, content, and author
- ✅ **Commenting System** - Users can comment on any post
- ✅ **Multiple Comments** - Each post supports unlimited comments
- ✅ **Rich Text Support** - Comments support bold, italic, hyperlinks, and line breaks

### Advanced Features
- 🔍 **Search & Filter** - Search posts by title or author
- 📄 **Pagination** - Efficient data loading with pagination support
- 🎯 **CRUD Operations** - Complete Create, Read, Update, Delete operations
- ⚡ **RESTful APIs** - Well-designed REST endpoints
- 🛡️ **Input Validation** - Comprehensive data validation
- 📊 **Audit Trail** - Automatic tracking of creation and update times

## 🛠️ Technology Stack

- **Framework:** Spring Boot 3.5.3
- **Language:** Java 21
- **Database:** MySQL 8.0
- **ORM:** Spring Data JPA with Hibernate
- **Build Tool:** Maven
- **Validation:** Jakarta Validation
- **Documentation:** RESTful API endpoints

## 📋 Prerequisites

- Java 21 or higher
- MySQL 8.0 or higher
- Maven 3.6+

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/Post-Comments-Services.git
cd Post-Comments-Services
```

### 2. Database Setup
Create a MySQL database named `CloudDB`:
```sql
CREATE DATABASE CloudDB;
```

### 3. Configure Database
Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

### Posts API

#### Create Post
```http
POST /api/posts
Content-Type: application/json

{
  "title": "My First Post",
  "content": "This is the content of my post",
  "author": "John Doe"
}
```

#### Get All Posts
```http
GET /api/posts
```

#### Get Post by ID
```http
GET /api/posts/{id}
```

#### Get Post with Comments
```http
GET /api/posts/{id}/with-comments
```

#### Update Post
```http
PUT /api/posts/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "content": "Updated content",
  "author": "John Doe"
}
```

#### Delete Post
```http
DELETE /api/posts/{id}
```

#### Search Posts
```http
GET /api/posts/search/author?author=John
GET /api/posts/search/title?keyword=first
```

#### Paginated Posts
```http
GET /api/posts/paginated?page=0&size=10&sortBy=createdAt&sortDir=desc
```

### Comments API

#### Create Comment
```http
POST /api/comments/post/{postId}
Content-Type: application/json

{
  "content": "This is a **bold** comment with *italic* text and a [link](https://example.com)",
  "author": "Jane Smith"
}
```

#### Get Comments by Post
```http
GET /api/comments/post/{postId}
```

#### Update Comment
```http
PUT /api/comments/{id}
Content-Type: application/json

{
  "content": "Updated comment content",
  "author": "Jane Smith"
}
```

#### Delete Comment
```http
DELETE /api/comments/{id}
```

#### Search Comments
```http
GET /api/comments/search/author?author=Jane
```

#### Paginated Comments
```http
GET /api/comments/post/{postId}/paginated?page=0&size=10
```

#### Comment Count
```http
GET /api/comments/post/{postId}/count
```

## 🎨 Rich Text Support

The application supports rich text formatting in comments:

- **Bold:** `**text**` → `<strong>text</strong>`
- **Italic:** `*text*` → `<em>text</em>`
- **Links:** `[text](url)` → `<a href="url">text</a>`
- **Line Breaks:** `\n` → `<br>`

### Example Rich Text Comment
```json
{
  "content": "This is **bold** and *italic* text with a [link](https://example.com)\nNew line here",
  "author": "User"
}
```

## 🏗️ Architecture Overview

### Layered Architecture
```
┌─────────────────┐
│   Controller    │ ← REST API endpoints
├─────────────────┤
│    Service      │ ← Business logic
├─────────────────┤
│   Repository    │ ← Data access
├─────────────────┤
│    Entity       │ ← Database models
└─────────────────┘
```

### Key Components

#### Entity Layer
- **Post.java** - Database entity for posts with one-to-many relationship to comments
- **Comment.java** - Database entity for comments with many-to-one relationship to posts

#### DTO Layer
- **PostDTO.java** - Data transfer object for posts
- **CommentDTO.java** - Data transfer object for comments
- **CreatePostRequest.java** - Request validation for post creation
- **CreateCommentRequest.java** - Request validation for comment creation

#### Repository Layer
- **PostRepository.java** - Database operations for posts with custom queries
- **CommentRepository.java** - Database operations for comments with custom queries

#### Service Layer
- **PostService.java** - Business logic interface for posts
- **PostServiceImpl.java** - Implementation of post business logic
- **CommentService.java** - Business logic interface for comments
- **CommentServiceImpl.java** - Implementation of comment business logic with rich text conversion

#### Controller Layer
- **PostController.java** - REST endpoints for post operations
- **CommentController.java** - REST endpoints for comment operations

#### Exception Handling
- **GlobalExceptionHandler.java** - Centralized exception handling
- **PostNotFoundException.java** - Custom exception for missing posts
- **CommentNotFoundException.java** - Custom exception for missing comments

#### Configuration
- **DatabaseConfig.java** - JPA and transaction configuration
- **RichTextUtil.java** - Utility for rich text conversion

## 🗄️ Database Design

### Posts Table
```sql
CREATE TABLE posts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME
);
```

### Comments Table
```sql
CREATE TABLE comments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    rich_content TEXT,
    author VARCHAR(50) NOT NULL,
    post_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    FOREIGN KEY (post_id) REFERENCES posts(id)
);
```

## 🔧 Configuration

### Application Properties
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/CloudDB
spring.datasource.username=root
spring.datasource.password=admin

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Jackson Configuration
spring.jackson.serialization.write-dates-as-timestamps=false
spring.jackson.time-zone=UTC

# Pagination Configuration
spring.data.web.pageable.default-page-size=10
spring.data.web.pageable.max-page-size=100
```

## 🧪 Testing

Run the application tests:
```bash
mvn test
```

## 📦 Dependencies

### Core Dependencies
- **spring-boot-starter-web** - Web application support
- **spring-boot-starter-data-jpa** - JPA and Hibernate
- **spring-boot-starter-validation** - Input validation
- **mysql-connector-j** - MySQL database driver
- **lombok** - Reduces boilerplate code

### Development Dependencies
- **spring-boot-devtools** - Development convenience
- **spring-boot-starter-test** - Testing support

## 🚀 Deployment

### Build JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/Post-Comments-Services-0.0.1-SNAPSHOT.jar
```

## 📝 Code Quality Features

- ✅ **Clean Architecture** - Layered design with clear separation of concerns
- ✅ **RESTful Design** - Proper HTTP methods and status codes
- ✅ **Input Validation** - Comprehensive data validation with custom error messages
- ✅ **Exception Handling** - Centralized error handling with proper HTTP responses
- ✅ **Audit Trail** - Automatic tracking of creation and modification times
- ✅ **Pagination** - Efficient data loading for large datasets
- ✅ **Search Functionality** - Flexible search by title and author
- ✅ **Rich Text Support** - Advanced comment formatting capabilities

## 🎯 Assignment Requirements Coverage

| Requirement | Implementation |
|-------------|----------------|
| Create Posts | Full CRUD operations with validation |
| Commenting System  | Rich text support with formatting |
| Multiple Comments  One-to-many relationship |
| Text-based Comments  | Plain text and rich text support |
| Rich Text Support  | Bold, italic, links, line breaks |
| Language/Framework  | Spring Boot 3.5.3 with Java 21 |
| Data Storage  | MySQL with JPA/Hibernate |
| RESTful APIs  | Comprehensive API design |
| Code Quality  | Clean, documented, well-structured |
| Documentation  | This README file |
