INSERT INTO person (admin, user_description, user_email, hidden, user_name)
VALUES (true, 'test1','ola@norman.com', false, 'Ola Nordmann');
INSERT INTO person (admin, user_description, user_email, hidden, user_name)
VALUES (false, 'test2','kari@norman.com', true, 'Kari Nordmann');
INSERT INTO person (admin, user_description, user_email, hidden, user_name)
VALUES (false, 'test3','smith@test.com', false, 'John Smith');
INSERT INTO project (project_category, project_image, project_link, project_name, summary, projects_owned)
VALUES ('Film', 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/Baby_Mother_Grandmother_and_Great_Grandmother.jpg/800px-Baby_Mother_Grandmother_and_Great_Grandmother.jpg',
        'placeholder', 'Filmen om filmen om generasjoner', 'Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip.', 1);
INSERT INTO project (project_category, project_image, project_link, project_name, summary, projects_owned)
VALUES ('Web', 'https://image.klikk.no/3026636.jpg?imageId=3026636&x=0&y=0&cropw=undefined&croph=undefined&width=1000&height=570',
        'placeholder', 'Remake av Norges første nettside', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua', 2);
INSERT INTO project (project_category, project_image, project_link, project_name, summary, projects_owned)
VALUES ('Musikk', 'https://premium.vgc.no/v2/images/fab20785-874d-4b48-a3c8-cdef1e8aa006?fit=crop&format=auto&h=1425&w=1900&s=642c3d1003e6a2066cc414a534b32df6d83ca699',
        'placeholder', 'Syngende Postbud - Et prosjekt av Posten',
        'Vi i Posten har i år startet prosjektet der vi tester ut syngende Postbud rundt omkring i landet. Som en del av prosjektet komponerer vi sangene som postbudene bruker selv.', 2);
INSERT INTO project (project_category, project_image, project_link, project_name, summary, projects_owned)
VALUES ('Spill', 'https://www.nfi.no/tildelinger-2016-til-2020/produksjon/dokumentar/mannen-fra-snasa/_/image/22dc0ab8-d013-4683-81a5-68e8ec5fe80b:524e6a3e472821a5ce76decae82dc19eda128443/width-1920/Mannen-fra-snåsa-1920x1080.jpg.jpeg',
        'placeholder', 'Spillet om Snåsamannen','Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua', 3);
INSERT INTO comment (text, time, project_id, user_id)
VALUES ('Hei, dette går ikke', '01-01-2001', 1, 1);
INSERT INTO comment (text, time, project_id, user_id)
VALUES ('Dette er den siste meldingen', '01.01.01', 1, 2);
INSERT INTO comment (text, time, project_id, user_id)
VALUES ('What is the meaning of life', '01.01.01', 2, 2);
INSERT INTO application (application_status, time, project_id, user_id)
VALUES ('Venter', '01.September 2014',1,1);
INSERT INTO application (application_status, time, project_id, user_id)
VALUES ('Venter', '02.September 2016',2,1);
INSERT INTO application (application_status, time, project_id, user_id)
VALUES ('Venter', '03.September 2015',3,1);
INSERT INTO application (application_status, time, project_id, user_id)
VALUES ('Venter', '05.September 2017',4,2);
INSERT INTO application (application_status, time, project_id, user_id)
VALUES ('Venter', '07.September 2019',4,3);

