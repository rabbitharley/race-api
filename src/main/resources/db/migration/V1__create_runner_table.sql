CREATE TABLE IF NOT EXISTS runners (
    id BIGSERIAL PRIMARY KEY, 
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(150),
    age INTEGER
);