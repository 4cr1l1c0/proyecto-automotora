CREATE TABLE vehicle (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    vin     VARCHAR(17)    NOT NULL UNIQUE,
    brand   VARCHAR(50)    NOT NULL,
    model   VARCHAR(60)    NOT NULL,
    year    INT            NOT NULL,
    color   VARCHAR(30)    NOT NULL,
    price   DECIMAL(12, 2) NOT NULL,
    status  VARCHAR(20)    NOT NULL DEFAULT 'AVAILABLE',
    type    VARCHAR(30)    NOT NULL,
    mileage INT            NOT NULL DEFAULT 0
);

INSERT INTO vehicle (vin, brand, model, year, color, price, status, type, mileage) VALUES
    ('1HGBH41JXMN109186', 'Toyota',   'Corolla',   2023, 'Blanco', 15000000.00, 'AVAILABLE', 'Sedán',  0),
    ('2T1BURHE0JC021545', 'Chevrolet','Tracker',   2022, 'Gris',   18000000.00, 'AVAILABLE', 'SUV',    5000),
    ('3FADP4BJ5KM123456', 'Hyundai',  'Tucson',    2024, 'Negro',  22000000.00, 'AVAILABLE', 'SUV',    0);
