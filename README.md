# SkillSwap

SkillSwap is a Java-based skill exchange platform where users can offer their skills, learn new skills, search for other users, send skill requests, manage skill exchanges, and provide reviews.

## Features

- User Registration
- User Login
- View User Profile
- Add Skills
- Remove Skills
- Search Users by Skill
- Send Skill Requests
- Accept or Reject Requests
- View Skill Exchanges
- Complete Exchanges
- Add Reviews
- View Reviews Received
- View Reviews Given
- Logout

## Technologies Used

- Java
- MySQL
- JDBC
- SQL
- VS Code

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
│   │   ├── UserDAO.java
│   │   ├── SkillDAO.java
│   │   ├── UserSkillDAO.java
│   │   ├── SkillRequestDAO.java
│   │   ├── ExchangeDAO.java
│   │   └── ReviewDAO.java
│   │
│   └── model/
│       ├── User.java
│       ├── Skill.java
│       ├── UserSkill.java
│       ├── SkillRequest.java
│       ├── Exchange.java
│       └── Review.java
│
├── lib/
│   └── mysql-connector-j-26.7.0.jar
│
├── out/
└── README.md