CREATE TABLE insurance (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    policy_number   VARCHAR(50)    NOT NULL UNIQUE,
    type            VARCHAR(50)    NOT NULL,
    coverage_amount DECIMAL(15,2)  NOT NULL,
    premium         DECIMAL(10,2)  NOT NULL,
    start_date      DATE           NOT NULL,
    end_date        DATE           NOT NULL,
    vehicle_id      BIGINT         NOT NULL,
    client_id       BIGINT         NOT NULL,
    active          BOOLEAN        NOT NULL DEFAULT TRUE
);

INSERT INTO insurance (policy_number, type, coverage_amount, premium, start_date, end_date, vehicle_id, client_id, active) VALUES
    ('POL-001', 'Automóvil', 10000.00, 500.00, '2026-01-01', '2027-01-01', 101, 201, TRUE),
    ('POL-002', 'Moto',      5000.00, 250.00, '2026-02-15', '2027-02-15', 102, 202, TRUE),
    ('POL-003', 'Camión',   20000.00, 1200.00,'2026-03-10', '2027-03-10', 103, 203, FALSE);
