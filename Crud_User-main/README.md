
# Reto de Refactorización – Calidad de Código (6° semestre)

Este proyecto **contiene errores intencionales de calidad** para que realices refactorización aplicando buenas prácticas.

## Objetivos
- Identificar **malas prácticas** en Controller, Service, Repository y Model.
- Aplicar **principios SOLID**, **DRY** y **clean code**.
- Mejorar **legibilidad, mantenibilidad y robustez**.

## Reglas del reto
1. Mantén la funcionalidad (CRUD simple de usuarios) pero **mejora el diseño**.
2. No agregues nuevas dependencias pesadas (mantén simple el build).
3. Escribe **pruebas unitarias** mínimas si te alcanza el tiempo (opcional).

## Problemas intencionales (encuéntralos y corrígelos)
- Inyección por campo y **falta de estereotipos** (`@Service`, `@Repository`).
- **Validaciones** y lógica de negocio en el `Controller`.
- **Código duplicado** (ordenamiento y validaciones).
- **Nombres crípticos** de rutas y métodos (`a()`, `b()`).
- Manejo de **excepciones genéricas** y `System.out.println`.
- **Modelo con campos públicos** y sin validaciones.
- **Repositorio en memoria** que **expone la colección interna**.
- **Valores mágicos** y respuestas **no tipadas**.

## Tareas sugeridas de refactorización
- [ ] Introducir `@Service`, `@Repository` y **inyección por constructor**.
- [ ] Usar `ResponseEntity<>` y **DTOs** para requests/responses.
- [ ] Validar con `jakarta.validation` (`@NotBlank`, `@Email`, `@Min`, etc.).
- [ ] Centralizar manejo de errores con `@RestControllerAdvice`.
- [ ] Extraer utilidades duplicadas y **eliminar magia** (constantes).
- [ ] Encapsular el modelo (campos privados + getters/setters).
- [ ] Revisar nombres de endpoints y **versionar API** (`/api/v1/users`).

## Endpoints actuales (mal diseñados)
- `GET /listAll`
- `POST /createUserNow`
- `GET /user/{id}`
- `DELETE /del/{id}`

## Ejecución
```bash
mvn spring-boot:run
```

> Nota: El repositorio es **en memoria**. Es suficiente para practicar refactorización de capas sin agregar BD.

## Entregables del equipo
1. Código refactorizado.
2. Explicación corta de cambios y justificación técnica.
3. Antes/Después (fragmentos de código).

¡Éxitos y que el código quede impecable! ✨
