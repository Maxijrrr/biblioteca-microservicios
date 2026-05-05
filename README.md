# 📚 Sistema de Gestión Bibliotecaria — Hito 2

Sistema de gestión bibliotecaria construido con arquitectura de microservicios Spring Boot, Docker y MySQL.

---

## 👥 Integrantes

| Nombre | Rol |
|--------|-----|
| Maximiliano Valenzuela | ms-autenticador, ms-catalogo, ms-prestamos |
| Genesis Cerda | ms-perfiles, ms-inventario, ms-devoluciones |
| Vicente Hueichapan | ms-ebooks, ms-penalizaciones, ms-reservas, ms-sucursales |

---

## 📊 Estado del Sistema (Hito 2)

| Microservicio     | Puerto | DB Name              | Funcionalidad                           |
|:------------------|:------:|:---------------------|:----------------------------------------|
| ms-autenticador   | 8080   | db_autenticador      | Registro, Login y seguridad perimetral  |
| ms-catalogo       | 8081   | db_catalogo          | Catálogo de libros y gestión de existencias |
| ms-devoluciones   | 8082   | db_devoluciones      | Gestión de devolución de libros físicos |
| ms-ebooks         | 8083   | db_ebooks            | Administración de libros digitales      |
| ms-inventario     | 8084   | db_inventario        | Control de inventario por sucursal      |
| ms-penalizaciones | 8085   | db_penalizaciones    | Gestión de multas por retrasos o daños  |
| ms-perfiles       | 8086   | db_perfiles          | Perfiles de usuarios registrados y roles|
| ms-prestamos      | 8087   | db_prestamos         | Préstamos de libros y control de plazos |
| ms-reservas       | 8088   | db_reservas          | Reservas de libros no disponibles       |
| ms-sucursales     | 8089   | db_sucursales        | Sedes físicas donde se encuentran libros|

---

## 🏗️ Arquitectura Database-per-Service

Cada microservicio es **dueño exclusivo** de su base de datos. Ningún servicio accede directamente a la BD de otro: la comunicación es **exclusivamente por API REST**.

```
ms-autenticador  ──▶ db_autenticador  (MySQL :3306)
ms-catalogo      ──▶ db_catalogo      (MySQL :3307)
ms-devoluciones  ──▶ db_devoluciones  (MySQL :3308)
ms-ebooks        ──▶ db_ebooks        (MySQL :3309)
ms-inventario    ──▶ db_inventario    (MySQL :3310)
ms-penalizaciones──▶ db_penalizaciones(MySQL :3311)
ms-perfiles      ──▶ db_perfiles      (MySQL :3312)
ms-prestamos     ──▶ db_prestamos     (MySQL :3313)
ms-reservas      ──▶ db_reservas      (MySQL :3314)
ms-sucursales    ──▶ db_sucursales    (MySQL :3315)
```

---

## 🔗 Comunicación entre Microservicios (Hito 2)

### Diagrama de dependencias

```
┌──────────────────┐   GET /api/perfil/{id}          ┌─────────────────┐
│  ms-prestamos    │ ─────────────────────────────▶  │  ms-perfiles    │
│     :8087        │                                  │     :8086       │
│                  │   GET /api/inventario/isbn/{isbn}┌─────────────────┐
│                  │ ─────────────────────────────▶  │  ms-inventario  │
└──────────────────┘                                  │     :8084       │
                                                      └─────────────────┘

┌──────────────────┐   GET /api/prestamos/{id}        ┌─────────────────┐
│  ms-devoluciones │ ─────────────────────────────▶   │  ms-prestamos   │
│     :8082        │                                   │     :8087       │
└──────────────────┘                                   └─────────────────┘
```

### Tabla de contratos API

| Origen           | Destino        | Método | Endpoint                        | DTO respuesta   |
|:-----------------|:---------------|:------:|:--------------------------------|:----------------|
| ms-prestamos     | ms-perfiles    | GET    | `/api/perfil/{id}`              | `PerfilDTO`     |
| ms-prestamos     | ms-inventario  | GET    | `/api/inventario/isbn/{isbn}`   | `InventarioDTO` |
| ms-devoluciones  | ms-prestamos   | GET    | `/api/prestamos/{id}`           | `PrestamoDTO`   |

### Tecnología utilizada

