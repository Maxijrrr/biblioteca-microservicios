# Sistema de Gestion Bibliotecaria - EP3 Semana 15

Sistema de gestion bibliotecaria construido con arquitectura de microservicios, Spring Boot, Docker Compose y MySQL. El proyecto aplica el patron Database per Service: cada microservicio tiene su propia base de datos y no accede directamente a la base de otro servicio.

Repositorio: https://github.com/Maxijrrr/biblioteca-microservicios

---

## Integrantes

| Nombre | Responsabilidad |
|:--|:--|
| Maximiliano Valenzuela | ms-autenticador, ms-ebooks, ms-prestamos |
| Genesis Cerda | ms-perfiles, ms-inventario, ms-penalizaciones |
| Vicente Hueichapan | ms-catalogo, ms-devoluciones, ms-reservas, ms-sucursales |

---

## Estado del Sistema

| Microservicio | Puerto app | Base de datos | Puerto DB local | Responsabilidad |
|:--|:--:|:--|:--:|:--|
| ms-autenticador | 8080 | db_autenticador | 3306 | Registro, login y gestion de credenciales |
| ms-catalogo | 8081 | db_catalogo | 3307 | Catalogo de libros |
| ms-devoluciones | 8082 | db_devoluciones | 3308 | Devoluciones de prestamos |
| ms-ebooks | 8083 | db_ebooks | 3309 | Libros digitales |
| ms-inventario | 8084 | db_inventario | 3310 | Stock por ISBN y sucursal |
| ms-penalizaciones | 8085 | db_penalizaciones | 3311 | Penalizaciones por retrasos o danos |
| ms-perfiles | 8086 | db_perfiles | 3312 | Perfiles de usuarios |
| ms-prestamos | 8087 | db_prestamos | 3313 | Prestamos de libros |
| ms-reservas | 8088 | db_reservas | 3314 | Reservas |
| ms-sucursales | 8089 | db_sucursales | 3315 | Sucursales fisicas |
| api-gateway | 8090 | No aplica | No aplica | Enrutamiento HTTP hacia microservicios |

---

## Checklist EP3 Semana 15

| Requisito docente | Estado en repositorio |
|:--|:--:|
| Pruebas de modelo, servicio, controlador y repositorio en 5 microservicios | Cumplido |
| Controlador con `MockMvc.standaloneSetup`, HTTP 200, 201 y 404 | Cumplido |
| Repositorio con `@DataJpaTest`, H2, `save`, `findById` y `findAll` | Cumplido |
| `./mvnw test` ejecutable por microservicio | Cumplido |
| `springdoc-openapi-starter-webmvc-ui` en los 5 microservicios evaluados | Cumplido |
| Swagger UI en `/swagger-ui/index.html` | Cumplido |
| Endpoints principales con `@Operation` y minimo 2 `@ApiResponse` | Cumplido |
| Modulo `api-gateway` con `pom.xml` | Cumplido |
| `application.yml` del Gateway con rutas hacia minimo 3 microservicios | Cumplido |
| README con integrantes, microservicios, Swagger e instrucciones | Cumplido |
| PPT minimo 11 diapositivas y capturas reales | Pendiente de generar |

---

## Arquitectura Database per Service

Cada servicio es dueno exclusivo de su base de datos. La comunicacion entre dominios se realiza por HTTP/REST mediante clientes declarativos Feign, nunca por acceso directo a tablas externas.

```text
ms-autenticador   -> db_autenticador
ms-catalogo       -> db_catalogo
ms-devoluciones   -> db_devoluciones
ms-ebooks         -> db_ebooks
ms-inventario     -> db_inventario
ms-penalizaciones -> db_penalizaciones
ms-perfiles       -> db_perfiles
ms-prestamos      -> db_prestamos
ms-reservas       -> db_reservas
ms-sucursales     -> db_sucursales
```

Docker Compose define 20 contenedores en total: 10 aplicaciones Spring Boot y 10 bases de datos MySQL independientes. Todos se conectan a la red interna `red_interna_proyecto`.

---

## Comunicacion Entre Microservicios

### Flujos implementados con OpenFeign

```mermaid
graph TD
    Prestamos["ms-prestamos :8087"]
    Perfiles["ms-perfiles :8086"]
    Inventario["ms-inventario :8084"]
    Devoluciones["ms-devoluciones :8082"]

    Prestamos -->|"GET /api/perfiles/{id}"| Perfiles
    Prestamos -->|"GET /api/inventario/isbn/{isbn}"| Inventario
    Devoluciones -->|"GET /api/v1/prestamos/{id}"| Prestamos
```

