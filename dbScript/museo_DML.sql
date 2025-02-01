-- database: ../database/museo.db

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

-- Insertar datos en la tabla Usuarios
INSERT INTO Usuarios (nombre, rol, identificacion, idCredenciales) VALUES
('Juan Pérez', 'Administrador', '1234567890123', 1),
('María Gómez', 'Usuario', '9876543210987', 2),
('Carlos Ruiz', 'Invitado', '1230984567890', 3);

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
INSERT INTO Secciones (secciones) VALUES
('Sala 1'),
('Sala 2'),
('Sala 3');

-- Insertar datos en la tabla Pinturas
INSERT INTO Pinturas (titulo, anio, descripcion, codigoBarras, idCategoria, idAutor, idSeccion, imagen) VALUES
('La Mona Lisa', 1503, 'Retrato de Lisa Gherardini, esposa de Francesco del Giocondo.', '1234567890123', 1, 1, 1, 'monalisa.jpg'),
('Guernica', 1937, 'Pintura que refleja los horrores de la Guerra Civil Española.', '9876543210987', 2, 2, 2, 'guernica.jpg'),
('Impresion Soleil Levant', 1872, 'Obra que dio nombre al movimiento impresionista.', '2345678901234', 3, 3, 3, 'soleillevant.jpg');
