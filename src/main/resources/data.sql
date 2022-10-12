INSERT INTO person (admin, user_description, user_email, hidden, user_name)
VALUES (false, 'test','smith@testx.com', false, 'John Smith');
INSERT INTO project (project_category, project_image, project_link, project_name, summary, projects_owned)
VALUES ('Musikk', 'placeholder', 'placeholder', 'Music of the Spheres', 'Godly sound', 1);
INSERT INTO comment (text, time, project_id, user_id)
VALUES ('what is the meaning of life', '01.01.01', 1, 1);
INSERT INTO application (application_status, time, project_id, user_id)
VALUES ('Venter', '02.September 2014',1,1);
INSERT INTO person (admin, user_description, user_email, hidden, user_name)
VALUES (true, 'test2','smith2@test.com',false, 'John Smith2');
INSERT INTO person (admin, user_description, user_email, hidden, user_name)
VALUES (false, 'test3','smith3@test.com',true, 'John Smith3');
