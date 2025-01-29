-- database: ../database/museo.db

/*
copyRight EPN 2K25
AnndyR
DDL : Museo >> permite crear las tablas principales para el sistema ----
*/

DROP TABLE If EXISTS Pinturas;
DROP TABLE If EXISTS Visitantes;
DROP TABLE If EXISTS Usuarios;
DROP TABLE If EXISTS Credenciales;


CREATE TABLE IF NOT EXISTS Credenciales (
    idCredenciales  INTEGER PRIMARY KEY AUTOINCREMENT,
    username        VARCHAR(50) UNIQUE NOT NULL,
    password        VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Usuarios (
    idUsuarios      INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre          TEXT NOT NULL,
    rol             TEXT NOT NULL,
    identificacion  CHAR(13) UNIQUE NOT NULL,
    idCredenciales  INTEGER NOT NULL,
    FOREIGN KEY (idCredenciales) REFERENCES Credenciales(idCredenciales)
);

CREATE TABLE IF NOT EXISTS Pinturas (
    idPintura       INTEGER PRIMARY KEY AUTOINCREMENT,
    titulo          TEXT NOT NULL,
    autor           TEXT NOT NULL,
    anio            INTEGER NOT NULL,
    descripcion     TEXT,
    codigoBarras    CHAR(13) UNIQUE NOT NULL,
    categoria       TEXT NOT NULL, 
    ubicacion       TEXT NOT NULL,
    imagen          TEXT
);

CREATE TABLE IF NOT EXISTS Visitantes (
    idVisitante     INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre          TEXT NOT NULL,
    apellido        TEXT NOT NULL,
    fecha_visita    DATE NOT NULL
);


