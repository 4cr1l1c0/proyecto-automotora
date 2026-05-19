CREATE TABLE client (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut             VARCHAR(12)  NOT NULL UNIQUE,
    primer_nombre   VARCHAR(30)  NOT NULL,
    segundo_nombre  VARCHAR(30),
    apellido_paterno VARCHAR(30) NOT NULL,
    apellido_materno VARCHAR(30) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    telefono        VARCHAR(9)   NOT NULL,
    direccion       VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE         NOT NULL,
    activo_cliente  BOOLEAN      NOT NULL DEFAULT TRUE
);

INSERT INTO client (rut, primer_nombre, segundo_nombre, apellido_paterno, apellido_materno, email, telefono, direccion, fecha_nacimiento, activo_cliente)
VALUES
    ('12345678-9', 'Juan', 'Carlos',  'Pérez',  'González', 'juan.perez@automotora.cl',  '912345678', 'Av. Principal 123',    '1990-05-15', TRUE),
    ('98765432-1', 'María', NULL,      'López',  'Rodríguez','maria.lopez@automotora.cl', '987654321', 'Calle Secundaria 456', '1985-08-22', TRUE);
