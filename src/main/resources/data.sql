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
INSERT INTO application (time, project_id, user_id)
VALUES ('01.September 2014',1,1);
INSERT INTO application (time, project_id, user_id)
VALUES ('02.September 2016',2,1);
INSERT INTO application (time, project_id, user_id)
VALUES ('03.September 2015',3,1);
INSERT INTO application (time, project_id, user_id)
VALUES ('05.September 2017',4,2);
INSERT INTO application (time, project_id, user_id)
VALUES ('07.September 2019',4,3);

INSERT INTO  user_skill_set (user_user_id, skill_set)
VALUES ('1', 'guitar');
INSERT INTO  user_skill_set (user_user_id, skill_set)
VALUES ('1', 'football');
INSERT INTO  user_skill_set (user_user_id, skill_set)
VALUES ('1', 'java');
INSERT INTO  user_skill_set (user_user_id, skill_set)
VALUES ('1', 'python');
INSERT INTO  user_skill_set (user_user_id, skill_set)
VALUES ('1', 'programming');
INSERT INTO  user_skill_set (user_user_id, skill_set)
VALUES ('1', 'react');

INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('2', 'backend');
INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('2', 'spring');
INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('2', 'hibernate');
INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('2', 'sql');
INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('2', 'design');
INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('2', 'maths');

INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('3', 'guitar');
INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('3', 'drums');
INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('3', 'bass');
INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('3', 'singing');
INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('3', 'art');
INSERT INTO user_skill_set (user_user_id, skill_set)
VALUES ('3', 'design');

INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (1, 'frontend');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (1, 'programming');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (1, 'react');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (1, 'angular');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (1, 'UX');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (1, 'design');

INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (2, 'guitar');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (2, 'oboe');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (2, 'flute');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (2, 'drums');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (2, 'leadership');

INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (3, 'backend');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (3, 'database');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (3, 'java');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (3, 'design');
INSERT INTO project_skills_needed (project_project_id, skills_needed)
VALUES (3, 'spring');