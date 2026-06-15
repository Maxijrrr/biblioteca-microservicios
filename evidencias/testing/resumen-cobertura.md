# Resumen De Cobertura De Pruebas

Verificacion final ejecutada el 2026-06-15 en EC2 con Java 21.0.10 y Maven 3.8.7. Los microservicios se probaron uno por uno.

| Microservicio | Modelo | Repositorio | Servicio | Controlador | Contexto Spring | Total Maven | Estado |
|:--|--:|--:|--:|--:|--:|--:|:--:|
| ms-catalogo | 3 | 4 | 5 | 4 | 1 | 17 | BUILD SUCCESS |
| ms-sucursales | 3 | 3 | 5 | 4 | 1 | 16 | BUILD SUCCESS |
| ms-reservas | 3 | 3 | 6 | 4 | 1 | 17 | BUILD SUCCESS |
| ms-devoluciones | 3 | 3 | 5 | 4 | 1 | 16 | BUILD SUCCESS |
| ms-prestamos | 4 | 4 | 5 | 5 | 1 | 19 | BUILD SUCCESS |

Total: 85 tests ejecutados, 0 fallas, 0 errores.

## Archivos Principales Para Revisar

| Microservicio | Archivo recomendado |
|:--|:--|
| ms-prestamos | `codigo-fuente/ms-prestamos/src/test/java/cl/duoc/ms_prestamos/service/PrestamoServiceTest.java` |
| ms-prestamos | `codigo-fuente/ms-prestamos/src/test/java/cl/duoc/ms_prestamos/controller/PrestamoControllerTest.java` |
| ms-catalogo | `codigo-fuente/ms-catalogo/src/test/java/cl/duoc/ms_catalogo/repository/LibroRepositoryTest.java` |
| ms-sucursales | `codigo-fuente/ms-sucursales/src/test/java/cl/duoc/ms_sucursales/controller/SucursalControllerTest.java` |
| ms-reservas | `codigo-fuente/ms-reservas/src/test/java/cl/duoc/ms_reservas/service/ReservaServiceImplTest.java` |
| ms-devoluciones | `codigo-fuente/ms-devoluciones/src/test/java/cl/duoc/ms_devoluciones/service/DevolucionServiceImplTest.java` |
