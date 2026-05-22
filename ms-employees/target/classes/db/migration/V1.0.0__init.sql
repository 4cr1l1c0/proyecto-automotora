CREATE TABLE employee (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50)    NOT NULL,
    last_name  VARCHAR(50)    NOT NULL,
    rut        VARCHAR(12)    NOT NULL UNIQUE,
    email      VARCHAR(100)   NOT NULL UNIQUE,
    phone      VARCHAR(20)    NOT NULL,
    position   VARCHAR(60)    NOT NULL,
    salary     DECIMAL(12, 2) NOT NULL,
    hire_date  DATE           NOT NULL,
    active     BOOLEAN        NOT NULL DEFAULT TRUE,
    branch_id  BIGINT         NOT NULL
);

INSERT INTO employee (first_name, last_name, rut, email, phone, position, salary, hire_date, active, branch_id) VALUE
    ('Pedro',  'Díaz',    '11111111-1', 'pedro.diaz@automotora.cl',    '912000001', 'Vendedor',  1200000.00, '2022-03-01', TRUE, 1),
    ('Lucía',  'Vargas',  '22222222-2', 'lucia.vargas@automotora.cl',   '912000002', 'Vendedora', 1200000.00, '2021-06-15', TRUE, 1),
    ('Roberto','Fuentes', '33333333-3', 'roberto.fuentes@automotora.cl','912000003', 'Gerente',   2500000.00, '2019-01-10', TRUE, 2);
