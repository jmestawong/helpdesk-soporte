# HelpDesk Soporte

Sistema de gestión de tickets de soporte (mesa de ayuda), proyecto del curso **Desarrollo de Aplicaciones Web** (EFSRT III/IV/V). Incluye gestión de tickets, usuarios, dashboard de métricas y una **base de conocimiento** de artículos de solución.

## Stack

- **Backend**: Java 17 + Spring Boot 3.5, Spring Data JPA, Spring Security, Thymeleaf
- **Frontend**: Thymeleaf + Bootstrap 5 + Chart.js (server-side rendered)
- **Base de datos**: MySQL 8
- **Autenticación**: formulario + sesión, contraseñas cifradas con `BCryptPasswordEncoder`
- **Reportes**: exportación de dashboard a PDF con OpenPDF

## Estructura

```
helpdesk/
  src/main/java/com/helpdesk/
    controllers/    Tickets, Usuarios, Dashboard, Base de Conocimiento, Login, Registro
    entity/         Usuario, Rol, Ticket, Categoria, Prioridad, EstadoTicket, ArticuloConocimiento
    repository/     Interfaces JpaRepository
    service/        Interfaces de servicio + implementación
    config/         Seguridad (roles y rutas)
  src/main/resources/
    templates/      Vistas Thymeleaf (login, tickets, usuarios, dashboard, base_conocimiento)
    application.properties
  database/
    helpdesk_soporte.sql   Script de creación de la BD + datos de prueba
```

## Módulos

- **Tickets**: alta, edición, baja y búsqueda de incidencias (todos los roles).
- **Usuarios**: administración de cuentas y roles, con badge de color por rol (solo Administrador).
- **Dashboard**: métricas de tickets por estado/prioridad, filtro por fecha y exportación a PDF (Administrador y Técnico).
- **Base de Conocimiento**: artículos con solución a problemas frecuentes, con búsqueda por título/contenido, asociados a una categoría y a su autor (todos los roles pueden ver/crear/editar).
- **Perfil**: cada usuario puede ver sus datos, actualizar nombres/apellidos y cambiar su contraseña.

## Interfaz

Todas las pantallas autenticadas comparten un layout con:

- **Sidebar** fijo (`templates/fragments/nav.html :: sidebar`) con Dashboard, Tickets, Nuevo Ticket, Base de Conocimiento, Usuarios y Perfil — cada ítem se muestra u oculta según el rol y resalta la sección activa.
- **Topbar** (`templates/fragments/nav.html :: topbar`) con buscador rápido de tickets, ícono de notificaciones y avatar con las iniciales del usuario (enlaza a `/perfil`).

El HTML/estilo compartido vive en `static/css/app.css`; para agregar una pantalla nueva basta con incluir ambos fragmentos y pasar un atributo de modelo `activo` (ej. `"tickets"`) para resaltar el ítem correspondiente del sidebar.

## Cómo levantarlo localmente

### 1. Base de datos

Requiere MySQL corriendo en `localhost:3306`. Ejecuta el script de `database/helpdesk_soporte.sql`, que crea la base `helpdesk_soporte`, sus tablas y datos de prueba:

```bash
mysql -u root -p --default-character-set=utf8mb4 < database/helpdesk_soporte.sql
```

> Importante: usa `--default-character-set=utf8mb4` (o el cliente equivalente en Workbench) para que los acentos se guarden correctamente.

Si tu usuario/contraseña de MySQL son distintos a `root` / `mysql`, o usas otro puerto, actualiza `spring.datasource.*` en `src/main/resources/application.properties`.

### 2. Backend

```bash
./mvnw spring-boot:run
```

Por defecto la app corre en `http://localhost:8081` (puerto configurable en `server.port`).

### 3. Pruebas

```bash
./mvnw test
```

## Credenciales de prueba

El script de base de datos crea tres usuarios, uno por cada rol:

| Rol            | Correo               | Contraseña    |
|----------------|-----------------------|---------------|
| Administrador  | `jennyfer@test.com`   | `admin123`    |
| Técnico        | `soporte@test.com`    | `tecnico123`  |
| Usuario        | `usuario@test.com`    | `usuario123`  |

También puedes crear una cuenta nueva desde "Registrarse" en el login (queda con rol Usuario por defecto).

## Permisos por rol

| Ruta                  | Administrador | Técnico | Usuario |
|-----------------------|:---:|:---:|:---:|
| `/tickets/**`          | ✅ | ✅ | ✅ |
| `/base-conocimiento/**`| ✅ | ✅ | ✅ |
| `/dashboard/**`        | ✅ | ✅ | ❌ |
| `/usuarios/**`         | ✅ | ❌ | ❌ |
| `/perfil/**`           | ✅ | ✅ | ✅ |
