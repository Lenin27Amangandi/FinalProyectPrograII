-- database: ../database/museo.sqlite

/*
copyRight EPN 2K25
AnndyR
DDL : Museo >> permite crear las tablas principales para el sistema para prueba ----
*/
-- Insertar datos en la tabla Credenciales
INSERT INTO Credenciales (username, password) VALUES
('admin', 'admin123'),
('user1', 'user123'),
('guest', 'guest123');
INSERT INTO Roles (nombreRol) VALUES
('admin');


-- Insertar datos en la tabla Usuarios
INSERT INTO Usuarios (nombre, identificacion, idCredenciales, idRol, estado) VALUES
('Juan Pérez', '1234567890123', 1, 1, 'A');
-- ('María Gómez', 'Usuario', '9876543210987', 2),
-- ('Carlos Ruiz', 'Invitado', '1230984567890', 3);

-- Insertar datos en la tabla Categorias
INSERT INTO Categorias (categoria) VALUES
('Renacimiento'),
('Barroco'),
('Impresionismo');

-- Insertar datos en la tabla Autores
INSERT INTO Autores (nombreAutor) VALUES
('Leonardo da Vinci'),
('Pablo Picasso'),
('Claude Monet');

-- Insertar datos en la tabla Secciones
INSERT INTO Salas (Salas) VALUES
('Sala 1'),
('Sala 2'),
('Sala 3');

-- Insertar datos en la tabla Pinturas
INSERT INTO Pinturas (titulo, anio, descripcion, codigoBarras, idCategoria, idAutor, idSala, imagen) VALUES
('La Mona Lisa', 1503, 'Retrato de Lisa Gherardini, esposa de Francesco del Giocondo.', '1234567890123', 1, 1, 1, 'monalisa.jpg');
-- ('Guernica', 1937, 'Pintura que refleja los horrores de la Guerra Civil Española.', '9876543210987', 2, 2, 2, 'guernica.jpg'),
-- ('Impresion Soleil Levant', 1872, 'Obra que dio nombre al movimiento impresionista.', '2345678901234', 3, 3, 3, 'soleillevant.jpg');
