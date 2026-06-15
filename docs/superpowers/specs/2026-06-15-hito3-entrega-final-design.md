# Diseno De Entrega Final Hito 3

## Objetivo

Cerrar la actividad DSY1103 de pruebas unitarias con codigo verificable, evidencia visual legible y una presentacion editable de 9 diapositivas, sin ampliar el alcance funcional del proyecto.

## Alcance Tecnico

- Mantener los cinco microservicios evaluados: catalogo, sucursales, reservas, devoluciones y prestamos.
- Cubrir modelo, servicio, controlador y repositorio con al menos tres tests por capa.
- Hacer ejecutable `mvnw` en Linux para que el comando literal `./mvnw test` funcione.
- Ajustar `PrestamoRepositoryTest` para demostrar explicitamente `save`, `findById` y `findAll`, conservando cuatro tests de repositorio.
- Ejecutar cada microservicio por separado en EC2.

## Evidencia

Se usaran `ms-prestamos` y `ms-catalogo`:

- Captura del arbol `src/test/java` con las cuatro capas visibles.
- Captura de `PrestamoServiceTest` para Mockito y clientes Feign.
- Captura de `LibroRepositoryTest` para `@DataJpaTest` y H2.
- Captura de terminal con `./mvnw test`, resumen y `BUILD SUCCESS`.

Las imagenes se almacenaran en `evidencias/testing/capturas/`.

## Presentacion

La presentacion tendra exactamente 9 diapositivas:

1. Portada.
2. Arquitectura de los cinco microservicios evaluados.
3. Estructura de tests de `ms-prestamos`.
4. Codigo de `PrestamoServiceTest`.
5. Resultado de `ms-prestamos`.
6. Estructura de tests de `ms-catalogo`.
7. Codigo de `LibroRepositoryTest`.
8. Resultado de `ms-catalogo`.
9. Tabla resumen de cobertura de los cinco microservicios.

El PPTX sera editable y usara las capturas reales como evidencia, con texto explicativo breve alrededor de ellas.

## Criterio De Finalizacion

- Los cinco `./mvnw test` terminan con `BUILD SUCCESS` en EC2.
- GitHub y EC2 apuntan al mismo commit.
- Existen seis capturas legibles.
- El PPTX tiene 9 diapositivas, se renderiza sin recortes y queda versionado junto a la evidencia.
