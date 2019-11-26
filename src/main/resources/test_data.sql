INSERT INTO translation_user(id, username, password, email) VALUES
 (1, 'test', '123', 'admin@test.org');

INSERT INTO role(id,name) VALUES
 (100, 'ROLE_ADMIN'),
(200, 'ROLE_USER'),
(300, 'ROLE_SUPPOIRT'),
(400, 'ROLE_TRANSLATOR'),
(500, 'ROLE_PO');



INSERT INTO translation_project (id, name, description) VALUES
  (1, 'Test Project', 'Test Project Description.'),
  (2, 'Test Project', 'Test Project for get test.'),
  (3, 'Test Project', 'Test Project for delete test.'),
  (4, 'Test Project', 'Test Project Description.');

INSERT INTO translation_locale (id, name, code) VALUES
  (1, 'Swedish', 'sv-SE'),
  (2, 'Swedish English', 'sv-EN');

  
INSERT INTO project_locales(project_id, locale_id) VALUES
(1,1),
(1,2),
(4,1);


INSERT INTO user_project_role(id, users_id, projects_id, roles_id) VALUES
(1, 1, 1, 100),
(2, 1, 1, 200);