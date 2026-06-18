# Comandos De Ejecucion Para Evidencia

Ejecutar desde la raiz del repositorio.

## Windows PowerShell

```powershell
cd codigo-fuente/ms-catalogo
.\mvnw.cmd test

cd ../ms-sucursales
.\mvnw.cmd test

cd ../ms-reservas
.\mvnw.cmd test

cd ../ms-devoluciones
.\mvnw.cmd test

cd ../ms-prestamos
.\mvnw.cmd test

cd ../api-gateway
.\mvnw.cmd test
```

## Linux / EC2

```bash
cd codigo-fuente/ms-catalogo && ./mvnw test
cd ../ms-sucursales && ./mvnw test
cd ../ms-reservas && ./mvnw test
cd ../ms-devoluciones && ./mvnw test
cd ../ms-prestamos && ./mvnw test
cd ../api-gateway && ./mvnw test
```

## Capturas Recomendadas

Para la presentacion, capturar:

- Arbol de carpetas `src/test/java` de `ms-prestamos`.
- Codigo abierto de `PrestamoServiceTest.java`.
- Terminal con `./mvnw test` y `BUILD SUCCESS` de `ms-prestamos`.
- Arbol de carpetas `src/test/java` de `ms-catalogo` o `ms-sucursales`.
- Codigo abierto de un `RepositoryTest` con `@DataJpaTest`.
- Terminal con `./mvnw test` y `BUILD SUCCESS` del segundo microservicio.
- Navegador con Swagger UI abierto en `/swagger-ui/index.html`.
- `application.yml` del `api-gateway` con rutas visibles.

Texto esperado en terminal:

```text
Tests run: N, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```
