# 🍽️ API Food

A REST API for restaurant management and food delivery, developed with Spring Boot and Java.

## 📋 Table of Contents

- [Technologies](#-technologies)
- [Features](#-features)
- [Endpoints and Request Bodies](#-endpoints-and-request-bodies)
- [Getting Started](#-getting-started)
- [Database Schema](#-database-schema)

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

## 🔌 Endpoints and Request Bodies

### Cities

| Method | Route | Description |
|--------|------|-----------|
| POST | `/cities/new` | Create a new city |
| GET | `/cities/all` | List all cities |
| GET | `/cities/{id}` | Get a specific city |
| PUT | `/cities/{id}` | Update a city |
| DELETE | `/cities/{id}` | Delete a city |

#### Request Body Examples:

Create City (POST `/cities/new`):
```json
{
  "name": "New York",
  "stateId": 1
}
```

Update City (PUT `/cities/{id}`):
```json
{
  "name": "Los Angeles",
  "stateId": 2
}
```

### States

| Method | Route | Description |
|--------|------|-----------|
| POST | `/states/new` | Create a new state |
| GET | `/states/all` | List all states |
| GET | `/states/{id}` | Get a specific state |
| PUT | `/states/{id}` | Update a state |
| DELETE | `/states/{id}` | Delete a state |

#### Request Body Examples:

Create State (POST `/states/new`):
```json
{
  "name": "São Paulo",
  "countryId": 1
}
```

### Kitchens

| Method | Route | Description |
|--------|------|-----------|
| POST | `/kitchens/new` | Create a new kitchen |
| GET | `/kitchens/all` | List all kitchens |
| GET | `/kitchens/{id}` | Get a specific kitchen |
| PUT | `/kitchens/{id}` | Update a kitchen |
| DELETE | `/kitchens/{id}` | Delete a kitchen |

#### Request Body Examples:

Create Kitchen (POST `/kitchens/new`):
```json
{
  "name": "Italian"
}
```

Update Kitchen (PUT `/kitchens/{id}`):
```json
{
  "name": "Brazilian"
}
```

### Restaurants

| Method | Route | Description |
|--------|------|-----------|
| POST | `/restaurants/new` | Create a new restaurant |
| GET | `/restaurants/all` | List all restaurants |
| GET | `/restaurants/{id}` | Get a specific restaurant |
| PUT | `/restaurants/{id}` | Update a restaurant |
| DELETE | `/restaurants/{id}` | Delete a restaurant |

#### Request Body Examples:

Create Restaurant (POST `/restaurants/new`):
```json
{
  "name": "Italian Food",
  "freightRate": 12.3,
  "kitchenId": 1,
  "cityId": 1,
  "address": {
    "zipCode": "00000000",
    "street": "some street",
    "number": "1234",
    "complement": "some complement",
    "neighborhood": "some neighborhood"
  }
}
```

Update Restaurant (PUT `/restaurants/{id}`):
```json
{
  "name": "Brazilian Food",
  "freightRate": 15.0,
  "kitchenId": 2,
   "cityId": 2,
  "address": {
    "zipCode": "11111111",
    "street": "another street",
    "number": "4321",
    "complement": "another complement",
    "neighborhood": "another neighborhood"
  }
}
```

### Payment Methods

| Method | Route | Description |
|--------|------|-----------|
| POST | `/payment-methods/new` | Create a new payment method |
| GET | `/payment-methods/all` | List all payment methods |
| GET | `/payment-methods/{id}` | Get a specific payment method |
| PUT | `/payment-methods/{id}` | Update a payment method |
| DELETE | `/payment-methods/{id}` | Delete a payment method |

#### Request Body Examples:

Create Payment Method (POST `/payment-methods/new`):
```json
{
  "description": "Credit Card"
}
```

Update Payment Method (PUT `/payment-methods/{id}`):
```json
{
  "description": "Debit Card"
}
```

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

1. Clone the repository: bash git clone [https://github.com/your-ThalysRow/apifood.git](https://github.com/ThalysRow/apifood.git)
2. Configure the database in `application.properties` or using environment variables (`.env`)
3. Run Docker Compose to start required services: bash docker-compose up -d
4. Run the application using Maven: bash ./mvnw spring-boot:run

The API will be available at `http://localhost:8080`

## 📝 Requirements

- Java 24
- Maven
- Docker and Docker Compose
- PostgreSQL (configured in `compose.yaml`)

## 🔒 Development Environment

To set up the development environment, copy `.env.exemple` to `.env` and adjust the variables as needed:
```bash
env DB_HOST=localhost DB_PORT=5432
DB_NAME=apifood DB_USER=your_username
DB_PASSWORD=your_password
```

## 📫 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request