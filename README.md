# 🏢 SmartOffice API

Nowoczesny system zarządzania biurem w architekturze REST. Projekt umożliwia zarządzanie rezerwacjami zasobów (biurka, parking, sale) z zaawansowaną logiką uprawnień (Pracownik/Manager).

**Backend:** Java 21 · Spring Boot 3 · Spring Data JPA (Hibernate)  
**Database:** PostgreSQL (z obsługą SQLite/H2 dla testów)  
**DevOps:** Docker · Docker Compose · Maven  
**API Docs:** Swagger / OpenAPI 3

---

## ✨ Kluczowe Funkcjonalności

### 🔐 System Ról i Bezpieczeństwo
- **RBAC (Role-Based Access Control):** Podział na `ROLE_EMPLOYEE` oraz `ROLE_MANAGER`.
- **JWT Auth:** Zabezpieczone punkty końcowe (przygotowane pod integrację z frontendem).
- **Manager Tools:** 
    - Możliwość usuwania (zwalniania) użytkowników.
    - Nadpisywanie rezerwacji (priorytet managera).

### 📅 Zarządzanie Zasobami (REST Endpoints)
- **Desks & Parking:** Rezerwacja miejsc w czasie rzeczywistym.
- **Meeting Rooms:** System rezerwacji sal z walidacją nakładających się terminów.
- **Business Logic:** Automatyczne sprawdzanie dostępności i priorytetów.

---

## 🛠️ Tech Stack & ORM

Projekt wykorzystuje **Hibernate (JPA)** jako warstwę mapowania obiektowo-relacyjnego, co pozwala na:
- Łatwą zmianę bazy danych (PostgreSQL/MySQL/Oracle) bez zmiany kodu.
- Automatyczną generację schematu bazy danych.
- Zaawansowane zapytania przez `JPA Repositories`.

---

## 🚀 Szybki Start (Docker Compose)

Najszybszy sposób na uruchomienie całego środowiska (API + Baza PostgreSQL):

```bash
git clone https://github.com/twoj-username/smart-office-api.git
cd smart-office-api

# Uruchomienie kontenerów (API + DB)
docker compose up -d
```

API będzie dostępne pod adresem: `http://localhost:8080`  
Dokumentacja Swagger (UI): `http://localhost:8080/swagger-ui.html`

---

## 📁 Struktura Projektu (Clean Architecture)
```text
smart-office/
├── src/main/java/com/smartoffice/
│   ├── controller/      # Punkty końcowe REST (API)
│   ├── service/         # Logika biznesowa (Rezerwacje, Priorytety)
│   ├── repository/      # Interfejsy Spring Data JPA (ORM)
│   ├── entity/          # Modele bazodanowe (Hibernate Entities)
│   ├── dto/             # Obiekty transferu danych (Request/Response)
│   ├── config/          # Konfiguracja Security (JWT), Swagger, DB
│   └── exception/       # Globalna obsługa błędów API
├── src/main/resources/
│   └── application.yml  # Konfiguracja bazy danych i aplikacji
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

---

## 📡 API Preview (Główne Endpointy)

| Metoda | Endpoint | Opis | Uprawnienia |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Rejestracja nowego konta | Publiczny |
| `GET` | `/api/resources` | Lista biurek/sal/parkingu | Employee+ |
| `POST` | `/api/reservations` | Stworzenie nowej rezerwacji | Employee+ |
| `GET` | `/api/admin/users` | Lista wszystkich pracowników | **Manager** |
| `DELETE` | `/api/admin/users/{id}` | Zwolnienie pracownika | **Manager** |

---

## 🗺️ Roadmap (Plany na przyszłość)
- [ ] **Frontend:** Implementacja panelu w React + Tailwind CSS.
- [ ] **Notifications:** Powiadomienia e-mail o nadchodzących rezerwacjach.
- [ ] **Mobile App:** Dedykowana aplikacja do szybkich rezerwacji kodem QR.

---

## ⚖️ Licencja
MIT © 2026 — SmartOffice API Team.
