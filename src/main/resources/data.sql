INSERT INTO person (user_id, user_description, user_email, hidden, user_name)
VALUES (1, 'test1','ola@norman.com', false, 'Ola Nordmann');
INSERT INTO person (user_id, user_description, user_email, hidden, user_name)
VALUES (2, 'test2','kari@norman.com', true, 'Kari Nordmann');
INSERT INTO person (user_id, user_description, user_email, hidden, user_name)
VALUES (3, 'test3','smith@test.com', false, 'John Smith');
INSERT INTO project (project_category, project_link, project_name, summary, projects_owned)
VALUES ('FILM',
        'placeholder', 'Filmen om filmen om generasjoner', 'Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip.', 1);
INSERT INTO project (project_category, project_link, project_name, summary, projects_owned)
VALUES ('WEB', 'placeholder', 'Remake av Norges første nettside', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua', 2);
INSERT INTO project (project_category, project_link, project_name, summary, projects_owned)
VALUES ('MUSIC',
        'placeholder', 'Syngende Postbud - Et prosjekt av Posten',
        'Vi i Posten har i år startet prosjektet der vi tester ut syngende Postbud rundt omkring i landet. Som en del av prosjektet komponerer vi sangene som postbudene bruker selv.', 2);
INSERT INTO project (project_category, project_link, project_name, summary, projects_owned)
VALUES ('GAME', 'placeholder', 'Spillet om Snåsamannen','Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua', 3);
INSERT INTO comment (text, time, project_id, user_id)
VALUES ('Hei, dette går ikke', '01-01-2001', 1, 1);
INSERT INTO comment (text, time, project_id, user_id)
VALUES ('Dette er den siste meldingen', '01.01.01', 1, 2);
INSERT INTO comment (text, time, project_id, user_id)
VALUES ('What is the meaning of life', '01.01.01', 2, 2);
