-- ================================================
-- CATEGORÍAS
-- ================================================
INSERT INTO categoria (nombre, descripcion) VALUES
('Ramos', 'Arreglos florales en ramo'),
('Arreglos', 'Arreglos florales en bases o cajas'),
('Plantas', 'Plantas de interior y exterior'),
('Decoraciones', 'Accesorios y decoraciones para el hogar con temática floral'),
('Detalles', 'Pequeños regalos, peluches o chocolates para acompañar flores');

-- ================================================
-- PRODUCTOS (mínimo 15)
-- ================================================
INSERT INTO producto (nombre, descripcion, precio, stock, imagen, activo, categoria_id) VALUES
-- Categoría 1: Ramos
('Ramo de Rosas Rojas', 'Ramo de 12 rosas rojas frescas con envoltorio elegante', 14990, 10, NULL, TRUE, 1),
('Ramo de Tulipanes', 'Ramo de 10 tulipanes de colores surtidos', 12990, 8, NULL, TRUE, 1),
('Ramo Primaveral', 'Ramo con flores variadas de temporada', 13990, 12, NULL, TRUE, 1),

-- Categoría 2: Arreglos
('Arreglo de Girasoles', 'Arreglo con girasoles y follaje verde en base de vidrio', 16990, 6, NULL, TRUE, 2),
('Arreglo Romántico', 'Centro de mesa con rosas, lirios y claveles en base cerámica', 18990, 5, NULL, TRUE, 2),
('Arreglo Elegante', 'Flores blancas y verdes en base de cristal con lazo', 19990, 4, NULL, TRUE, 2),

-- Categoría 3: Plantas
('Orquídea Phalaenopsis', 'Orquídea blanca en maceta decorativa', 17990, 7, NULL, TRUE, 3),
('Suculenta Mini', 'Pequeña suculenta en maceta de cerámica', 4990, 20, NULL, TRUE, 3),
('Helecho Boston', 'Planta verde ideal para interiores, en maceta plástica', 7990, 10, NULL, TRUE, 3),

-- Categoría 4: Decoraciones
('Vela Aromática Floral', 'Vela artesanal con aroma a jazmín y rosa', 5990, 15, NULL, TRUE, 4),
('Cuadro Floral', 'Cuadro decorativo con ilustraciones de flores silvestres', 12990, 6, NULL, TRUE, 4),
('Jarrón de Cristal', 'Jarrón mediano transparente ideal para arreglos florales', 9990, 9, NULL, TRUE, 4),

-- Categoría 5: Detalles
('Peluche de Corazón', 'Peluche suave con forma de corazón', 6990, 10, NULL, TRUE, 5),
('Caja de Chocolates', 'Caja con 12 bombones artesanales', 7990, 12, NULL, TRUE, 5),
('Tarjeta Dedicatoria', 'Tarjeta personalizada con mensaje para regalo', 1990, 25, NULL, TRUE, 5);

-- ================================================
-- USUARIOS
-- ================================================
INSERT INTO usuarios (nombre, correo, password, rol) VALUES
('Admin User', 'admin@gmail.com', 'admin123', 'ADMIN'),
('Admin User 2', 'admin2@gmail.com', 'admin123', 'ADMIN'),
('María López', 'maria@gmail.com', 'user123', 'USER'),
('Juan Pérez', 'juan@gmail.com', 'user123', 'USER'),
('Carla Soto', 'carla@gmail.com', 'user123', 'USER'),
('Pedro Rivas', 'pedro@gmail.com', 'user123', 'USER'),
('Lucía Torres', 'lucia@gmail.com', 'user123', 'USER'),
('Tomás Herrera', 'tomas@gmail.com', 'user123', 'USER');
