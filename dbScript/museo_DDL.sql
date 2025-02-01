-- database: ../database/museo.sqlite

/*
copyRight EPN 2K25
AnndyR
DDL : Museo >> permite crear las tablas principales para el sistema ----
*/

-- Eliminar tablas existentes si es necesario
DROP TABLE IF EXISTS Pinturas;
DROP TABLE IF EXISTS Categorias;
DROP TABLE IF EXISTS Autores;
DROP TABLE IF EXISTS Salas;
DROP TABLE IF EXISTS Usuarios;
DROP TABLE IF EXISTS Credenciales;
DROP TABLE IF EXISTS Roles;

-- Crear tabla Roles para manejar los roles de los usuarios
CREATE TABLE IF NOT EXISTS Roles (
    idRol INTEGER PRIMARY KEY AUTOINCREMENT,
    nombreRol TEXT NOT NULL UNIQUE
);

-- Crear tabla Credenciales para manejar el acceso
CREATE TABLE IF NOT EXISTS Credenciales (
    idCredenciales  INTEGER PRIMARY KEY AUTOINCREMENT,
    username        VARCHAR(50) UNIQUE NOT NULL,
    password        VARCHAR(50) NOT NULL  -- Almacenar hash de contraseña
);

-- Crear tabla Usuarios para manejar los usuarios
CREATE TABLE IF NOT EXISTS Usuarios (
    idUsuarios      INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre          TEXT NOT NULL,
    identificacion  CHAR(13) UNIQUE NOT NULL,
    idCredenciales  INTEGER NOT NULL,
    idRol           INTEGER NOT NULL,  -- Relaciona con Roles
    estado          TEXT NOT NULL DEFAULT 'A',  -- Estado del usuario (activo, inactivo, eliminado)
    fechaCrea       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Fecha de creación
    fechaModifica   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Fecha de última modificación
    FOREIGN KEY (idCredenciales) REFERENCES Credenciales(idCredenciales),
    FOREIGN KEY (idRol) REFERENCES Roles(idRol)
);

-- Crear tabla Categorias para manejar las categorías de las pinturas
CREATE TABLE IF NOT EXISTS Categorias (
    idCategoria      INTEGER PRIMARY KEY AUTOINCREMENT,
    categoria        TEXT NOT NULL UNIQUE
);

-- Crear tabla Autores para manejar los autores de las pinturas
CREATE TABLE IF NOT EXISTS Autores (
    idAutor          INTEGER PRIMARY KEY AUTOINCREMENT,
    nombreAutor      TEXT NOT NULL
);

-- Crear tabla Salas para manejar la ubicación de las pinturas
CREATE TABLE IF NOT EXISTS Salas (
    idSala      INTEGER PRIMARY KEY AUTOINCREMENT,
    Salas       TEXT NOT NULL
);

-- Crear tabla Pinturas para manejar la información específica de cada pintura
CREATE TABLE IF NOT EXISTS Pinturas (
    idPintura        INTEGER PRIMARY KEY AUTOINCREMENT,
    titulo           TEXT NOT NULL,
    anio             INTEGER NOT NULL,  -- Validación de año
    descripcion      TEXT,
    codigoBarras     CHAR(13) UNIQUE NOT NULL,
    idCategoria      INTEGER NOT NULL,  -- Relaciona con Categorias
    idAutor          INTEGER NOT NULL,  -- Relaciona con Autores
    idSala           INTEGER NOT NULL,  -- Relaciona con Salas
    imagen           TEXT,              -- Ruta o URL de la imagen
    estado           TEXT NOT NULL DEFAULT 'A',  -- Estado de la pintura (activo, inactivo, eliminado)
    fechaCrea        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Fecha de creación
    fechaModifica    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Fecha de última modificación
    FOREIGN KEY (idCategoria) REFERENCES Categorias(idCategoria),
    FOREIGN KEY (idAutor) REFERENCES Autores(idAutor),
    FOREIGN KEY (idSala) REFERENCES Salas(idSala)
);

-- Crear índices para mejorar el rendimiento en búsquedas frecuentes
CREATE INDEX idx_pinturas_titulo ON Pinturas(titulo);
CREATE INDEX idx_autores_nombre ON Autores(nombreAutor);
CREATE INDEX idx_usuarios_nombre ON Usuarios(nombre);

-- Insertar los roles por defecto: Administrador y Supervisor
-- INSERT OR IGNORE INTO Roles (nombreRol) VALUES ('Administrador');
-- INSERT OR IGNORE INTO Roles (nombreRol) VALUES ('Supervisor');