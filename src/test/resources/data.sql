INSERT INTO translation_project (id, name, description) VALUES
  (1, 'Test Project', 'Test Project Description.'),
  (2, 'Test Project', 'Test Project for get test.'),
  (3, 'Test Project', 'Test Project for delete test.');
  (4, 'Test Project', 'Test Project Description.'),

INSERT INTO translation_locale (id, name, code) VALUES
  (1, 'Swedish', 'sv-SE'),
  (2, 'Swedish English', 'sv-EN');

  
INSERT INTO project_locales(project_id, locale_id) VALUES 
(1,1),
(1,2),
(4,1);