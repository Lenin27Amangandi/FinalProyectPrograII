# ArtVision - Sistema de Gestión de Museo

## Descripción

ArtVision es una aplicación de gestión de museos que permite la administración de pinturas, usuarios y otras funcionalidades clave para la organización y exhibición de obras de arte. La aplicación está desarrollada en **Java** con una interfaz gráfica basada en **Swing** y una base de datos en **SQLite**.

## Estructura del Proyecto

└── anndyrengifo-finalproyectprograii/
    ├── README.md
    ├── bin/
    │   ├── TodoCode.txt
    │   ├── BusinessLogic/
    │   │   ├── entities/
    │   │   └── services/
    │   ├── DataAccess/
    │   │   ├── DAO/
    │   │   ├── DTO/
    │   │   └── DataHelper/
    │   ├── GUI/
    │   │   └── panel/
    │   │       ├── adminPanel/
    │   │       ├── commonPanel/
    │   │       ├── pinturaPanel/
    │   │       └── usuarioPanel/
    │   └── utils/
    │       ├── Estilo/
    │       └── Resources/
    ├── database/
    │   └── museo.sqlite
    ├── dbScript/
    │   ├── museo_DDL.sql
    │   └── museo_DML.sql
    ├── design/
    │   ├── Diagram.plantUML
    │   └── Diagramas.drawio
    ├── lib/
    └── src/
        ├── App.java
        ├── BusinessLogic/
        ├── DataAccess/
        ├── GUI/
        ├── utils/

## Instalación y Ejecución

### Requisitos

- **Java 8+**
- **SQLite** (integrado en el proyecto)
- **IDE recomendado:** IntelliJ IDEA, Eclipse o NetBeans

### Configuración

1. Clona el repositorio:

   ```sh
   git clone https://github.com/usuario/anndyrengifo-finalproyectprograii.git
   ```

2. Importa el proyecto en tu IDE favorito.
3. Asegúrate de que `sqlite-jdbc.jar` esté en el classpath.
4. Ejecuta `App.java`.

## Características Principales

- **Gestión de pinturas**: Agregar, modificar, eliminar y visualizar pinturas en la base de datos.
- **Gestión de usuarios**: Administración de cuentas y roles.
- **Autenticación**: Inicio de sesión con credenciales.
- **Interfaz gráfica**: Basada en **Swing**, incluye paneles para administración.
- **Base de datos**: SQLite con scripts de inicialización (`museo_DDL.sql` y `museo_DML.sql`).

## Uso

1. Al ejecutar la aplicación, se iniciará la interfaz principal.
2. Desde la pantalla de inicio, puedes acceder al panel de administración para gestionar pinturas y usuarios.
3. Los usuarios pueden autenticarse según su rol (`Administrador` o `Supervisor`).
4. Se pueden realizar operaciones CRUD en las pinturas desde el panel correspondiente.

## Base de Datos

Ubicada en `database/museo.sqlite`, contiene las siguientes tablas principales:

- `Usuarios`
- `Pinturas`
- `Categorias`
- `Autores`
- `Salas`

## Contribuciones

Create by: Grupo1

Si deseas contribuir:

1. Realiza un **fork** del proyecto.
2. Crea una **rama** (`git checkout -b feature-nueva-funcionalidad`).
3. Realiza los cambios y haz un **commit** (`git commit -m "Añadir nueva funcionalidad"`).
4. Sube la rama (`git push origin feature-nueva-funcionalidad`).
5. Abre un **pull request**.

## Licencia

Este proyecto está bajo la licencia **MIT**. Puedes ver más detalles en el archivo `LICENSE`.
