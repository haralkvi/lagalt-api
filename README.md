## LAGALT.NO API

### Hva er dette?

Dette er API/Backend for Lagalt.no. Frontend er en seperat repo.
Lagalt API innkluderer en rekke API endpoints som utnyttes av denne frontenden.
Lagalt utnytter seg av Java med Spring Boot, med en Postgresql database. Hibernate
blir brukt til å forenkle operasjoner med databasen. Entiteter i databasen genereres
ved start av applikasjonen fra definerte modeller. Backend benytter seg av også
controller-service-repository mønstret. 

### Kjente bugs/problemer
--

### Hvem arbeidet med dette?

Lagalt gruppen består av:
- Magnus Smeby
- Renate Karlsen
- Sevde Oguz
- Harald Kvisli
- Marius Olafsen

De to sistnevnte var involvert i utvikling av dette API (backend-team).

### Deployment

The API is deployed to heroku at: 
https://lagalt-api.herokuapp.com/api/

Example of an endpoint: https://lagalt-api.herokuapp.com/api/v1/users/

Other endpoints include CRUD operations for all entities:
Users, Applications, Projects and Comments

### What is this?

This is the API/Backend for Lagalt.no. Frontend is in a separate repo.
Lagalt API includes a number of API endpoints that are utilized  by this frontend.
Lagalt makes use of Java with Spring Boot, with a Postgresql database. Hibernate
is used to simplify operations with this database. Entities in the database are generated
when starting the application from defined models. Backend makes use of the
controller-service-repository pattern.

### Known problems
--

### Who worked on this?

The group involved with making this project:
- Magnus Smeby
- Renate Karlsen
- Sevde Oguz
- Harald Kvisli
- Marius Olafsen

The last two mentioned was part of the backend team and therefore made this API.
