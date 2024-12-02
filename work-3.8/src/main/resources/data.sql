CREATE TABLE contacts(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(32),
    last_name VARCHAR(32),
    email VARCHAR(320),
    phone VARCHAR(32)
);

INSERT INTO contacts(first_name, last_name, email, phone)
    VALUES('John', 'Doe', 'johndoe@mail.example', '89205553535'),
          ('Ann', 'Doe', 'anndoe@mail.example', '89206363636'),
          ('Daria', 'Tenckacheva', 'dariatenckacheva@mail.example', '89512145467');