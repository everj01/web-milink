# ↗ MiLink

> Acorta tu link altoque nomás.

**Acortador de enlaces con estadísticas**, hecho con **microservicios en Java Spring Boot** y un frontend en **React**.

Pegas un link largo, obtienes uno corto (o con tu propio alias) y ves cuántas personas lo abrieron, por día y por navegador.

---

##  Tecnologías

| Capa | Tecnología |
|---|---|
| Backend | Java 17, Spring Boot 3.3, Spring Data JPA, Bean Validation |
| Microservicios | Spring Cloud Gateway, Eureka, OpenFeign |
| Base de datos | PostgreSQL (una BD por servicio) |
| Frontend | React 18, Vite |
| Contenedores | Docker, Docker Compose |

---

### Ejcutarlo con Docker 
```bash
docker compose up -d --build
```
| Qué | URL |
|---|---|
| App | http://localhost:3000 |
| Eureka | http://localhost:8761 |
