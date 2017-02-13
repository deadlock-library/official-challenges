-- Gender
CREATE TYPE GENDER AS ENUM ('male', 'female');
-- User profiles
CREATE TABLE public."user"
(
    id SERIAL PRIMARY KEY NOT NULL,
    nickname VARCHAR(64) NOT NULL,
    name VARCHAR(64) NOT NULL,
    description VARCHAR(255),
    email VARCHAR(64),
    gender GENDER NOT NULL,
    age INT NOT NULL,
    interested_in GENDER NOT NULL,
    subscribe_date DATE NOT NULL,
    latitude FLOAT,
    longitude FLOAT
);
CREATE UNIQUE INDEX user_id_uindex ON public."user" (id);