### Tabla de contratos

| Origen | Destino | Metodo | Endpoint | DTO esperado | Justificacion |
|:--|:--|:--:|:--|:--|:--|
| ms-prestamos | ms-perfiles | GET | `/api/perfiles/{id}` | `PerfilDTO` | Validar que el perfil exista antes de crear un prestamo. |
| ms-prestamos | ms-inventario | GET | `/api/inventario/isbn/{isbn}` | Lista de stock inventario | Validar stock disponible antes de crear un prestamo. |
| ms-devoluciones | ms-prestamos | GET | `/api/v1/prestamos/{id}` | `PrestamoDTO` | Confirmar que el prestamo exista y este `ACTIVO` antes de registrar la devolucion. |

### Configuracion tecnica

| Elemento | Implementacion |
|:--|:--|
| Cliente REST | OpenFeign (`spring-cloud-starter-openfeign`) |
| Descubrimiento en Docker | URLs por nombre de contenedor: `http://ms-servicio:puerto` |
| Timeouts Feign | `connectTimeout=3000ms`, `readTimeout=5000ms` |
| Errores controlados | `@RestControllerAdvice` y excepciones personalizadas |
| Logs | SLF4J en llamadas externas, validaciones y fallos |
| Arranque DB | `healthcheck`, `depends_on: condition: service_healthy` y tolerancia Hikari |

---

## Swagger / OpenAPI - EP3

Los cinco microservicios evaluados para EP3 tienen Springdoc OpenAPI agregado en su `pom.xml` y exponen Swagger UI en `/swagger-ui/index.html`. Los controladores principales documentan sus endpoints con `@Operation(summary = "...")` y `@ApiResponses`.

| Microservicio | URL Swagger local |
|:--|:--|
| ms-autenticador | `http://localhost:8080/swagger-ui/index.html` |
| ms-catalogo | `http://localhost:8081/swagger-ui/index.html` |
| ms-devoluciones | `http://localhost:8082/swagger-ui/index.html` |
| ms-prestamos | `http://localhost:8087/swagger-ui/index.html` |
| ms-reservas | `http://localhost:8088/swagger-ui/index.html` |
| ms-sucursales | `http://localhost:8089/swagger-ui/index.html` |

Ejemplo de ejecucion para revisar Swagger de un servicio:

```bash
cd codigo-fuente/ms-catalogo
./mvnw spring-boot:run
```

Luego abrir `http://localhost:8081/swagger-ui/index.html`.

---

## API Gateway - Preparacion Examen

El modulo `codigo-fuente/api-gateway` fue creado como entrada unica HTTP para enrutar hacia los microservicios evaluados. Escucha en el puerto `8090` y usa rutas por path.

| Ruta Gateway | Destino local |
|:--|:--|
| `/api/catalogo/**` | `http://localhost:8081` |
| `/api/devoluciones/**` | `http://localhost:8082` |
| `/api/v1/prestamos/**` | `http://localhost:8087` |
| `/api/reservas/**` | `http://localhost:8088` |
| `/api/sucursales/**` | `http://localhost:8089` |

Ejecucion del Gateway:

```bash
cd codigo-fuente/api-gateway
./mvnw spring-boot:run
```

Ejemplo de llamada a traves del Gateway:

```bash
curl http://localhost:8090/api/catalogo/libros
```

---

## Endpoints Principales Para Hito 2

### Crear perfil

```bash
curl -X POST http://localhost:8086/api/perfil \
  -H "Content-Type: application/json" \
  -d '{"rut":"12345678-9","nombre":"Juan Perez","correo":"juan@duoc.cl","carrera":"Informatica"}'
```

### Crear stock

```bash
curl -X POST http://localhost:8084/api/inventario \
  -H "Content-Type: application/json" \
  -d '{"isbn":"978-3-16","idSucursal":1,"stockTotal":5,"stockDisponible":5}'
```

### Crear prestamo E2E

```bash
curl -X POST http://localhost:8087/api/v1/prestamos \
  -H "Content-Type: application/json" \
  -d '{"idPerfil":1,"isbn":"978-3-16"}'
```

Resultado esperado: `ms-prestamos` consulta a `ms-perfiles` y `ms-inventario`; si ambas validaciones son correctas, crea un prestamo con estado `ACTIVO`.

### Crear devolucion E2E

```bash
curl -X POST http://localhost:8082/api/devoluciones \
  -H "Content-Type: application/json" \
  -d '{"idPrestamo":1}'
```

Resultado esperado: `ms-devoluciones` consulta a `ms-prestamos`; si el prestamo existe y esta `ACTIVO`, registra la devolucion.

