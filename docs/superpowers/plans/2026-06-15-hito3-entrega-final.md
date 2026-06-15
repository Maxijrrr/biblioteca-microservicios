# Hito 3 Entrega Final Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Dejar el Hito 3 tecnicamente verificable, documentado y presentado en el formato exacto de la pauta DSY1103.

**Architecture:** Los cambios de codigo se limitan a pruebas y metadatos Git. La evidencia se genera desde VS Code y la presentacion se construye como PPTX editable con nueve diapositivas.

**Tech Stack:** Java 21, Spring Boot 3.2.5, JUnit 5, Mockito, MockMvc, H2, Maven Wrapper, AWS EC2, VS Code y `@oai/artifact-tool`.

---

### Task 1: Cerrar La Cobertura De Repositorio

**Files:**
- Modify: `codigo-fuente/ms-prestamos/src/test/java/cl/duoc/ms_prestamos/repository/PrestamoRepositoryTest.java`

- [ ] Renombrar el test de guardado para expresar que tambien valida `findById`.
- [ ] Convertir el test por perfil en una prueba explicita de `findAll`.
- [ ] Ejecutar `mvn test` y confirmar 19 tests, 0 fallas y 0 errores.

### Task 2: Corregir Maven Wrapper En Linux

**Files:**
- Modify mode: `codigo-fuente/ms-catalogo/mvnw`
- Modify mode: `codigo-fuente/ms-sucursales/mvnw`
- Modify mode: `codigo-fuente/ms-reservas/mvnw`
- Modify mode: `codigo-fuente/ms-devoluciones/mvnw`
- Modify mode: `codigo-fuente/ms-prestamos/mvnw`

- [ ] Registrar modo Git `100755` para cada wrapper.
- [ ] Publicar el cambio y actualizar EC2 con `git pull --ff-only`.
- [ ] Ejecutar `./mvnw test` por separado en cada servicio.

### Task 3: Capturar Evidencia Real

**Files:**
- Create: `evidencias/testing/capturas/prestamos-estructura.png`
- Create: `evidencias/testing/capturas/prestamos-codigo.png`
- Create: `evidencias/testing/capturas/prestamos-terminal.png`
- Create: `evidencias/testing/capturas/catalogo-estructura.png`
- Create: `evidencias/testing/capturas/catalogo-codigo.png`
- Create: `evidencias/testing/capturas/catalogo-terminal.png`

- [ ] Abrir el repositorio real en VS Code.
- [ ] Capturar arbol, codigo y terminal de ambos servicios.
- [ ] Revisar legibilidad, rutas, anotaciones y resumen Maven.

### Task 4: Construir La Presentacion

**Files:**
- Create: `evidencias/testing/Presentacion_Hito_3_Pruebas_Unitarias.pptx`

- [ ] Crear nueve diapositivas en el orden de la pauta.
- [ ] Incluir las seis capturas reales sin reemplazar el contenido editable.
- [ ] Renderizar todas las diapositivas y revisar el montaje completo.
- [ ] Corregir recortes, desbordes y contraste antes de exportar.

### Task 5: Documentar Y Publicar

**Files:**
- Modify: `README.md`
- Modify: `TESTING_PLAN.md`
- Modify: `evidencias/testing/resumen-cobertura.md`
- Modify: `evidencias/testing/comandos-ejecucion.md`

- [ ] Registrar el uso verificado de `./mvnw test`.
- [ ] Enlazar la presentacion y las capturas.
- [ ] Ejecutar `git diff --check`.
- [ ] Confirmar que local, GitHub y EC2 comparten el mismo hash final.
