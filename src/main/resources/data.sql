INSERT INTO person (admin, user_desciption, user_email, hidden, user_name, password)
VALUES (false, 'test','smith@testx.com', false, 'John Smith', 'pro123');
INSERT INTO project (project_category, project_image, project_link, project_name, summary, tags, owner_user_id)
VALUES ('Musikk', 'placeholder', 'placeholder', 'Music of the Spheres', 'Godly sound', 'Christian, God',
        1);
INSERT INTO comment (text, time, project_project_id, user_user_id)
VALUES ('what is the meaning of life', '01.01.01', 1, 1);
INSERT INTO application (application_status, time, project_project_id, user_user_id)
VALUES ('Venter', '02.September 2014',1,1);
INSERT INTO person (admin, user_desciption, user_email, hidden, user_name, password)
VALUES (true, 'test2','smith2@test.com',false, 'John Smith2', 'pro234');
INSERT INTO person (admin, user_desciption, user_email, hidden, user_name, password)
VALUES (false, 'test3','smith3@test.com',true, 'John Smith3', 'pro345');
