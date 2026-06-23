# Movie Booking System

Spring Boot REST API for Movie Ticket Booking.

## Features

- JWT Authentication
- Role Based Access Control (ADMIN/CUSTOMER)
- Movie Management
- Show Management
- Ticket Booking
- Promo Code Support
- Booking Cancellation & Refund
- Global Exception Handling
- Swagger/OpenAPI Documentation
- Optimistic Locking for Concurrent Booking Protection

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL
- Lombok
- Swagger/OpenAPI

## APIs

### Authentication
- Register
- Login

### Movies
- Add Movie
- Update Movie
- Delete Movie
- Get Movie By Id
- Get All Movies

### Shows
- Add Show
- Update Show
- Delete Show
- Get Show By Id
- Get All Shows

### Bookings
- Book Tickets
- View My Bookings
- Cancel Booking

## Business Rules

- Booking fails if seats are unavailable.
- Promo codes allowed only when:
  - Booking count > 5 OR
  - Total spent > ₹1500
- FREE_SEAT promo provides one free seat.
- FLAT250 promo provides ₹250 discount.
- Optimistic locking prevents overselling seats during concurrent bookings.