---

## Pruebas Unitarias - Hito 3

Se integraron pruebas unitarias reutilizables por capas en los cinco microservicios exigidos por la rubrica:

| Microservicio | Modelo | Repositorio | Servicio | Controlador | Contexto | Total Maven |
|:--|--:|--:|--:|--:|--:|--:|
| ms-catalogo | 3 | 4 | 5 | 4 | 1 | 17 |
| ms-sucursales | 3 | 3 | 5 | 4 | 1 | 16 |
| ms-reservas | 3 | 3 | 6 | 4 | 1 | 17 |
| ms-devoluciones | 3 | 3 | 5 | 4 | 1 | 16 |
| ms-prestamos | 4 | 4 | 5 | 5 | 1 | 19 |
| ms-autenticador | 3 | 4 | 6 | 4 | 1 | 18 |

Total verificado: 85 tests, 0 fallas, 0 errores.

La columna `Contexto` corresponde al `contextLoads` generado por Spring Boot. El cumplimiento de las cuatro capas se acredita con los tests de modelo, repositorio, servicio y controlador, sin depender de ese test adicional.

Herramientas usadas:

- JUnit 5 para modelo y reglas de negocio.
- Mockito para aislar servicios y clientes Feign.
- MockMvc para controladores REST.
- `@DataJpaTest` con H2 para repositorios.
- Maven para ejecucion repetible.

### Que valida cada clase y por que cumple

Todos los metodos usan nombres descriptivos y estructura Arrange / Act / Assert. Los servicios se prueban aislados con Mockito, los controladores con `MockMvcBuilders.standaloneSetup` y los repositorios contra H2 mediante `@DataJpaTest`.

| Microservicio | Clase | Tests | Que valida | Por que cumple |
|:--|:--|--:|:--|:--|
| ms-catalogo | `LibroTest` | 3 | Constructor vacio y completo, getters/setters, `equals` y `hashCode`. | Cubre la entidad JPA con JUnit 5 puro. |
| ms-catalogo | `LibroRepositoryTest` | 4 | `save`, `findById`, `findAll` y busqueda por ISBN sobre H2. | Ejecuta CRUD SQL real y una consulta personalizada con `@DataJpaTest`. |
| ms-catalogo | `LibroServiceImplTest` | 5 | Listado, busqueda exitosa, recurso inexistente, guardado y eliminacion. | Aisla la logica con `@Mock` e `@InjectMocks` y verifica errores controlados. |
| ms-catalogo | `LibroControllerTest` | 4 | Respuestas HTTP 200, 201, 404 y 400 con JSON. | Prueba el contrato REST con MockMvc standalone. |
| ms-sucursales | `SucursalTest` | 3 | Constructor vacio y completo, getters/setters, `equals` y `hashCode`. | Cubre la entidad JPA con JUnit 5 puro. |
| ms-sucursales | `SucursalRepositoryTest` | 3 | `save`, `findById` y `findAll` sobre H2. | Cumple el minimo CRUD real exigido para repositorio. |
| ms-sucursales | `SucursalServiceImplTest` | 5 | Listado, busqueda, 404 logico, guardado y eliminacion. | Valida la logica de negocio sin base de datos real mediante Mockito. |
| ms-sucursales | `SucursalControllerTest` | 4 | Respuestas HTTP 200, 201, 404 y 400. | Verifica estados y validacion del controlador con MockMvc standalone. |
| ms-reservas | `ReservaTest` | 3 | Constructor vacio y completo, getters/setters, `equals` y `hashCode`. | Cubre la entidad JPA con JUnit 5 puro. |
| ms-reservas | `ReservaRepositoryTest` | 3 | `save`, `findById` y `findAll` sobre H2. | Cumple las operaciones CRUD reales solicitadas. |
| ms-reservas | `ReservaServiceImplTest` | 6 | Listado, busqueda, guardado, eliminacion y errores por inexistencia. | Prueba casos felices y excepciones con repositorio mockeado. |
| ms-reservas | `ReservaControllerTest` | 4 | Respuestas HTTP 200, 201, 404 y 400. | Valida el contrato REST y el cuerpo procesado sin levantar servidor. |
| ms-devoluciones | `DevolucionTest` | 3 | Constructor vacio y completo, getters/setters, `equals` y `hashCode`. | Cubre la entidad JPA con JUnit 5 puro. |
| ms-devoluciones | `DevolucionRepositoryTest` | 3 | `save`, `findById` y `findAll` sobre H2. | Verifica persistencia SQL aislada con `@DataJpaTest`. |
| ms-devoluciones | `DevolucionServiceImplTest` | 5 | Prestamo activo, prestamo no activo, busqueda, 404 logico y eliminacion. | Aisla repositorio y cliente Feign con Mockito para probar la regla de negocio. |
| ms-devoluciones | `DevolucionControllerTest` | 4 | Respuestas HTTP 200, 201, 404 y 400. | Verifica endpoints y validacion con MockMvc standalone. |
| ms-prestamos | `PrestamoTest` | 4 | Constructores, setters/getters, campos nulos, `equals` y `hashCode`. | Supera el minimo de pruebas de modelo con JUnit 5 puro. |
| ms-prestamos | `PrestamoRepositoryTest` | 4 | `save`, `findById`, `findAll` y consultas por ISBN/prestamos vencidos. | Verifica SQL real, CRUD minimo exigido y consultas de dominio sobre H2. |
| ms-prestamos | `PrestamoServiceTest` | 5 | Perfil valido, stock disponible, falta de stock, busqueda y listado. | Mockea repositorio y clientes Feign para aislar la logica principal. |
| ms-prestamos | `PrestamoControllerTest` | 5 | Respuestas HTTP 200, 201, 404 y 400, incluido renovar prestamo. | Prueba estados y JSON del API con MockMvc standalone. |

