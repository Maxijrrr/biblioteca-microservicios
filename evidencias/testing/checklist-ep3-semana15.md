# Checklist EP3 Semana 15

| Item solicitado por el docente | Evidencia en el repositorio | Estado |
|:--|:--|:--:|
| Tests de modelo en 5 microservicios | `src/test/java/**/model/*Test.java` | Cumplido |
| Tests de servicio en 5 microservicios | `src/test/java/**/service/*Test.java` | Cumplido |
| Tests de controlador en 5 microservicios | `src/test/java/**/controller/*ControllerTest.java` | Cumplido |
| Tests de repositorio en 5 microservicios | `src/test/java/**/repository/*RepositoryTest.java` | Cumplido |
| Controlador con MockMvc standalone | `MockMvcBuilders.standaloneSetup(...)` | Cumplido |
| HTTP 200, 201 y 404 en controladores | `status().isOk()`, `isCreated()`, `isNotFound()` | Cumplido |
| Repositorios con H2 y `@DataJpaTest` | `@DataJpaTest` y dependencia H2 | Cumplido |
| Repositorios prueban `save`, `findById`, `findAll` | Clases `*RepositoryTest.java` | Cumplido |
| `springdoc-openapi-starter-webmvc-ui` | `pom.xml` de los 5 servicios evaluados | Cumplido |
| Swagger UI en `/swagger-ui/index.html` | Endpoint estandar de Springdoc OpenAPI | Cumplido |
| Endpoints con `@Operation` y `@ApiResponse` | Controladores principales | Cumplido |
| Modulo `api-gateway` creado | `codigo-fuente/api-gateway` | Cumplido |
| Gateway con rutas hacia minimo 3 servicios | 5 rutas en `application.yml` | Cumplido |
| README actualizado | `README.md` | Cumplido |
| PPT minimo 11 diapositivas | `evidencias/testing/Presentacion_EP3_Semana15_Pruebas_Swagger_Gateway.pptx` | Pendiente de generar |
| Capturas reales legibles | `evidencias/testing/capturas/` | Pendiente de renovar |
