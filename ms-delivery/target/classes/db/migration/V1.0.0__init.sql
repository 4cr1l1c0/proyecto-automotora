CREATE TABLE delivery (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    sale_id          BIGINT       NOT NULL,
    client_id        BIGINT       NOT NULL,
    delivery_date    DATE         NOT NULL,
    delivery_address VARCHAR(150) NOT NULL,
    status           VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    notes            TEXT
);

INSERT INTO delivery (sale_id, client_id, delivery_date, delivery_address, status, notes) VALUES
    (1, 1, '2025-01-20', 'Av. Principal 123, Santiago',    'DELIVERED', 'Entrega sin inconvenientes'),
    (2, 2, '2025-02-25', 'Calle Secundaria 456, Santiago', 'PENDING',   NULL);
