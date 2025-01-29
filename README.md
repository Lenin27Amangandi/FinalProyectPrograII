# Proyecto Museo - Sistema de Gestión de Pinturas y Usuarios

## Descripción General

Este proyecto es una aplicación de escritorio desarrollada en Java para gestionar información de un museo. Incluye funcionalidades para:

- Administrar pinturas mediante un sistema de código de barras.
- Gestionar usuarios con roles asignados como "Administrador" y "Supervisor".
- Conectar y operar con una base de datos SQLite integrada.

La aplicación utiliza una arquitectura modular, dividiendo la lógica en paquetes como `controller`, `model`, `service`, y `view`, con componentes diseñados para escalabilidad y mantenimiento.

---

## Estructura del Proyecto

```
ProyectoMuseo
├── src
│   ├── controller      # Lógica de negocio y controladores de usuarios y pinturas
│   ├── manager         # Manejo centralizado de la base de datos
│   ├── model           # Clases de datos (Usuario, Pintura)
│   ├── panel           # Paneles gráficos para la interfaz de usuario
│   └── view            # Ventanas y vistas principales
├── database
│   ├── museo.db        # Archivo SQLite de la base de datos
│   └── schema.sql      # Esquema de la base de datos
├── resources
│   ├── config.properties # Configuración de usuario y base de datos
│   └── paintings        # Imágenes asociadas a las pinturas
└── lib
    └── sqlite-jdbc.jar # Controlador JDBC para SQLite
```

---

## Configuración

### Requisitos Previos

1. Java 11 o superior.
2. Visual Studio Code con soporte para proyectos Java.
3. Controlador JDBC para SQLite (incluido en `/lib`).

### Configuración del Entorno

1. Clona el repositorio en tu máquina local:

   ```bash

   git clone <repositorio>
   ```

2. Configura la variable `JAVA_HOME` apuntando a tu instalación de Java.
3. Asegúrate de que el archivo `config.properties` contiene los datos correctos:

   ```properties
   usuario=Admin
   password=Admin123
   id=0000000000001
   ```

4. Ejecuta la aplicación desde Visual Studio Code o desde línea de comandos:

   ```bash
   java -jar ProyectoMuseo.jar
   ```

---

## Funcionalidades

### Gestión de Usuarios

- **Agregar Usuarios:**
  - Datos requeridos: Nombre, Rol, Nombre de Usuario, Contraseña, ID.
  - Validación automática de campos.
- **Modificar Usuarios:**
  - Campos editables: Nombre, Rol, Nombre de Usuario.

### Gestión de Pinturas

- **Agregar Pinturas:**
  - Incluye validación de código de barras y previsualización de imágenes.
  - Campos requeridos: Título, Autor, Año, Código de Barras, Ubicación, Descripción.
- **Modificar y Eliminar:**
  - Modifica o elimina pinturas según su código de barras.

### Administración de Base de Datos

- **Inicialización Automática:**
  - Creación de tablas (`Usuarios`, `Pinturas`, `Visitantes`) al iniciar la aplicación.
- **Usuario por Defecto:**
  - Usuario principal cargado desde `config.properties` si no hay registros.

---

## Uso

### Iniciar Sesión como Administrador

1. Introduce las credenciales configuradas en `config.properties`.
2. Si el inicio de sesión es exitoso, accederás al panel de administración.

### Agregar Pinturas

1. Ve al panel "Pinturas".
2. Llena los campos requeridos y carga una imagen asociada.
3. Haz clic en "Agregar".

---

## Créditos

Desarrollado por: Anndy Rengifo.
