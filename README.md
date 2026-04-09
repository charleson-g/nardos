# Nardo's Platform

A robust, Spring Boot-based e-commerce and inventory management platform. This application features a layered architecture that seamlessly bridges a web-based UI with persistent inventory, order, and payment processing.

## 🚀 Features

* **Inventory Management**: Track and manage products, monitor stock movements, and generate comprehensive stock reports. 
* **Order Processing**: End-to-end shopping experience featuring a customer cart, order forms, payment processing, and pickup slot scheduling.
* **Admin Dashboard**: Dedicated portal for store administrators to oversee sales, update product listings, and manage overarching operations.
* **Role-Based Security**: Built-in access control and authentication mechanisms to protect sensitive administrative endpoints.
* **Server-Side UI**: Responsive user interface built with HTML/CSS and rendered via Spring templates.

## 🛠️ Tech Stack

* **Language**: Java
* **Framework**: Spring Boot
* **Data Access**: Spring Data JPA
* **Build Tool**: Gradle
* **Frontend**: HTML5, CSS3, Server-side templating (Thymeleaf/Spring)

## 📁 Project Architecture

The application is structured using a clean, layered architecture:

* `com.nardo.platform.business` - Contains the core domain entities (`Product`, `Order`, `Payment`, `Sale`, etc.) and business logic.
* `com.nardo.platform.persistence` - Spring Data JPA Repositories (`InventoryRepository`, `OrderRepository`, etc.) handling all database interactions.
* `com.nardo.platform.ui` - Boundary interfaces and controllers bridging the backend data with the frontend templates.
* `com.nardo.platform.security` - Authentication and access control handlers.
* `src/main/resources/templates` - HTML views including dashboards, menus, and checkout flows.

## ⚙️ Getting Started

### Prerequisites

* Java 17 or higher
* Database server (configured in `src/main/resources/application.properties`)

### Running the Application Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/nardos.git
   cd nardos

2. Configure the Database:
Update your database credentials in src/main/resources/application.properties.

3. Build and Run (using the Gradle Wrapper):

   On Windows:
   gradlew.bat bootRun

   On Mac/Linux:
   ./gradlew bootRun

4. Access the Application:
Open your browser and navigate to http://localhost:8080/ (or the port specified in your properties file).


🧪 Testing
To run the automated test suite, execute the following command:

On Windows:
gradlew.bat test

On Mac/Linux:
./gradlew test

Test reports will be generated in index.html.


📄 License
This project is licensed under the MIT License - see the LICENSE file for details.



If you are using a specific database (like MySQL, PostgreSQL, or H2), or a specific Java version, feel free to update those sections to accurately reflect your system.
