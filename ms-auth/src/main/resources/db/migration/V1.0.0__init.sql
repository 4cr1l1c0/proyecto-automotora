CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL DEFAULT 'USER',
                       active BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO users (username, password, role, active)
VALUES('admin', '$2a$10$EblZqNptyYxnSrwvOe/h2.aDtBfWa6KETcD8eXkFOhO9CkA/W3jj', 'ADMIN', true);