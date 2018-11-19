INSERT INTO translation_project (id, name, description) VALUES
  (134545, 'First Card Admin', 'First Card offers a complete payment system for all purchases and expenses in the service. For companies that want a fast, economical and secure administration of invoices and travel bills. First Card makes it easy for the traveler to write travel calculations and gives the company total control and aggregate travel statistics. The card can be obtained with connection to MasterCard or Visa.'),
  (254545, 'IW Project', 'IW Project Description');

INSERT INTO translation_locale (id, name, code , projects_id) VALUES
  (54533, 'Swedish', 'sv-SE' , 134545),
  (22323, 'Swedish English', 'sv-EN', 134545);

INSERT INTO translation_key (id, name, description , projects_id) VALUES
  (1, 'F_Lo_Title', 'Login page title' , 134545),
  (2, 'F_Lo_Subtitle', 'Login page subtitle', 134545),
  (3, 'F_Lo_Placeholder', 'SSN input box placehoder text' , 134545),
  (4, 'F_Lo_phonenumber', 'First card support phone number details' , 134545),
  (5, 'F_Lo_LoginButton', 'Login button text' , 134545),
  (6, 'F_Lo_InstructionHeading', 'Login instruction heading' , 134545),
  (7, 'F_Lo_Instruction', 'Login instruction' , 134545),
  (8, 'F_Lo_Inputfieldlable', 'Login input box lable' , 134545);
  
INSERT INTO translation_translation (id, content, projects_id, locales_id, keys_id) VALUES 
  (1, 'Logga in', 134545, 54533, 1),
  (2, 'Använd Mobilt BankID för att logga in', 134545, 54533, 2),
  (3, 'ååååmmddxxxx', 134545, 54533, 3),
  (4, 'First Card, kundservice +46 771 40 71 70 ', 134545, 54533, 4),
  (5, 'Logga in', 134545, 54533, 5),
  (6, 'Så här loggar du in', 134545, 54533, 6),
  (7, 'Ange ditt personnummer och klicka på Logga in. Starta BankID-appen på din mobil eller surfplatta och ange din säkerhetskod.', 134545, 54533, 7),
  (8, 'Personnummer', 134545, 54533, 8),
  (9, 'Log in', 134545, 22323, 1),
  (10, 'Use Mobile BankID to log in', 134545, 22323, 2),
  (11, 'yyyymmddxxxx', 134545, 22323, 3),
  (12, 'First Card Customer Support +46 771 40 71 70  ', 134545, 22323, 4),
  (13, 'Log in', 134545, 22323, 5),
  (14, 'How to log in?', 134545, 22323, 6),
  (15, 'Enter your social security number and press Log in. Start the BankID app on your mobile or tablet and enter your security code.', 134545, 22323, 7),
  (16, 'Social security number', 134545, 22323, 8);
  
INSERT INTO translation_user(username, password, email) VALUES 
('zainabed', '1234', 'zainabed@test.org'),
('testuser', '1234', 'testuser@test.org');