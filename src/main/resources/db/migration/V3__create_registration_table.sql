CREATE TABLE IF NOT EXISTS registrations ( 
     id BIGSERIAL PRIMARY KEY,
     runner_id BIGINT NOT NULL,
     race_id BIGINT NOT NULL,
     registration_date DATE NOT NULL,

     CONSTRAINT fk_runner FOREIGN KEY (runner_id) REFERENCES runners(id),
     CONSTRAINT fk_race FOREIGN KEY (race_id) REFERENCES races(id),
     CONSTRAINT unique_registration UNIQUE (runner_id, race_id)
);