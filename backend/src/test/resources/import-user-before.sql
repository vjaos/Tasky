DELETE
FROM user_roles;
DELETE
FROM users;

INSERT INTO users(email, first_name, last_name, password, username)
VALUES ('test1@mail.ru', 'Jacob', 'Jeferson',
        '$2y$05$4.PCIrlgJcBm2TZx7FjJs.jLrIQ1WzdRxoV/viT2kGtaqcIARvVgO',
        'jaje');



