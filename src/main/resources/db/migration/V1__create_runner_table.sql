CREATE TABLE runners (
                         id SERIAL PRIMARY KEY,
                         first_name VARCHAR(100),
                         last_name VARCHAR(100),
                         email VARCHAR(150),
                         age INTEGER
);

CREATE TABLE races (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(200),
                       date DATE,
                       location VARCHAR(200),
                       max_participants INTEGER
);

CREATE TABLE registrations (
                               id SERIAL PRIMARY KEY,
                               runner_id BIGINT REFERENCES runners(id),
                               race_id BIGINT REFERENCES races(id),
                               registration_date DATE
);