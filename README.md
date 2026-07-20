Here is a comprehensive, professional, and well-structured `README.md` for your GitHub repository based on your project report. It includes all the technical specifications, architectural details, and team contributions formatted cleanly for GitHub.

---

```markdown
# Car Rental System

A full-stack, enterprise-grade web application designed for comprehensive car rental management. Developed as a group final project for the JAVA course at **Jamhuuriya University of Science and Technology (JUST)**[cite: 1].

A key highlight of this project is its **cross-backend interoperability**. The system features a decoupled architecture with a single **React frontend** that seamlessly communicates with either of two independently developed backends—one built in **Java (Spring Boot)** and the other in **C# (ASP.NET Core)**—both sharing the same **PostgreSQL database**[cite: 1].

---

## 🚀 Key Features

- **Role-Based Authentication:** Secure admin and staff roles equipped with protected routing[cite: 1].
- **Live Dashboard:** Interactive revenue charts and real-time vehicle status breakdowns utilizing `Recharts`[cite: 1].
- **Vehicle Management:** Full tracking of inventory, including availability, real-time rental status, and maintenance logs[cite: 1].
- **Customer Management:** Tracks licenses, demographic data, and active customer blacklist statuses[cite: 1].
- **Rental Operations:** Streamlined management of active, returned, and overdue rentals with a one-click vehicle return system[cite: 1].
- **Payment Processing:** Financial tracking categorized by payment methods (Cash, Credit Card, Debit Card, Bank Transfer) and real-time statuses[cite: 1].
- **Advanced Reporting:** Date-filterable reporting utilities featuring seamless CSV exports and print support[cite: 1].
- **UI/UX Excellence:** Modern user interface boasting complete dark mode support and fully responsive layouts (desktop table views and mobile card views)[cite: 1]. Includes 10 highly reusable shared UI components[cite: 1].

---

## 🛠️ Tech Stack

### Frontend
* **Core Framework:** React + Vite (v18.x)[cite: 1]
* **Styling:** Tailwind CSS (Utility-first CSS)[cite: 1]
* **Routing:** React Router DOM (v6.x)[cite: 1]
* **HTTP Client:** Axios (API Consumption)[cite: 1]
* **Data Visualization:** Recharts[cite: 1]
* **Icons:** Tabler Icons[cite: 1]

### Backend Options
* **Backend 1 (Java):** Spring Boot v3.5.x & Java 17[cite: 1]
  * *Build Tool:* Maven[cite: 1]
  * *ORM / Data Access:* Spring Data JPA + Hibernate[cite: 1]
  * *Security:* Spring Security (Stateless session management, CORS configurations)[cite: 1]
  * *Validation:* Bean Validation (`@Valid`, `@NotBlank`, etc.)[cite: 1]
* **Backend 2 (C#):** ASP.NET Core (REST API)[cite: 1]

### Database & Infrastructure
* **Database:** PostgreSQL (Hosted via Supabase)[cite: 1]
* **Hosting Platforms:** 
  * Frontend: Vercel (Two separate deployments)[cite: 1]
  * Java Backend: Railway (CI/CD auto-deploy via GitHub)[cite: 1]
  * C# Backend: Render (CI/CD auto-deploy via GitHub)[cite: 1]

---

## 🏗️ System Architecture

The application adopts a decoupled client-server architectural pattern. The React frontend interacts with either backend via RESTful API endpoints[cite: 1]. Since both backends share an identical API contract (matching JSON payloads and routing paths) and map to the same Supabase PostgreSQL data layer, the frontend functions interchangeably between them[cite: 1].

```text
       ┌────────────────────────────────────────┐
       │         React Frontend (Vercel)        │
       └───────────────────┬────────────────────┘
                           │
             HTTP REST API Requests (Axios)
                           │
                           ▼
 ┌────────────────────────────────────────────────────┐
 │  C# Backend (Render)  OR  Java Backend (Railway)   │
 │                                                    │
 │  Controller ──► Service ──► Repository (JPA/ORM)   │
 └─────────────────────────┬──────────────────────────┘
                           │
                           ▼
       ┌────────────────────────────────────────┐
       │      PostgreSQL Database (Supabase)    │
       └────────────────────────────────────────┘

