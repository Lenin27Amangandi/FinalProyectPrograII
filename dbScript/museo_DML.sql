-- database: ../database/museo.db

/*
copyRight EPN 2K25
AnndyR
DDL : Museo >> permite crear las tablas principales para el sistema para prueba ----
*/


INSERT INTO Credenciales (username, password) VALUES 
('admin', 'admin123'),
('user1', 'password1');

INSERT INTO Usuarios (nombre, rol, identificacion, idCredenciales) VALUES 
('Anndy', 'Administrador', '0000000000001', 1),
('Juan Perez', 'Visitante', '0000000000002', 2);

INSERT INTO Autores (nombre, nacionalidad, fecha_nacimiento, fecha_fallecimiento) VALUES 
('Leonardo da Vinci', 'Italiano', '1452-04-15', '1519-05-02'),
('Vincent van Gogh', 'Holandés', '1853-03-30', '1890-07-29');

INSERT INTO Categorias (nombre) VALUES 
('Renacimiento'),
('Impresionismo');

INSERT INTO Ubicaciones (sala, seccion) VALUES 
('Sala 1', 'Sección A'),
('Sala 2', 'Sección B');

INSERT INTO Pinturas (titulo, idAutor, anio, descripcion, codigo_barras, idCategoria, idUbicacion, imagen) VALUES 
('Mona Lisa', 1, 1503, 'Retrato de Lisa Gherardini', '1234567890123', 1, 1, 'mona_lisa.jpg'),
('La noche estrellada', 2, 1889, 'Pintura de un cielo nocturno vibrante', '9876543210987', 2, 2, 'noche_estrellada.jpg');

INSERT INTO Visitantes (nombre, apellido, fecha_visita) VALUES 
('Carlos', 'Ramírez', '2025-01-15'),
('Luisa', 'Fernández', '2025-01-14');
