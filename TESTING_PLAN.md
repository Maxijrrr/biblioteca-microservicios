# Plan de Pruebas Unitarias - Hito 3

## Objetivo

Integrar pruebas unitarias reutilizables por capas en cinco microservicios del sistema de biblioteca, manteniendo una estructura clara, repetible y defendible para evaluacion academica.

## Microservicios Incluidos

| Microservicio | Motivo |
|:--|:--|
| ms-catalogo | CRUD base de libros y repositorio con consulta por ISBN. |
| ms-sucursales | CRUD simple para demostrar patron por capas. |
| ms-reservas | CRUD con regla de eliminacion de recurso inexistente. |
| ms-devoluciones | Regla de negocio con cliente Feign mockeado hacia prestamos. |
| ms-prestamos | Servicio principal con Feign mockeado hacia perfiles e inventario. |

## Estructura Reutilizable

Cada microservicio trabajado mantiene el mismo formato:

```text
src/test/java/cl/duoc/ms_xxx/
|-- controller/
|   `-- XxxControllerTest.java
|-- model/
|   `-- XxxTest.java
|-- repository/
|   `-- XxxRepositoryTest.java
`-- service/
    `-- XxxServiceImplTest.java
```

En `ms-prestamos`, el servicio no usa paquete `impl`, por eso la clase se llama `PrestamoServiceTest`.

## Criterios Por Capa

| Capa | Herramienta | Criterio probado |
|:--|:--|:--|
| Modelo | JUnit 5 | Constructor vacio, constructor completo, getters/setters, equals/hashCode. |
| Repositorio | `@DataJpaTest` + H2 | `save`, `findById`, `findAll` y consultas personalizadas. |
| Servicio | JUnit 5 + Mockito | Casos felices, errores controlados y reglas de negocio. |
| Controlador | MockMvc standalone | HTTP 200/201, HTTP 400 por validacion y HTTP 404 controlado. |

## Dependencias De Prueba

Los cinco servicios usan:

- `spring-boot-starter-test`
- `h2` con `scope=test`
- `src/test/resources/application.properties` con base H2 en memoria

Se unifico `ms-catalogo`, `ms-sucursales` y `ms-reservas` a Spring Boot `3.2.5`, igual que `ms-prestamos` y `ms-devoluciones`, para mantener compatibilidad y ejecucion consistente.

## Resultados Verificados

Verificacion final ejecutada el 2026-06-15 en EC2 con Java 21.0.10 y Maven 3.8.7. Cada microservicio se probo por separado:

| Microservicio | Tests | Fallas | Errores | Estado |
|:--|--:|--:|--:|:--:|
| ms-catalogo | 17 | 0 | 0 | BUILD SUCCESS |
| ms-sucursales | 16 | 0 | 0 | BUILD SUCCESS |
| ms-reservas | 17 | 0 | 0 | BUILD SUCCESS |
| ms-devoluciones | 16 | 0 | 0 | BUILD SUCCESS |
| ms-prestamos | 19 | 0 | 0 | BUILD SUCCESS |

Total verificado: 85 tests, 0 fallas, 0 errores.

## Comandos De Ejecucion

Desde la raiz del repositorio:

```powershell
cd codigo-fuente/ms-catalogo
mvn test

cd ../ms-sucursales
mvn test

cd ../ms-reservas
mvn test

cd ../ms-devoluciones
mvn test

cd ../ms-prestamos
mvn test
```

En equipos sin Maven global, tambien puede usarse el wrapper del servicio si funciona en el entorno:

```powershell
.\mvnw.cmd test
```

En Linux o EC2:

```bash
cd codigo-fuente/ms-catalogo && mvn test
cd ../ms-sucursales && mvn test
cd ../ms-reservas && mvn test
cd ../ms-devoluciones && mvn test
cd ../ms-prestamos && mvn test
```

## Evidencia Recomendada Para Presentacion

Para dos microservicios, se recomienda capturar:

1. Arbol `src/test/java` mostrando `controller`, `model`, `repository`, `service`.
2. Una clase de servicio con `@ExtendWith(MockitoExtension.class)`, `@Mock`, `@InjectMocks`.
3. Una clase de repositorio con `@DataJpaTest`.
4. Terminal con `Tests run`, `Failures: 0`, `Errors: 0` y `BUILD SUCCESS`.

Microservicios sugeridos para evidencias visuales:

- `ms-prestamos`: demuestra reglas con Feign mockeado e inventario/perfil.
- `ms-catalogo` o `ms-sucursales`: demuestra CRUD por capas de forma simple.

## Definition Of Done

| Criterio | Estado |
|:--|:--:|
| 5 microservicios con pruebas | Cumplido |
| 4 capas por microservicio | Cumplido |
| Minimo 12 tests por microservicio | Cumplido |
| H2 para repositorios | Cumplido |
| Mockito para servicios | Cumplido |
| MockMvc para controladores | Cumplido |
| Ejecucion Maven en verde | Cumplido |
| README actualizado | Cumplido |
| Guia de evidencia incluida | Cumplido |
