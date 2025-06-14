# 🍽️ API Food

A REST API for restaurant management and food delivery, developed with Spring Boot and Java.

## 📋 Table of Contents

- [Technologies](#-technologies)
- [Features](#-features)
- [Endpoints](#-endpoints)
- [Getting Started](#-getting-started)
- [Database Schema](#-database-schema)
- [Contributing](#-contributing)
- [License](#-license)

## 🚀 Technologies

- Java 24
- Spring Boot
- Spring Data JPA
- Jakarta EE
- Lombok
- Docker
- Maven
- PostgreSQL

## ✨ Features

The system provides a complete API for managing:

- Cities and States
- Kitchens (Cuisines)
- Restaurants
- Payment Methods
- Users and Groups
- Permissions
- Products

## 🔌 Endpoints

### Cities

| Method | Route | Description |
|--------|------|-----------|
| POST | `/cities/new` | Create a new city |
| GET | `/cities/all` | List all cities |
| GET | `/cities/{id}` | Get a specific city |
| PUT | `/cities/{id}` | Update a city |
| DELETE | `/cities/{id}` | Delete a city |

### States

| Method | Route | Description |
|--------|------|-----------|
| POST | `/states/new` | Create a new state |
| GET | `/states/all` | List all states |
| GET | `/states/{id}` | Get a specific state |
| PUT | `/states/{id}` | Update a state |
| DELETE | `/states/{id}` | Delete a state |

### Kitchens

| Method | Route | Description |
|--------|------|-----------|
| POST | `/kitchens/new` | Create a new kitchen |
| GET | `/kitchens/all` | List all kitchens |
| GET | `/kitchens/{id}` | Get a specific kitchen |
| PUT | `/kitchens/{id}` | Update a kitchen |
| DELETE | `/kitchens/{id}` | Delete a kitchen |

### Restaurants

| Method | Route | Description |
|--------|------|-----------|
| POST | `/restaurants/new` | Create a new restaurant |
| GET | `/restaurants/all` | List all restaurants |
| GET | `/restaurants/{id}` | Get a specific restaurant |
| PUT | `/restaurants/{id}` | Update a restaurant |
| DELETE | `/restaurants/{id}` | Delete a restaurant |

### Payment Methods

| Method | Route | Description |
|--------|------|-----------|
| POST | `/payment-methods/new` | Create a new payment method |
| GET | `/payment-methods/all` | List all payment methods |
| GET | `/payment-methods/{id}` | Get a specific payment method |
| PUT | `/payment-methods/{id}` | Update a payment method |
| DELETE | `/payment-methods/{id}` | Delete a payment method |

## 📊 Database Schema

The application uses the following main entities:

- **Restaurants**: Stores restaurant information including address and delivery fee
- **Kitchens**: Represents different cuisine types
- **Products**: Restaurant menu items
- **Users**: System users with authentication
- **Groups**: User groups for permission management
- **Permissions**: Access control settings
- **Payment Methods**: Available payment options
- **Cities and States**: Location management

## 🚀 Getting Started

1. Clone the repository:
2. Configure the database in `application.properties` or using environment variables (`.env`)
3. Run Docker Compose to start required services:
4. Run the application using Maven:
The API will be available at `http://localhost:8080`

## 📝 Requirements

- Java 24
- Maven
- Docker and Docker Compose
- PostgreSQL (configured in `compose.yaml`)

## 🔒 Development Environment

To set up the development environment, copy `.env.exemple` to `.env` and adjust the variables as needed:

## 📫 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is under MIT license. See [LICENSE](LICENSE) file for more details.