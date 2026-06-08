# Comandos De Ejecucion Para Evidencia

Ejecutar desde la raiz del repositorio.

## Windows PowerShell

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

## Linux / EC2

```bash
cd codigo-fuente/ms-catalogo && mvn test
cd ../ms-sucursales && mvn test
cd ../ms-reservas && mvn test
cd ../ms-devoluciones && mvn test
cd ../ms-prestamos && mvn test
```

## Capturas Recomendadas

Para la presentacion, capturar:

- Arbol de carpetas `src/test/java` de `ms-prestamos`.
- Codigo abierto de `PrestamoServiceTest.java`.
- Terminal con `mvn test` y `BUILD SUCCESS` de `ms-prestamos`.
- Arbol de carpetas `src/test/java` de `ms-catalogo` o `ms-sucursales`.
- Codigo abierto de un `RepositoryTest` con `@DataJpaTest`.
- Terminal con `mvn test` y `BUILD SUCCESS` del segundo microservicio.

Texto esperado en terminal:

```text
Tests run: N, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```
