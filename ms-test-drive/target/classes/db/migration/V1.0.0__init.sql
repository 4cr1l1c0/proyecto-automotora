CREATE TABLE test_drive_visit (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id  BIGINT      NOT NULL,
    vehicle_id BIGINT      NOT NULL,
    visit_date DATE        NOT NULL,
    notes      TEXT,
    status     VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED'
);

INSERT INTO test_drive_visit (client_id, vehicle_id, visit_date, notes, status) VALUES
    (1, 1, '2025-03-10', 'Cliente interesado en financiamiento', 'COMPLETED'),
    (2, 2, '2025-03-15', NULL,                                    'SCHEDULED');
