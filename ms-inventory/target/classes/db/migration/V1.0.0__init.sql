CREATE TABLE inventory_item (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id   BIGINT       NOT NULL,
    quantity     INT          NOT NULL DEFAULT 0,
    min_quantity INT          NOT NULL DEFAULT 1,
    location     VARCHAR(100) NOT NULL,
    last_updated DATE         NOT NULL
);

INSERT INTO inventory_item (vehicle_id, quantity, min_quantity, location, last_updated) VALUES
    (1, 3, 1, 'Bodega Central - Santiago',    '2025-01-01'),
    (2, 5, 2, 'Bodega Oriente - Las Condes',  '2025-01-01'),
    (3, 2, 1, 'Bodega Central - Santiago',    '2025-01-01');