- **Cliente REST:** OpenFeign (`spring-cloud-starter-openfeign`)
  - **Justificación:** cliente declarativo — se define una sola interfaz Java y Spring genera la implementación HTTP automáticamente. Reduce código, mejora legibilidad y se integra nativamente con Spring Boot.
- **Manejo de errores:** `@RestControllerAdvice` + excepciones personalizadas (`RecursoNoEncontradoException`, `ServicioNoDisponibleException`)
- **Logs:** SLF4J (`LoggerFactory`) en cada llamada externa — antes, después y en caso de fallo
- **Timeouts:** `connectTimeout=3000ms`, `readTimeout=5000ms` vía `spring.cloud.openfeign.client.config.default`
- **Pruebas de integración:** colección Postman en `/postman/hito2-integracion.json`

### Escenario de despliegue

- [x] **Escenario A — Todos los servicios en una sola instancia EC2**
  - **Instancia:** AWS EC2 t3.large (Ubuntu 24.04)
  - **IP pública:** `3.91.218.133`
  - **Repositorio:** https://github.com/Maxijrrr/biblioteca-microservicios
  - Los contenedores se comunican por nombre Docker interno (red `red_interna_proyecto`)
  - Las URLs de Feign usan `http://ms-<servicio>:<puerto>` — resolución interna Docker DNS

---

## 🚀 Despliegue Técnico

### Prerrequisitos
- Docker Desktop con al menos 8 GB RAM asignados
- Git

### Levantar el sistema completo

```bash
git clone https://github.com/Maxijrrr/biblioteca-microservicios
cd biblioteca-microservicios
docker compose up -d --build
```

Docker espera a que cada MySQL esté 100% listo (`healthcheck`) antes de iniciar el microservicio correspondiente, eliminando errores de Race Condition en el arranque.

### Verificar estado del sistema

```bash
# Ver los 20 contenedores corriendo (10 MySQL + 10 Spring Boot)
docker ps

# Ver logs de un microservicio específico
docker logs ms-prestamos

# Ver solo los logs de comunicación entre servicios
docker logs ms-prestamos | grep "ms-perfiles\|ms-inventario"
```

---

## 🧪 Pruebas de Integración (Hito 2)

### Importar colección Postman
1. Abrir Postman
2. `File → Import → postman/hito2-integracion.json`
3. Ejecutar los flujos en orden

### Pruebas manuales desde EC2

**Paso 1 — Crear perfil de alumno:**
```bash
curl -X POST http://localhost:8086/api/perfil \
-H "Content-Type: application/json" \
-d '{"rut":"12345678-9","nombre":"Juan Perez","correo":"juan@duoc.cl","carrera":"Informatica"}'
```
> Respuesta esperada: `{"success":true,"message":"Perfil creado exitosamente","data":{...},"status":201}`

**Paso 2 — Crear stock en inventario:**
```bash
curl -X POST http://localhost:8084/api/inventario \
-H "Content-Type: application/json" \
-d '{"isbn":"978-3-16","idSucursal":1,"stockTotal":5,"stockDisponible":5}'
```
> Respuesta esperada: `{"idInventario":1,"isbn":"978-3-16","idSucursal":1,"stockTotal":5,"stockDisponible":5}`

**Paso 3 — Crear préstamo (Prueba de Fuego Feign E2E):**
```bash
curl -X POST http://localhost:8087/api/prestamos \
-H "Content-Type: application/json" \
-d '{"idPerfil":1,"isbn":"978-3-16"}'
```
> ✅ `ms-prestamos` consulta internamente a `ms-perfiles` (valida alumno) y a `ms-inventario` (valida stock). Si ambos confirman, crea el préstamo con estado `ACTIVO`.

**Paso 4 — Prueba de resiliencia (servicio caído):**
```bash
# Apagar ms-perfiles
docker stop ms-perfiles

# Intentar crear préstamo — el sistema NO debe caer con Error 500
curl -X POST http://localhost:8087/api/prestamos \
-H "Content-Type: application/json" \
-d '{"idPerfil":1,"isbn":"978-3-16"}'
```
> ✅ Respuesta controlada: `{"error":"Servicio de perfiles no disponible en este momento"}` HTTP 503

**Paso 5 — Recuperar ms-perfiles:**
```bash
docker start ms-perfiles
```

---

