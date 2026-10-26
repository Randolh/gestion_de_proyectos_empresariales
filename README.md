# Sistema de Gestión de Proyectos Empresariales

Aplicación de consola en **Java 17** con persistencia relacional en **MySQL** mediante **JDBC puro**, construida bajo el patrón arquitectónico **Modelo-Vista-Controlador (MVC)**, el patrón de acceso a datos **DAO (Data Access Object)** y el patrón creacional **Singleton** para la gestión de conexiones.

El diseño del proyecto se rige por los principios **SOLID, KISS, DRY y POLA**, evitando sobre-ingeniería innecesaria (sin DTOs ni frameworks pesados) para ofrecer una solución robusta, mantenible y pedagógica.

---

## Tabla de Contenidos
1. [Características y Arquitectura](#características-y-arquitectura)
2. [Requisitos Previos](#requisitos-previos)
3. [Paso 1: Clonar el Repositorio](#paso-1-clonar-el-repositorio)
4. [Paso 2: Inicializar la Base de Datos MySQL](#paso-2-inicializar-la-base-de-datos-mysql)
5. [Paso 3: Configurar Credenciales de Conexión](#paso-3-configurar-credenciales-de-conexión)
6. [Paso 4: Abrir y Ejecutar en Apache NetBeans](#paso-4-abrir-y-ejecutar-en-apache-netbeans)
7. [Estructura del Proyecto](#estructura-del-proyecto)
8. [Reglas de Negocio Implementadas](#reglas-de-negocio-implementadas)

---

## Características y Arquitectura

* **Arquitectura en Capas (MVC)**:
  * **Modelo (`model`)**: Entidades POO puras (`Empleado`, `Proyecto`, `EmpleadoProyecto`, `EstadoProyecto`), configuración Singleton (`ConexionBD`) y acceso a datos (`EmpleadoDAO`, `ProyectoDAO`, `EmpleadoProyectoDAO`).
  * **Controlador (`controller`)**: Validación rigurosa de reglas de negocio, garantizando integridad antes de llegar a la base de datos.
  * **Vista (`view`)**: Vistas modulares por funcionalidad (`EmpleadoView`, `ProyectoView`, `AsignacionView`, `MenuPrincipal`) con lectura segura por teclado (`ConsolaUtils`).
* **Excepciones Verificadas (*Checked Exceptions*)**: Uso de `ValidacionException` heredando de `Exception`, obligando en tiempo de compilación al control y captura de errores sin salidas forzadas ni caídas del programa (**RF-12**).
* **Integridad Referencial y Restricciones**: Claves foráneas con `ON DELETE RESTRICT` y reglas `CHECK` tanto a nivel de DDL en MySQL como en la lógica de negocio en Java.
* **Seguridad de Credenciales**: Parámetros de conexión desacoplados en `database.properties` fuera del control de versiones (`.gitignore`).

---

## Requisitos Previos

Asegúrate de tener instaladas las siguientes herramientas en tu sistema operativo:

1. **Java Development Kit (JDK 17 o superior)**. Verifica con:
   ```bash
   java -version
   ```
2. **Apache Maven (3.8 o superior)**. Verifica con:
   ```bash
   mvn -version
   ```
3. **Servidor MySQL activo (versión 8.0 o superior)** corriendo en el puerto `3306`.
4. **Apache NetBeans IDE (versión 17 o superior)** (o cualquier IDE de tu preferencia como IntelliJ IDEA o VS Code).

---

## Paso 1: Clonar el Repositorio

Abre tu terminal y clona el proyecto en tu máquina local:

```bash
git clone https://github.com/Randolh/gestion_de_proyectos_empresariales.git

cd gestion_de_proyectos_empresariales
```

---

## Paso 2: Inicializar la Base de Datos MySQL

Los scripts SQL estructurados se encuentran en la carpeta `scripts_sql/`.

### Opción A: Script Automático Todo-en-Uno (Recomendado)
Desde la terminal, ejecuta el archivo `init_all.sql` en tu motor MySQL:

```bash
mysql -u root -p < scripts_sql/init_all.sql
```
*(Ingresa la contraseña de tu usuario MySQL cuando el sistema te la solicite).*

> **Nota sobre el usuario y contraseña:**
> - Si tu usuario de MySQL es diferente de `root`, reemplázalo por tu nombre de usuario:
>   ```bash
>   mysql -u tu_usuario -p < scripts_sql/init_all.sql
>   ```
> - Si tu usuario no tiene contraseña configurada, puedes omitir el parámetro `-p`:
>   ```bash
>   mysql -u tu_usuario < scripts_sql/init_all.sql
>   ```

### Opción B: Ejecución Manual en Orden
Si prefieres ejecutar los scripts individualmente desde **MySQL Workbench**:

1. **`01_ddl_schema.sql`**: Elimina y crea la base de datos `gestion_proyectos`, las tablas `empleados`, `proyectos` y `empleado_proyecto`, con sus PKs, FKs y restricciones `CHECK`.
2. **`02_dml_seeds.sql`**: Carga los datos semilla iniciales (5 empleados, 3 proyectos y 6 asignaciones).
3. **`03_dql_consultas.sql`**: Consultas `JOIN` de verificación para comprobar la integridad de las relaciones.

---

## Paso 3: Configurar Credenciales de Conexión

Por razones de seguridad, el archivo con tus contraseñas locales no se sube al repositorio Git.

1. Ve a la carpeta de recursos del proyecto Java:
   ```bash
   cd gestion_de_proyectos_empresariales/src/main/resources/
   ```
2. Observarás una plantilla llamada `database.properties.example`. Crea una copia con el nombre **`database.properties`**:
   ```bash
   cp database.properties.example database.properties
   ```
3. Abre `database.properties` en tu editor de texto y coloca las credenciales de tu servidor MySQL local:
   ```properties
   db.url=jdbc:mysql://localhost:3306/gestion_proyectos
   db.user=root
   db.password=tu_contraseña_aqui
   ```
   *(Si tu usuario `root` de MySQL no tiene contraseña, deja `db.password=` en blanco).*

> **Nota:** La aplicación también admite variables de entorno (`DB_URL`, `DB_USER`, `DB_PASSWORD`), las cuales tienen prioridad si están definidas.

---

## Paso 4: Abrir y Ejecutar en Apache NetBeans

1. **Abrir NetBeans**.
2. Dirígete al menú superior: **File** > **Open Project...** (o presiona `Ctrl + Shift + O`).
3. Navega hasta la carpeta descargada y selecciona **`gestion_de_proyectos_empresariales`** (la reconocerás porque tiene el ícono de proyecto Maven).
4. Haz clic en el botón **Open Project**.
5. Espera unos segundos a que NetBeans resuelva las dependencias de Maven (`com.mysql:mysql-connector-j:8.3.0`).
6. En la barra de herramientas superior, presiona el botón de **Run Project** o presiona la tecla **`F6`**.
7. En la ventana inferior **Output** de NetBeans aparecerá el menú interactivo de la consola listo para utilizar.

---

## Estructura del Proyecto

```text
gestion_de_proyectos_empresariales/
├── pom.xml                                      # Configuración Maven y driver MySQL
├── nbactions.xml                                # Configuración de ejecución en NetBeans
├── scripts_sql/                                 # Scripts modulares de base de datos
│   ├── 01_ddl_schema.sql                        # Estructura de tablas y restricciones
│   ├── 02_dml_seeds.sql                         # Datos de prueba iniciales
│   ├── 03_dql_consultas.sql                     # Consultas JOIN de verificación
│   └── init_all.sql                             # Script unificado para inicialización
└── src/
    ├── main/
    │   ├── resources/
    │   │   ├── database.properties.example      # Plantilla de credenciales
    │   │   └── database.properties              # Credenciales locales (ignorado en git)
    │   └── java/randolh/gestion_de_proyectos_empresariales/
    │       ├── exception/
    │       │   └── ValidacionException.java     # Checked Exception personalizada
    │       ├── model/
    │       │   ├── clases/                      # Entidades POO
    │       │   │   ├── Empleado.java
    │       │   │   ├── Proyecto.java
    │       │   │   ├── EstadoProyecto.java      # Enum (PLANIFICADO, EN_CURSO, FINALIZADO)
    │       │   │   └── EmpleadoProyecto.java    # Asociación con Empleado y Proyecto (sin DTOs)
    │       │   ├── config/
    │       │   │   └── ConexionBD.java          # Patrón Singleton para JDBC
    │       │   └── dao/                         # Capa de Acceso a Datos
    │       │       ├── EmpleadoDAO.java
    │       │       ├── ProyectoDAO.java
    │       │       └── EmpleadoProyectoDAO.java
    │       ├── controller/                      # Capa de Negocio y Reglas
    │       │   ├── EmpleadoController.java
    │       │   ├── ProyectoController.java
    │       │   └── EmpleadoProyectoController.java
    │       └── view/                            # Capa de Vista y Consola
    │           ├── ConsolaUtils.java            # Lectura segura con Scanner
    │           ├── EmpleadoView.java            # Menú y formularios de empleados
    │           ├── ProyectoView.java            # Menú y formularios de proyectos
    │           ├── AsignacionView.java          # Menú de asignaciones y reportes
    │           ├── MenuPrincipal.java           # Orquestador general
    │           └── Main.java                    # Punto de entrada de la aplicación
```

---

## Reglas de Negocio Implementadas

| Regla | Requisito | Descripción |
| :--- | :--- | :--- |
| **Regla 1** | RF-01, RF-12 | Nombre, correo y cargo del empleado son estrictamente obligatorios. |
| **Regla 2** | RF-01 | El correo electrónico debe ser único en todo el sistema. |
| **Regla 3** | RF-04, RF-12 | El nombre del proyecto es obligatorio. |
| **Regla 4** | RF-04, RF-12 | La fecha de fin debe ser igual o posterior a la fecha de inicio (`fecha_fin >= fecha_inicio`). |
| **Regla 5** | RF-04 | El estado del proyecto sólo puede ser `PLANIFICADO`, `EN_CURSO` o `FINALIZADO`. |
| **Regla 6** | RF-07, RF-10 | Las horas semanales dedicadas deben ser mayores a 0 y como máximo 40 (`1 a 40`). |
| **Regla 7** | RF-07 | Tanto el empleado como el proyecto deben existir en la BD antes de permitir la asignación. |
| **Regla 8** | RF-07 | No se permite asignar el mismo empleado al mismo proyecto más de una vez. |
| **Regla 9** | RF-11 | No se puede modificar ni retirar una asignación si no existe previamente la pareja `(empleadoId, proyectoId)`. |
| **Regla 10**| RF-11 | El retiro de asignación únicamente elimina de `empleado_proyecto`, jamás borra empleados ni proyectos. |
| **Integridad** | RF-03, RF-06 | Rechazo amigable si se intenta eliminar un empleado o proyecto con asignaciones activas. |
| **Robustez** | RF-12 | La aplicación jamás se cae ante datos inválidos ni fallos de BD; muestra avisos claros y regresa al menú. |