```

### Spring Boot Backend Layering

1. **Entity Layer:** JPA models representing database tables using annotations and Bean Validation rules.


2. **Repository Layer:** Abstracted data layers leveraging `JpaRepository` to minimize boilerplate CRUD queries.


3. **Service Layer:** Houses modular business logic, handling transactional configurations (`@Transactional`) and core operational computations.


4. **Controller Layer:** REST API Controllers validating payloads via `@Valid` and assuring standardized `ApiResponse` shapes.


5. **Config & Exception Handling:** Enforces state-free security protocols, unified CORS mappings, and an app-wide `GlobalExceptionHandler`.



---

## 🛣️ API Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| **GET** | `/api/Users` | Get all users

 |
| **GET** | `/api/Users/{id}` | Get user by ID

 |
| **POST** | `/api/Users` | Create new user

 |
| **PUT** | `/api/Users/{id}` | Update user

 |
| **DELETE** | `/api/Users/{id}` | Delete user

 |
| **GET** | `/api/Vehicles` | Get all vehicles

 |
| **POST** | `/api/Vehicles` | Add new vehicle

 |
| **PUT** | `/api/Vehicles` | Update vehicle

 |
| **DELETE** | `/api/Vehicles/{id}` | Delete vehicle

 |
| **GET** | `/api/Customers` | Get all customers

 |
| **POST** | `/api/Customers` | Add new customer

 |
| **PUT** | `/api/Customers` | Update customer

 |
| **DELETE** | `/api/Customers/{id}` | Delete customer

 |
| **GET** | `/api/Rentals` | Get all rentals (with JOINs)

 |
| **POST** | `/api/Rentals` | Create new rental entry

 |
| **PUT** | `/api/Rentals/return/{rentalId}/{vehicleId}` | Process vehicle return

 |
| **DELETE** | `/api/Rentals/{id}` | Delete rental record

 |
| **GET** | `/api/Payments` | Get all payments (with JOINs)

 |
| **GET** | `/api/Payments/revenue` | Get total aggregated revenue

 |
| **POST** | `/api/Payments` | Log new payment

 |
| **PUT** | `/api/Payments/status/{id}/{status}` | Update specific payment status

 |
| **DELETE** | `/api/Payments/{id}` | Delete payment record

 |

---

## 👥 The Team & Contributions

* **Abdiasis Mohamed Abdi Muuse (ID: C1230383):** Project Lead. Engineered the complete C# backend, developed the Java Spring Boot backend, handled server deployments, and completed multi-backend integrations.


* **Umu Kheyr Faarah Abdullahi (ID: C1230421):** Developed the React frontend architecture, created Users and Dashboard views, and built core shared components (`PageHeader`, `StatCard`, `Badge`).


* **Hidaya Ali Hashi (ID: C1230423):** Developed the Customers and Vehicles frontend pages, and designed reusable elements like `ActionButtons` and `AddButton`.


* **Ikran Muktar Abdullahi (ID: C1230441):** Developed frontend interfaces for Payments and Reports, alongside global UI features (`ConfirmModal`, `SearchInput`).


* **Saabiriin Maxamuud Yusuf (ID: C1230419):** Built the Rentals view, master Sidebar navigations, and global view wrappers (`AnimatedPage`, `NotFound`).



---

## 🛡️ Educational AI Usage Acknowledgement

In compliance with project specifications, the team utilized **Claude (by Anthropic)** as an educational assistant. The tool was strictly utilized to deepen the understanding of Spring Boot abstractions, patch specific compilation/library errors (e.g., JWT syntax upgrades, CORS adjustments), and cross-examine deployment guidelines for Cloud environments. All core codebase mechanics were authored and fully verified by the team members.

---

## 🎓 Instructor & Course Context

* **Institution:** Jamhuuriya University of Science and Technology (JUST)


* **Course:** Advanced JAVA


* **Instructor:** Mohamed Abdullahi Mohamud (Shurie)


* **Project Deadline:** July 14, 2026



```

```
