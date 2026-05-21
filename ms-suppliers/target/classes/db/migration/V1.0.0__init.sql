CREATE TABLE supplier (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    rut          VARCHAR(12)  NOT NULL UNIQUE,
    email        VARCHAR(100) NOT NULL UNIQUE,
    phone        VARCHAR(20)  NOT NULL,
    address      VARCHAR(150) NOT NULL,
    contact_name VARCHAR(100) NOT NULL,
    active       BOOLEAN      NOT NULL DEFAULT TRUE
);

INSERT INTO supplier (name, rut, email, phone, address, contact_name, active) VALUES
    ('Toyota Chile S.A.',   '76543210-1', 'contacto@toyota.cl',   '225560001', 'Av. Apoquindo 3000, Santiago', 'Carlos Muñoz',  TRUE),
    ('Chevrolet Distribuidora', '65432109-2', 'ventas@chevrolet.cl', '225560002', 'Av. Kennedy 4500, Santiago',  'Ana Soto',      TRUE);
