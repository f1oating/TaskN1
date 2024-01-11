DELETE FROM users;
DELETE FROM company;
INSERT INTO company(id, name) VALUES (1, 'TEST');
INSERT INTO users(id, name, password, company_id)
VALUES (1, 'Test', 'test', (SELECT company.id FROM company WHERE company.name = 'TEST'));