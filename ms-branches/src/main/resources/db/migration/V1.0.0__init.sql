CREATE TABLE branch (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    address VARCHAR(150) NOT NULL,
    phone   VARCHAR(20)  NOT NULL,
    region  VARCHAR(60)  NOT NULL,
    city    VARCHAR(60)  NOT NULL,
    active  BOOLEAN      NOT NULL DEFAULT TRUE
);

INSERT INTO branch (name, address, phone, region, city, active) VALUES
    ('Sucursal Central',    'Av. Providencia 1234', '225551001', 'Metropolitana', 'Santiago', TRUE),
    ('Sucursal Oriente',    'Av. Las Condes 5678',  '225551002', 'Metropolitana', 'Las Condes', TRUE),
    ('Sucursal Valparaíso', 'Av. Brasil 910',        '322551003', 'Valparaíso',    'Valparaíso', TRUE);
