DELETE
FROM users;

INSERT INTO users(username, email, first_name, last_name, password, status)
VALUES ('jaje', 'test1@mail.ru', 'Jacob', 'Jeferson',
        '$2y$05$4.PCIrlgJcBm2TZx7FjJs.jLrIQ1WzdRxoV/viT2kGtaqcIARvVgO', 'ACTIVE');

INSERT INTO roles(name)
VALUES ('ROLE_USER')

