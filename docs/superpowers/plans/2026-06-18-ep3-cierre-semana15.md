# EP3 Cierre Semana 15 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Complete the EP3 Semana 15 repository requirements: tests, Swagger, API Gateway, README, evidence, PPT, GitHub and EC2 verification.

**Architecture:** Keep each microservice independent and only add OpenAPI documentation to its existing REST controller. Add a standalone Spring Boot API Gateway module under `codigo-fuente/api-gateway` with path-based routes to the evaluated services.

**Tech Stack:** Java 21, Spring Boot 3.2.5, Springdoc OpenAPI 2.5.0, Spring Cloud Gateway MVC 2023.0.3, JUnit 5, Mockito, MockMvc, H2, Maven Wrapper.

---

### Task 1: Swagger Dependencies And Properties

**Files:**
- Modify: `codigo-fuente/ms-catalogo/pom.xml`
- Modify: `codigo-fuente/ms-sucursales/pom.xml`
- Modify: `codigo-fuente/ms-reservas/pom.xml`
- Modify: `codigo-fuente/ms-devoluciones/pom.xml`
- Modify: `codigo-fuente/ms-prestamos/pom.xml`
- Modify: each service `src/main/resources/application.properties`

- [ ] Add `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0` to each evaluated service.
- [ ] Add `springdoc.swagger-ui.path=/swagger-ui/index.html`.
- [ ] Keep existing ports and database settings unchanged.
- [ ] Run one Maven test command after the changes compile.

### Task 2: Controller OpenAPI Annotations

**Files:**
- Modify: `codigo-fuente/ms-catalogo/src/main/java/cl/duoc/ms_catalogo/controller/LibroController.java`
- Modify: `codigo-fuente/ms-sucursales/src/main/java/cl/duoc/ms_sucursales/controller/SucursalController.java`
- Modify: `codigo-fuente/ms-reservas/src/main/java/cl/duoc/ms_reservas/controller/ReservaController.java`
- Modify: `codigo-fuente/ms-devoluciones/src/main/java/cl/duoc/ms_devoluciones/controller/DevolucionController.java`
- Modify: `codigo-fuente/ms-prestamos/src/main/java/cl/duoc/ms_prestamos/controller/PrestamoController.java`

- [ ] Import `Operation`, `ApiResponse` and `ApiResponses`.
- [ ] Add summaries to list, get by id and create endpoints.
- [ ] Add response documentation for success, validation failure and not found where applicable.
- [ ] Do not change response bodies, status codes or business behavior.

### Task 3: API Gateway Module

**Files:**
- Create: `codigo-fuente/api-gateway/pom.xml`
- Create: `codigo-fuente/api-gateway/mvnw`
- Create: `codigo-fuente/api-gateway/mvnw.cmd`
- Create: `codigo-fuente/api-gateway/src/main/java/cl/duoc/api_gateway/ApiGatewayApplication.java`
- Create: `codigo-fuente/api-gateway/src/main/resources/application.yml`
- Create: `codigo-fuente/api-gateway/src/test/java/cl/duoc/api_gateway/ApiGatewayApplicationTests.java`

- [ ] Use Spring Boot 3.2.5 and Spring Cloud 2023.0.3.
- [ ] Configure Gateway port `8090`.
- [ ] Configure routes for catalogo, devoluciones, prestamos, reservas and sucursales.
- [ ] Add a minimal `contextLoads` test.
- [ ] Verify the Gateway with `./mvnw test`.

### Task 4: README And Evidence Text

**Files:**
- Modify: `README.md`
- Modify: `evidencias/testing/comandos-ejecucion.md`
- Modify: `evidencias/testing/resumen-cobertura.md`

- [ ] Replace outdated `mvn test` references for EP3 with `./mvnw test`.
- [ ] Add Swagger links for the five evaluated services.
- [ ] Add Gateway routes and execution instructions.
- [ ] Add a checklist matching the Semana 15 rubric.
- [ ] Correct the `ms-prestamos` repository description to mention `findAll`.

### Task 5: PPT And Captures

**Files:**
- Create or update: `evidencias/testing/Presentacion_EP3_Semana15_Pruebas_Swagger_Gateway.pptx`
- Update: `evidencias/testing/capturas/*`

- [ ] Use `ms-prestamos` and `ms-catalogo` as detailed services.
- [ ] Include at least 11 slides in the required order.
- [ ] Use real readable captures for tests, terminal, Swagger and Gateway.
- [ ] Render and inspect slides before final export.

### Task 6: Final Verification, Commit And Push

**Commands:**
- Run `./mvnw -Dstyle.color=never test` in each evaluated service.
- Run `./mvnw -Dstyle.color=never test` in `api-gateway`.
- Push a descriptive final commit.
- Confirm GitHub remote hash.
- Pull on EC2 `ubuntu@3.91.61.39` and run the same service tests one by one if connectivity allows.

- [ ] No completion claim without fresh command output.
- [ ] Final commit must happen before Sunday 21 June 23:59.
