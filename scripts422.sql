-- Таблица людей
CREATE TABLE person
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(100) NOT NULL,
    age                INTEGER CHECK (age > 0),
    has_driver_license BOOLEAN DEFAULT FALSE
);

-- Таблица машин
CREATE TABLE car
(
    id    SERIAL PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    price DECIMAL(12, 2) CHECK (price >= 0)
);

-- Многие ко многим
CREATE TABLE person_car
(
    person_id INTEGER REFERENCES person (id) ON DELETE CASCADE,
    car_id    INTEGER REFERENCES car (id) ON DELETE CASCADE,
    PRIMARY KEY (person_id, car_id)
);