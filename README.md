# URL Shortener Platform

> A production-grade distributed URL shortening service built with
> Java 17, Spring Boot 3, PostgreSQL, Redis, and Apache Kafka.

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen?style=flat-square)
![License](https://img.shields.io/badge/license-MIT-blue?style=flat-square)

## Tech Stack
| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Database | PostgreSQL (Phase 2) |
| Cache | Redis (Phase 4) |
| Messaging | Apache Kafka (Phase 4) |
| Auth | JWT / Spring Security (Phase 3) |
| Containerisation | Docker & Docker Compose (Phase 5) |

## API Endpoints (Current)

| Method | Endpoint | Description | Status |
|---|---|---|---|
| GET | /api/v1/health | Liveness check | ✅ Live |
| POST | /api/v1/urls | Shorten a URL | ✅ Live (stub) |
| GET | /api/v1/urls/{shortCode} | Get URL details | ✅ Live (stub) |
| DELETE | /api/v1/urls/{shortCode} | Delete a URL | ✅ Live (stub) |
| GET | /{shortCode} | Redirect to original | 🔄 Phase 2 |

## Features
- [x] Layered architecture (Controller → Service → Repository)
- [x] REST API with correct HTTP status codes
- [x] Input validation (JSR-380 / Hibernate Validator)
- [x] DTO pattern (request/response separation)
- [ ] PostgreSQL persistence (Phase 2)
- [ ] JWT Authentication (Phase 3)
- [ ] Redis caching — sub-millisecond redirects (Phase 4)
- [ ] Apache Kafka — async click analytics (Phase 4)
- [ ] Docker & Docker Compose (Phase 5)
- [ ] Live deployment (Phase 6)

## Project Structure
