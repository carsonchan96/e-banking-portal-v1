INSERT INTO roles (name, created_at, updated_at) 
  SELECT 'ROLE_USER',current_timestamp(6),current_timestamp(6) FROM roles
WHERE NOT EXISTS 
  (SELECT name FROM roles WHERE name='ROLE_USER')
limit 1;

INSERT INTO roles (name, created_at, updated_at) 
  SELECT 'ROLE_RM',current_timestamp(6),current_timestamp(6) FROM roles
WHERE NOT EXISTS 
  (SELECT name FROM roles WHERE name='ROLE_RM') limit 1;

INSERT INTO roles (name, created_at, updated_at) 
  SELECT 'ROLE_ADMIN',current_timestamp(6),current_timestamp(6) FROM roles
WHERE NOT EXISTS 
  (SELECT name FROM roles WHERE name='ROLE_ADMIN') limit 1;

