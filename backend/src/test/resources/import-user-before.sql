DELETE
FROM users;

DELETE
FROM projects;


INSERT INTO users(username, email, first_name, last_name, password, status)
VALUES ('jaje', 'test1@mail.ru', 'Jacob', 'Jeferson',
        '$2y$05$4.PCIrlgJcBm2TZx7FjJs.jLrIQ1WzdRxoV/viT2kGtaqcIARvVgO', 'ACTIVE');


INSERT INTO users(username, email, first_name, last_name, password, status)
VALUES ('testAccount', 'doesntmatter@bib.com', 'Test', 'User',
        '$2y$05$/Mub3W/zUQ.hwJBSh3icLudzP0BpIjnRli8MdndmIdhOL6YcLdHJa', 'ACTIVE');

INSERT INTO roles(name)
VALUES ('ROLE_USER')



