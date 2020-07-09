# OCR-06 Application PayMyBuddy
This application is a web application for money transfer between relatives
In order to run the application, you will need:

- **Java JDK14**
- **mySql 8.0**

The application has been coded with:

- Spring
- dataJpa (Java Persistence Api)
- Spring Security (for the management of the multi-session)
- Hibernate (for database simulation for the tests)
- Junit, assertJ, mockito (for the tests)

In order to execute the application, a .jar has been generated, you need to specify the name and the password variable when you launch the application:

You can specify the two variable previously by registered in your variable environnement:

- Variable: DB_Username / Value: "yourDatabaseName"
- Variable: DB_Password / Value: "yourDatabasePasword"

Or you can write directly the following command with the specified variables:

- **java -DDB_Username="yourDatabaseName" -DDB_Password="yourDatabasePasword" -jar transferapp.jar** .

At the start of the application, the server will be deploy on  **"localhost:8080"** , this endpoint will lead you on the homePage.

You will find the data-Init mysql script (injecting the data for the tests) and other annex files in the database_feature folder.

The application is developed with JPA, you just need to create a sql Schema with the name 'transferapp', and all the tables will be created automatically at the start of the application.

For the good running of the application, some user constraint as been added like:

- a password constraint (8 to 20 character)
- a maximum amount of money deposed on each account (10000E)
- a filter security access for the endpoint define in the config of spring-Security

The password is saved encrypted in the database with the tool Spring Security.crypto (BCryptPasswordEncoder)

Here is the UML diagram:
![alt text](https://raw.githubusercontent.com/Kerbetth/OCR-06/master/database_feature/Uml_transferapps.png "UML")

I hope you will have a good experience.
