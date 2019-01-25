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


INSERT INTO translation_key (id, name, description , projects_id) VALUES
  (1, 'test.title', 'test title' , 1 ),
  (2, 'test.subtitle', 'test subtitle', 1 ),
  (3, 'test.title', 'title' , 2 ),
  (4, 'test.title', 'title' , 4);

INSERT INTO translation_translation (id, content, projects_id, locales_id, keys_id) VALUES
    (1, 'Test Title', 1, 1, 1),
    (2, 'Test Subtitle', 1, 1, 2),
    (3, 'Test test', 2, 1, 3 ),
    (4, 'Test test', 4, 1, 4);