Si una regla productiva se rompe, los asserts dejan de coincidir, las verificaciones Mockito detectan interacciones incorrectas o MockMvc devuelve un estado/cuerpo distinto; Maven termina entonces con `BUILD FAILURE`. Por ello no son tests vacios ni asserts que siempre pasan.

### Verificacion final en EC2

El 2026-06-15 se ejecutaron los cinco comandos de prueba uno por uno en la instancia EC2, con Java 21.0.10 y Maven 3.8.7. Los cinco finalizaron con `BUILD SUCCESS`: 85 tests, 0 fallas y 0 errores. No se levantaron aplicaciones Spring para esta verificacion.

El 2026-06-18 se repitio verificacion local con Java 21.0.10 y Maven 3.9.12 despues de integrar Swagger y Gateway. Los cinco microservicios finalizaron con `BUILD SUCCESS` y el `api-gateway` finalizo con `BUILD SUCCESS` adicional. Para la entrega final de Semana 15 se usa `./mvnw test` como comando estandar de Linux/EC2.

Comandos de ejecucion:

```powershell
cd codigo-fuente/ms-catalogo
./mvnw test

cd ../ms-sucursales
./mvnw test

cd ../ms-reservas
./mvnw test

cd ../ms-devoluciones
./mvnw test

cd ../ms-prestamos
./mvnw test

cd ../api-gateway
./mvnw test
```

Documentacion del hito:

```text
TESTING_PLAN.md
evidencias/testing/comandos-ejecucion.md
evidencias/testing/resumen-cobertura.md
```

Para la evidencia visual de la presentacion, abrir en VS Code el arbol `src/test/java` y ejecutar `./mvnw test` en terminal. La captura debe mostrar `Failures: 0`, `Errors: 0` y `BUILD SUCCESS`.

---

## Pruebas Empiricas Realizadas

Por limitacion de recursos locales, las pruebas se ejecutaron de forma incremental: se levantaron solo los microservicios necesarios para cada escenario y luego se apagaron antes de continuar. Esta estrategia evita sobrecargar el equipo y mantiene las pruebas controladas.

| Area | Estado | Evidencia |
|:--|:--:|:--|
| ms-ebooks + db-ebooks | Probado | Crear, listar, buscar, prestar, devolver y eliminar ebook. |
| ms-catalogo + db-catalogo | Probado | CRUD completo y correccion de tolerancia de arranque DB. |
| ms-inventario + db-inventario | Probado | Crear stock, buscar por ISBN/sucursal, actualizar, eliminar y 404 esperado. |
| ms-sucursales + db-sucursales | Probado | CRUD completo y 404 esperado. |
| ms-penalizaciones + db-penalizaciones | Probado | Crear, buscar por perfil/estado, actualizar, eliminar y 404 esperado. |
| ms-autenticador + db-autenticador | Probado | Registrar, login, consultar perfil, cambiar password y login nuevo. |
| ms-reservas + db-reservas | Probado | Crear, listar, actualizar, eliminar y 404 esperado. |
| Flujo prestamos | Probado | `ms-prestamos -> ms-perfiles` y `ms-prestamos -> ms-inventario`. |
| Flujo devoluciones | Probado | `ms-devoluciones -> ms-prestamos`. |
| Resiliencia inventario caido | Probado | `ms-prestamos` responde HTTP 503 controlado. |
| Resiliencia prestamos caido | Probado | `ms-devoluciones` responde HTTP 503 controlado. |

