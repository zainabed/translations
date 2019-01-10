INSERT INTO translation_project (id, name, description) VALUES
  (134545, 'Nordea brand', 'Nordea brand project'),
  (254545, 'Nordea corporate', 'Nordea corporate project');

INSERT INTO translation_locale (id, name, code ) VALUES
  (22323, 'Swedish', 'sv'),
  (54533, 'English', 'en'),
  (22324, 'Finnish', 'fi'),
  (22325, 'Danish', 'da'),
  (22326, 'Norwegian', 'nb');
  
  
INSERT INTO project_locales(project_id, locale_id) VALUES 
(134545,54533),
(134545,22323);

INSERT INTO translation_key (id, name, description , projects_id) VALUES
  (1, 'app.title', 'Application title' , 134545),
  (2, 'app.lang.en', 'Application language english', 134545),
  (3, 'app.lang.sv', 'Application language swedish' , 134545),
  (4, 'login.title', 'Login title' , 134545),
  (5, 'login.description', 'Login description' , 134545),
  (6, 'login.ssn.label', 'Login SSN field label' , 134545),
  (7, 'login.instruction', 'Login instruction' , 134545),
  (8, 'login.instruction.option.one', 'Login instruction option one' , 134545),
  (9, 'login.instruction.option.two', 'Login instruction option two' , 134545);
  
INSERT INTO translation_translation (id, content, projects_id, locales_id, keys_id) VALUES
    (1, 'Nordea Brand', 134545, 54533, 1),
    (2, 'English', 134545, 54533, 2),
    (3, 'Svenska', 134545, 54533, 3),
    (4, 'Login', 134545, 54533, 4),
    (5, 'Log in with your Mobile BankID', 134545, 54533, 5),
    (6, 'User ID', 134545, 54533, 6),
    (7, 'Mobile BankID', 134545, 54533, 7),
    (8, 'Log on as usual.', 134545, 54533, 8),
    (9, 'If you have access to Nordea Business, you will be forwarded to the service. After login, you can access Internetbanken Företag via a link below Other services.', 134545, 54533, 9),
    (10, 'Nordea Brand', 134545, 22323, 1),
    (11, 'English', 134545, 22323, 2),
    (12, 'Svenska', 134545, 22323, 3),
    (13, 'Logga in', 134545, 22323, 4),
    (14, 'Logga in med Mobilt BankID', 134545, 22323, 5),
    (15, 'Personnummer', 134545, 22323, 6),
    (16, 'Mobilt BankID', 134545, 22323, 7),
    (17, 'Logga in.', 134545, 22323, 8),
    (18, 'Har du tillgång till Nordea Business hamnar du direkt inne i tjänsten. Du kan använda Internetbanken Företag genom att klicka på Internetbanken Företag under Övriga tjänster.', 134545, 22323, 9);

INSERT INTO translation_user(id, username, password, email) VALUES 
(1, 'admin', '1234', 'admin@test.org'),
(2, 'testuser', '1234', 'testuser@test.org'),
(3, 'testsupport', '1234', 'testsupport@test.org'),
(4, 'testtranslator', '1234', 'testtranslator@test.org'),
(5, 'testpo', '1234', 'testpo@test.org');

INSERT INTO role(id,name) VALUES 
(1, "ROLE_ADMIN"),
(2, "ROLE_USER"),
(3, "ROLE_SUPPOIRT"),
(4, "ROLE_TRANSLATOR"),
(5, "ROLE_PO");

INSERT INTO user_roles(user_id, role_id) VALUES 
(1, 1),
(1, 2),
(2, 2),
(3, 2),
(3, 3),
(4, 2),
(4, 4),
(5, 2),
(5, 5);