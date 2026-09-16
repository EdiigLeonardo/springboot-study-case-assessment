CREATE TABLE users (id BIGSERIAL PRIMARY KEY, email VARCHAR(255) UNIQUE, password VARCHAR(255), role VARCHAR(20));
INSERT INTO users (email, password, role) VALUES ('admin@siemens.com', '{noop}admin123', 'ADMIN');
