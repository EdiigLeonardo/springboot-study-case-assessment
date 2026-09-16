CREATE TABLE announcement (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(255),
  body VARCHAR(2000),
  created_at TIMESTAMP,
  created_by VARCHAR(255),
  updated_at TIMESTAMP,
  updated_by VARCHAR(255)
);
