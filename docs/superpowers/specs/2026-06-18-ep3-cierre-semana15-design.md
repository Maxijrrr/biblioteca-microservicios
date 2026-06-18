# EP3 Cierre Semana 15 - Design

## Objetivo

Cerrar el entregable EP3 de DSY1103 antes del domingo 21 de junio a las 23:59, dejando el repositorio evaluable con pruebas unitarias, Swagger, API Gateway, README, evidencia y PPT alineados con la rubrica entregada por el docente.

## Alcance estricto

Se trabajara solo sobre lo que pide la rubrica de Semana 15:

- Pruebas unitarias en las cuatro capas de los cinco microservicios evaluados: `ms-catalogo`, `ms-sucursales`, `ms-reservas`, `ms-devoluciones` y `ms-prestamos`.
- Swagger/OpenAPI en esos cinco microservicios, disponible en `/swagger-ui/index.html`.
- Anotaciones `@Operation` y al menos dos `@ApiResponse` en endpoints principales.
- Modulo `api-gateway` con `pom.xml` y `application.yml`.
- Rutas del Gateway hacia al menos tres microservicios.
- README actualizado con integrantes, microservicios, links Swagger, Gateway e instrucciones de ejecucion.
- Evidencia y PPT minimo de 11 diapositivas para la presentacion de Semana 16.
- Verificacion empirica con `./mvnw test` por microservicio, uno por uno.

No se agregaran funcionalidades de negocio nuevas, autenticacion extra, descubrimiento de servicios ni despliegue complejo fuera de lo pedido.

## Estado base auditado

Las pruebas unitarias ya existen en los cinco microservicios evaluados, con cuatro clases por servicio:

- Modelo: JUnit 5 puro.
- Servicio: Mockito.
- Controlador: `MockMvcBuilders.standaloneSetup(...)`.
- Repositorio: `@DataJpaTest` con H2.

La auditoria local confirmo:

- Controladores cubren HTTP 200, 201 y 404.
- Repositorios prueban `save`, `findById` y `findAll`.
- Conteo documentado: 85 tests totales.

## Diseno tecnico

### Swagger/OpenAPI

Cada microservicio evaluado incorporara:

- Dependencia `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0`.
- Configuracion de propiedades para exponer Swagger UI en `/swagger-ui/index.html`.
- Anotaciones OpenAPI en el controlador principal:
  - `@Operation(summary = "...")`.
  - `@ApiResponses` con respuestas exitosas y errores relevantes.

Se documentaran endpoints existentes sin cambiar su contrato real.

### API Gateway

Se creara `codigo-fuente/api-gateway` como aplicacion Spring Boot independiente con Spring Cloud Gateway MVC. El modulo usara Java 21 y Spring Boot 3.2.5, consistente con los microservicios.

El Gateway escuchara en el puerto `8090` y tendra rutas por path hacia al menos:

- `/api/catalogo/**` -> `http://localhost:8081`
- `/api/devoluciones/**` -> `http://localhost:8082`
- `/api/v1/prestamos/**` -> `http://localhost:8087`
- `/api/reservas/**` -> `http://localhost:8088`
- `/api/sucursales/**` -> `http://localhost:8089`

Se documentan cinco rutas aunque la rubrica exige minimo tres, porque corresponden exactamente a los cinco microservicios presentados en EP3.

### Documentacion y evidencia

El README sera la fuente principal para evaluacion asincronica:

- Checklist EP3 Semana 15.
- Tabla de Swagger por servicio.
- Tabla de rutas Gateway.
- Instrucciones de `./mvnw test`.
- Comando recomendado de commit final.

La carpeta `evidencias/testing` mantendra resumen y comandos. Las capturas finales deben ser reales y legibles; las capturas se renovaran con los dos servicios escogidos para presentacion: `ms-prestamos` y `ms-catalogo`.

### PPT

Se preparara un PPT editable de minimo 11 diapositivas:

1. Portada.
2. Arquitectura.
3. Arbol de tests de `ms-prestamos`.
4. Codigo de tests de `ms-prestamos`.
5. `BUILD SUCCESS` de `ms-prestamos`.
6. Arbol de tests de `ms-catalogo`.
7. Codigo de tests de `ms-catalogo`.
8. `BUILD SUCCESS` de `ms-catalogo`.
9. Tabla de cobertura.
10. Swagger.
11. API Gateway.

## Verificacion

La entrega solo se considerara lista con evidencia fresca de:

- `./mvnw test` en cada uno de los cinco microservicios.
- Compilacion o test basico del `api-gateway`.
- Confirmacion de archivos Swagger/Gateway.
- Estado Git limpio salvo archivos deliberadamente versionados.
- Push final a GitHub y confirmacion de hash remoto.
- Pull o estado actualizado en EC2 `3.91.61.39` cuando haya conectividad.
