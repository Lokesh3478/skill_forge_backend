# <h1 align="center">🧠 SkillForge Backend ⚙️</h1>

<p align="center">AI-powered adaptive learning & personalized skill development platform</p> <p align="center"> <img src="https://img.shields.io/badge/Java-17-orange" /> <img src="https://img.shields.io/badge/Spring%20Boot-3-brightgreen" /> <img src="https://img.shields.io/badge/Security-JWT-yellow" /> <img src="https://img.shields.io/badge/Database-MySQL-blue" /> <img src="https://img.shields.io/badge/Build-Maven-red" /> <img src="https://img.shields.io/badge/ORM-Hibernate-purple" /> </p>

----------

# 📚 **Table of Contents**

-   About

-   Tech Stack

-   Architecture

-   Project Setup

-   Authentication API

-   Authorization Rules


----------

# 🧩 **About**

SkillForge is a backend system powering a smart adaptive learning platform, featuring:

-   🔐 JWT Authentication

-   🛡 Role-based access control

-   👤 User CRUD operations

-   📘 Course & Assessment modules (future)

-   ⚡ REST APIs for React frontend


----------

<h2 align="center">🚀 Tech Stack</h2>

<table>
  <tr>
    <th>Layer</th>
    <th>Technology</th>
  </tr>
  <tr>
    <td>Language</td>
    <td>Java 17+</td>
  </tr>
  <tr>
    <td>Framework</td>
    <td>Spring Boot 3</td>
  </tr>
  <tr>
    <td>Security</td>
    <td>Spring Security + JWT</td>
  </tr>
  <tr>
    <td>Database</td>
    <td>MySQL</td>
  </tr>
  <tr>
    <td>Build Tool</td>
    <td>Maven</td>
  </tr>
  <tr>
    <td>ORM</td>
    <td>Hibernate / JPA</td>
  </tr>
  <tr>
    <td>Tools</td>
    <td>Lombok</td>
  </tr>
</table>

----------

# 🏗 **Architecture**

`flowchart TD
    A[Frontend - React.js] --> B[Backend - Spring Boot]
    B --> C[(MySQL Database)]
    B --> D[JWT Security Layer]
    D --> E[Role Based Access]`

----------

# 📦 **Project Setup**

<details> <summary><strong>1️⃣ Clone Repository</strong></summary>

`git clone https://github.com/Lokesh3478/skill_forge_backend.git cd skill_forge_backend`

</details>

----------

<details> <summary><strong>2️⃣ Configure Database</strong></summary>

Create database:

`CREATE DATABASE skillforge;`

Update `application.properties`:

`spring.datasource.url=jdbc:mysql://localhost:3306/skillforge
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true`

</details>

----------

<details> <summary><strong>3️⃣ Dependencies (Auto-installed by Maven)</strong></summary>

-   Spring Web

-   Spring Security

-   Spring Data JPA

-   Lombok

-   MySQL Driver

-   JWT (io.jsonwebtoken)


</details>

----------

<details> <summary><strong>4️⃣ Run the Application</strong></summary>

`mvn spring-boot:run`

Server runs at:

👉 **[http://localhost:8080/](http://localhost:8080/)**

</details>

----------

# 🔐 **Authentication Module**

The backend uses **JWT-based authentication**.

----------

## <details><summary><strong>🔑 1. Register User</strong></summary>

### **Endpoint**

`POST /auth/register`

### **Request Body**

`{  "id":  "U001",  "fullName":  "Sample User",  "email":  "sample@example.com",  "password":  "StrongPassword123",  "phone":  "9876543210",  "role":  "USER"  }`

### **AuthResponse**

`{  "token":  "jwt-token-here",  "userId":  "U001",  "fullName":  "Sample User",  "email":  "sample@example.com",  "role":  "USER"  }`

</details>

----------

## <details><summary><strong>🔑 2. Login User</strong></summary>

### **Endpoint**

`POST /auth/login`

### **Request Body**

`{  "email":  "sample@example.com",  "password":  "StrongPassword123"  }`

### **Response**

`{  "token":  "jwt-token-here",  "userId":  "U001",  "fullName":  "Sample User",  "email":  "sample@example.com",  "role":  "USER"  }`

</details>

----------

# 🛡 **Authorization**

All protected endpoints require:

`Authorization: Bearer <JWT_TOKEN>`

### **Available Roles**

-   USER

-   ADMIN

-   INSTRUCTOR


Access is enforced using `UserRole` enum and Spring Security filters.