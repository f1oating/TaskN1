DELETE FROM users;
DELETE FROM company;
INSERT INTO company(id, name) VALUES (1, 'TEST');
INSERT INTO users(id, name, password, role, company_id)
VALUES (1, 'Test', 'test', 'USER',(SELECT company.id FROM company WHERE company.name = 'TEST'));