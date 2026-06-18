# Resumen De Cobertura De Pruebas - EP3 Semana 15

Verificacion base ejecutada el 2026-06-15 en EC2 con Java 21.0.10 y Maven 3.8.7. Los microservicios se probaron uno por uno. Para el cierre de Semana 15, el comando estandar documentado es `./mvnw test` en Linux/EC2.

Verificacion local actualizada el 2026-06-18 con Java 21.0.10 y Maven 3.9.12: los cinco microservicios y el `api-gateway` finalizaron con `BUILD SUCCESS`.

| Microservicio | Modelo | Repositorio | Servicio | Controlador | Contexto Spring | Total Maven | Estado |
|:--|--:|--:|--:|--:|--:|--:|:--:|
| ms-catalogo | 3 | 4 | 5 | 4 | 1 | 17 | BUILD SUCCESS |
| ms-sucursales | 3 | 3 | 5 | 4 | 1 | 16 | BUILD SUCCESS |
| ms-reservas | 3 | 3 | 6 | 4 | 1 | 17 | BUILD SUCCESS |
| ms-devoluciones | 3 | 3 | 5 | 4 | 1 | 16 | BUILD SUCCESS |
| ms-prestamos | 4 | 4 | 5 | 5 | 1 | 19 | BUILD SUCCESS |

Total microservicios: 85 tests ejecutados, 0 fallas, 0 errores.

Total adicional Gateway: 1 test ejecutado, 0 fallas, 0 errores.

## Swagger Y Gateway

| Elemento | Estado | Evidencia tecnica |
|:--|:--:|:--|
| Springdoc en `ms-catalogo` | Integrado | `pom.xml`, `application.properties`, `LibroController.java` |
| Springdoc en `ms-sucursales` | Integrado | `pom.xml`, `application.properties`, `SucursalController.java` |
| Springdoc en `ms-reservas` | Integrado | `pom.xml`, `application.properties`, `ReservaController.java` |
| Springdoc en `ms-devoluciones` | Integrado | `pom.xml`, `application.properties`, `DevolucionController.java` |
| Springdoc en `ms-prestamos` | Integrado | `pom.xml`, `application.properties`, `PrestamoController.java` |
| API Gateway | Integrado | `codigo-fuente/api-gateway/application.yml` con 5 rutas |

## Archivos Principales Para Revisar

| Microservicio | Archivo recomendado |
|:--|:--|
| ms-prestamos | `codigo-fuente/ms-prestamos/src/test/java/cl/duoc/ms_prestamos/service/PrestamoServiceTest.java` |
| ms-prestamos | `codigo-fuente/ms-prestamos/src/test/java/cl/duoc/ms_prestamos/controller/PrestamoControllerTest.java` |
| ms-catalogo | `codigo-fuente/ms-catalogo/src/test/java/cl/duoc/ms_catalogo/repository/LibroRepositoryTest.java` |
| ms-sucursales | `codigo-fuente/ms-sucursales/src/test/java/cl/duoc/ms_sucursales/controller/SucursalControllerTest.java` |
| ms-reservas | `codigo-fuente/ms-reservas/src/test/java/cl/duoc/ms_reservas/service/ReservaServiceImplTest.java` |
| ms-devoluciones | `codigo-fuente/ms-devoluciones/src/test/java/cl/duoc/ms_devoluciones/service/DevolucionServiceImplTest.java` |
