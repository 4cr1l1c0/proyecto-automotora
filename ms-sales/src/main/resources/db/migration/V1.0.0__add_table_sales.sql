CREATE TABLE sale (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    sale_date    DATE           NOT NULL,
    subtotal     DECIMAL(12, 2) NOT NULL,
    iva          DECIMAL(12, 2) NOT NULL,
    total        DECIMAL(12, 2) NOT NULL,
    payment_type VARCHAR(30)    NOT NULL,
    client_id    BIGINT         NOT NULL,
    vehicle_id   BIGINT         NOT NULL,
    employee_id  BIGINT         NOT NULL
);

INSERT INTO sale (sale_date, subtotal, iva, total, payment_type, client_id, vehicle_id, employee_id)
VALUES
    ('2025-01-15', 16806722.69, 3193277.31, 20000000.00, 'Efectivo',     1, 1, 1),
    ('2025-02-20', 12605042.02, 2394957.98, 15000000.00, 'Transferencia', 2, 2, 1);