Logs observados durante el flujo de prestamos:

```text
Consultando ms-perfiles...
Perfil validado correctamente
Consultando ms-inventario...
Stock validado correctamente
Prestamo guardado exitosamente
```

Logs observados durante el flujo de devoluciones:

```text
Consultando ms-prestamos para validar prestamo
Prestamo validado correctamente
```

---

## Ejecucion Local Segura

Para equipos con recursos limitados, no es obligatorio levantar todo el sistema al mismo tiempo. Se recomienda probar por subconjuntos:

```bash
# Ejemplo: probar prestamos con sus dependencias
docker compose up -d --build db-perfiles db-inventario db-prestamos ms-perfiles ms-inventario ms-prestamos

# Ver estado
docker ps

# Apagar el subconjunto probado
docker compose stop ms-prestamos ms-inventario ms-perfiles db-prestamos db-inventario db-perfiles
```

Para levantar todo el sistema en una maquina con recursos suficientes:

```bash
docker compose up -d --build
```

Para apagar todo:

```bash
docker compose down
```

---

## Despliegue En AWS EC2

El proyecto esta documentado para el Escenario A: todos los servicios en una sola instancia EC2 usando Docker Compose.

Recomendacion minima:

- Ubuntu 24.04
- Docker y Docker Compose instalados
- Instancia con memoria suficiente para 20 contenedores
- Puertos abiertos segun necesidad de evaluacion: 8080 a 8089

Comandos base:

```bash
git clone https://github.com/Maxijrrr/biblioteca-microservicios
cd biblioteca-microservicios
docker compose up -d --build
docker ps
```

Si la instancia EC2 es pequena, aplicar la misma estrategia local: levantar solo los subconjuntos requeridos para cada flujo y apagarlos al terminar.

---

## Estructura Del Proyecto

```text
biblioteca-microservicios/
|-- docker-compose.yml
|-- README.md
|-- postman/
|   `-- hito2-integracion.json
`-- codigo-fuente/
    |-- api-gateway/
    |-- ms-autenticador/
    |-- ms-catalogo/
    |-- ms-devoluciones/
    |-- ms-ebooks/
    |-- ms-inventario/
    |-- ms-penalizaciones/
    |-- ms-perfiles/
    |-- ms-prestamos/
    |-- ms-reservas/
    `-- ms-sucursales/
```

Cada microservicio mantiene la estructura esperada:

```text
controller/
service/
repository/
model/
dto/
exception/
src/main/resources/application.properties
Dockerfile
pom.xml
```

---

## Checklist Hito 2

| Item | Estado |
|:--|:--:|
| 10 microservicios Spring Boot definidos | Cumplido |
| 10 bases de datos MySQL independientes | Cumplido |
| Patron Database per Service | Cumplido |
| Docker Compose con red interna unica | Cumplido |
| Healthchecks para MySQL | Cumplido |
| `depends_on` condicionado por salud de DB | Cumplido |
| DTOs de entrada/salida en los servicios | Cumplido |
| Feign Client en ms-prestamos | Cumplido |
| Feign Client en ms-devoluciones | Cumplido |
| Dos flujos E2E interconectados | Cumplido y probado |
| Timeouts Feign 3000ms/5000ms | Cumplido |
| Manejo global de excepciones | Cumplido |
| Respuestas 404/400/503 controladas | Cumplido |
| Logs SLF4J en llamadas externas | Cumplido |
| README tecnico actualizado | Cumplido |
| Coleccion Postman incluida | Cumplido |
| Pruebas empiricas por subconjuntos | Cumplido |
| Preparado para GitHub y EC2 | Cumplido |

---

## Coleccion Postman

La coleccion de pruebas se encuentra en:

```text
postman/hito2-integracion.json
```

Incluye endpoints para CRUD y pruebas E2E de comunicacion entre servicios.

---

## Notas De Ingenieria

- El sistema evita compartir bases de datos entre microservicios.
- Los servicios se comunican por APIs internas, no por tablas externas.
- Las URLs externas se inyectan con variables de entorno desde Docker Compose.
- Se agrego tolerancia de conexion Hikari para evitar fallos por arranque lento de MySQL.
- Las pruebas locales se realizaron por subconjuntos para proteger recursos del equipo.
- En EC2 se puede repetir la misma estrategia o levantar todo si la instancia tiene memoria suficiente.

---

Desarrollo FullStack 1 - DSY1103 - Duoc UC 2026
