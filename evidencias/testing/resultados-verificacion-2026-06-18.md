# Resultados De Verificacion - 2026-06-18

Entorno local:

- Java: 21.0.10
- Maven: 3.9.12
- Comando por modulo: `mvn -Dstyle.color=never test`
- Objetivo: validar que la integracion de Swagger y `api-gateway` no rompe las pruebas existentes.

| Modulo | Tests | Fallas | Errores | Estado |
|:--|--:|--:|--:|:--:|
| ms-catalogo | 17 | 0 | 0 | BUILD SUCCESS |
| ms-sucursales | 16 | 0 | 0 | BUILD SUCCESS |
| ms-reservas | 17 | 0 | 0 | BUILD SUCCESS |
| ms-devoluciones | 16 | 0 | 0 | BUILD SUCCESS |
| ms-prestamos | 19 | 0 | 0 | BUILD SUCCESS |
| api-gateway | 1 | 0 | 0 | BUILD SUCCESS |

Resultado:

- Total microservicios evaluados: 85 tests.
- Total adicional Gateway: 1 test.
- No se levantaron todos los servicios a la vez.
- La ejecucion fue secuencial, modulo por modulo.
