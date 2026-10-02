# SkillSwap

A Java-based skill exchange platform where users can offer their skills, learn new skills, discover other users, send skill requests, manage skill exchanges, and give reviews.

## Features

* User Registration and Login
* User Profile Management
* Add and Remove Skills
* Offer or Learn a Skill
* Search Users by Skill
* Send Skill Exchange Requests
* Accept or Reject Requests
* Track Skill Exchanges
* Mark Exchanges as Completed
* Add Reviews and Ratings
* View Reviews Received
* View Reviews Given
* MySQL Database Integration
* Secure Database Password Handling using Environment Variables

## Technologies Used

* Java
* JDBC
* MySQL
* Object-Oriented Programming
* DAO (Data Access Object) Pattern
* Git & GitHub
* VS Code

## Project Structure

```text
SkillSwap/
│
├── src/
│   ├── app/
│   │   ├── Main.java
│   │   └── Skill.java
│   │
│   ├── config/
│   │   └── DBConnection.java
│   │
│   ├── dao/
│   │   ├── ExchangeDAO.java
│   │   ├── ReviewDAO.java
│   │   ├── SkillDAO.java
│   │   ├── SkillRequestDAO.java
│   │   ├── UserDAO.java
│   │   └── UserSkillDAO.java
│   │
│   └── model/
│       ├── Exchange.java
│       ├── Review.java
│       ├── Skill.java
│       ├── SkillRequest.java
│       ├── User.java
│       └── UserSkill.java
│
├── lib/
│   └── mysql-connector-j-26.7.0.jar
│
├── .gitignore
└── README.md
```

## Database

SkillSwap uses MySQL with the following main tables:

* `users`
* `skills`
* `user_skills`
* `skill_requests`
* `exchanges`
* `reviews`

## Database Configuration

The database password is **not stored directly in the Java source code**.

The application reads the password from the following environment variable:

```text
SKILLSWAP_DB_PASSWORD
```

### Windows PowerShell

Set the user environment variable:

```powershell
[Environment]::SetEnvironmentVariable("SKILLSWAP_DB_PASSWORD", "YOUR_DATABASE_PASSWORD", "User")
```

After setting it, open a new PowerShell window.

## How to Run

### 1. Clone the repository

```bash
git clone https://github.com/gshagun16/SkillSwap.git
cd SkillSwap
```

### 2. Compile

On Windows PowerShell:

```powershell
javac -cp "lib\mysql-connector-j-26.7.0.jar" -d out src\config\DBConnection.java src\model\*.java src\dao\*.java src\app\Main.java
```

### 3. Run

```powershell
java -cp "out;lib\mysql-connector-j-26.7.0.jar" app.Main
```

## Application Flow

```text
Register / Login
       ↓
   Dashboard
       ↓
   Manage Skills
       ↓
 Search Users
       ↓
 Send Skill Request
       ↓
 Accept / Reject Request
       ↓
   Skill Exchange
       ↓
 Complete Exchange
       ↓
   Give / Receive Review
```

## Learning Outcomes

This project demonstrates practical implementation of:

* Java Classes and Objects
* Encapsulation
* Exception Handling
* JDBC
* SQL Queries
* CRUD Operations
* DAO Architecture
* Database Relationships
* User Authentication
* Environment Variables
* Git and GitHub

## Future Improvements

* Web-based frontend
* REST API using Spring Boot
* Password hashing
* JWT-based authentication
* User profile images
* Real-time messaging
* Skill recommendation system
* Advanced search and filtering

## Author

**Shagun Gupta**

Computer Engineering Student

GitHub: https://github.com/gshagun16