## 🗂️ Estructura del Proyecto

```
biblioteca-microservicios/
├── docker-compose.yml              ← Orquestación: 10 servicios + 10 BDs con healthcheck
├── postman/
│   └── hito2-integracion.json     ← Colección de pruebas E2E
├── README.md
└── codigo-fuente/
    ├── ms-autenticador/
    │   ├── Dockerfile              ← Build 2 etapas: Maven (compilar) + JRE (ejecutar)
    │   ├── pom.xml
    │   └── src/main/java/cl/duoc/ms_autenticador/
    │       ├── controller/         ← REST endpoints con ResponseEntity<DTO>
    │       ├── service/            ← Lógica de negocio + logs SLF4J
    │       ├── repository/         ← JpaRepository (solo accede a SU BD)
    │       ├── model/              ← Entidades JPA @Entity (NO se exponen)
    │       ├── dto/                ← DTOs de entrada y salida
    │       └── exception/          ← GlobalExceptionHandler + custom exceptions
    ├── ms-catalogo/        ← mismo patrón
    ├── ms-devoluciones/    ← mismo patrón + FeignClient → ms-prestamos
    ├── ms-ebooks/          ← mismo patrón
    ├── ms-inventario/      ← mismo patrón
    ├── ms-penalizaciones/  ← mismo patrón
    ├── ms-perfiles/        ← mismo patrón
    ├── ms-prestamos/       ← mismo patrón + FeignClient → ms-perfiles, ms-inventario
    ├── ms-reservas/        ← mismo patrón
    └── ms-sucursales/      ← mismo patrón
```

---

## ✅ Lista de Cotejo Final — Hito 2

### Fase 1 — Diseño de dependencias
- [x] Diagrama de dependencias publicado en README (10 servicios)
- [x] Tabla de contratos con Origen, Destino, Método, Endpoint y DTO

### Fase 2 — DTOs
- [x] Cada microservicio tiene paquete `dto/` con DTOs de entrada y salida
- [x] Los controllers devuelven `ResponseEntity<DTO>` — nunca entidades JPA

### Fase 3 — Feign Client
- [x] `ms-prestamos`: `@EnableFeignClients` + `PerfilClient` + `InventarioClient`
- [x] `ms-devoluciones`: `@EnableFeignClients` + `PrestamoClient`
- [x] URLs en `application.properties` con variables de entorno `${VARIABLE:default}`
- [x] URLs inyectadas desde `docker-compose.yml` para no hardcodear

### Fase 4 — Timeouts, errores y logs
- [x] `connectTimeout=3000ms`, `readTimeout=5000ms` en servicios con Feign
- [x] `FeignException.NotFound` → HTTP 404 controlado
- [x] `FeignException` genérica → HTTP 503 controlado
- [x] `@RestControllerAdvice` en los **10 microservicios** (maneja 404, 503 y 400)
- [x] `MethodArgumentNotValidException` → HTTP 400 con detalle de campos
- [x] Logs SLF4J en cada llamada externa (antes, después y al fallar)

### Fase 5 — Pruebas Postman
- [x] Colección `postman/hito2-integracion.json` en el repositorio
- [x] Flujo éxito E2E documentado
- [x] Flujo resiliencia (servicio caído) documentado

### Infraestructura Docker
- [x] 10 Dockerfiles de 2 etapas (Maven build + JRE runtime ligero)
- [x] 10 BDs MySQL con `healthcheck` configurado
- [x] 10 microservicios con `depends_on: condition: service_healthy`
- [x] Variables de entorno inyectadas desde `docker-compose.yml`
- [x] Red única `red_interna_proyecto` tipo bridge para comunicación interna
- [x] Volúmenes de persistencia nombrados para cada BD

---

## 🛠️ Comandos útiles en EC2

```bash
# Levantar sistema completo
docker compose up -d --build

# Ver estado de todos los contenedores
docker ps

# Ver logs en tiempo real de un servicio
docker logs -f ms-prestamos

# Detener todo el sistema
docker compose down

# Reiniciar un solo servicio
docker compose restart ms-prestamos

# Ver red interna Docker
docker network inspect biblioteca-microservicios_red_interna_proyecto
```

---

*Desarrollo FullStack 1 — DSY1103 · Duoc UC 2026 · Profesor Michael Catalán*
