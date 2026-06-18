# Codigo Fuente - Biblioteca Microservicios

La documentacion principal del proyecto esta en:

```text
../README.md
../TESTING_PLAN.md
../evidencias/testing/comandos-ejecucion.md
../evidencias/testing/resumen-cobertura.md
```

Para ejecutar las pruebas unitarias del hito:

```powershell
cd ms-catalogo
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

En Linux o EC2:

```bash
cd ms-catalogo && ./mvnw test
cd ../ms-sucursales && ./mvnw test
cd ../ms-reservas && ./mvnw test
cd ../ms-devoluciones && ./mvnw test
cd ../ms-prestamos && ./mvnw test
cd ../api-gateway && ./mvnw test
```
