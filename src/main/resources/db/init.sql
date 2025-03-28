CREATE TABLE IF NOT EXISTS empleados (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  apellido VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE NOT NULL,
  telefono VARCHAR(15),
  salario DECIMAL(10, 2) NOT NULL
);
-- Insert sample data
INSERT INTO empleados (nombre, apellido, email, telefono, salario)
VALUES (
    'Juan',
    'Pérez',
    'juan.perez@example.com',
    '555-1234',
    35000.00
  ),
  (
    'María',
    'González',
    'maria.gonzalez@example.com',
    '555-5678',
    42000.00
  ),
  (
    'Carlos',
    'Rodríguez',
    'carlos.rodriguez@example.com',
    '555-9012',
    38500.00
  ),
  (
    'Ana',
    'Martínez',
    'ana.martinez@example.com',
    '555-3456',
    40000.00
  ),
  (
    'Luis',
    'Sánchez',
    'luis.sanchez@example.com',
    '555-7890',
    36500.00
  );