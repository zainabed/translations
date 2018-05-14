INSERT INTO translation_project (id, name, description) VALUES
  (1, 'First Card Admin', 'First Card Admin Description'),
  (2, 'IW Project', 'IW Project Description');

INSERT INTO translation_locale (id, name, code , projects_id) VALUES
  (1, 'Swedish', 'sv-SE' , 1),
  (2, 'Swedish English', 'sv-EN', 1);

INSERT INTO translation_key (id, name, description , projects_id) VALUES
  (1, 'f.login.title', 'Login page title' , 1),
  (2, 'f.login.subtitle', 'Login page subtitle', 1),
  (3, 'f.login.description', 'Login page description' , 1),
  (4, 'f.login.footer', 'Login page footer' , 1),
  (5, 'g.button.ok', 'OK button' , 1);