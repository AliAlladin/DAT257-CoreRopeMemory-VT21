# TA-Portal
This is the repository of group Core Rope Memory’s project in the course Agile software project management (DAT257) VT21.  
* [Scrum board]()
* [Documents](/Documents/)

## Description
TA-portal is a web application that digitizes the course time report for teaching assistants at Chalmers University of Technology in Gothenburg, Sweden. After downloading the application users can create an account and start logging their work shifts whenever they wish. At the end of the month a filled-out time report can be downloaded and then easily signed with e.g. Adobe Acrobat Reader to finally be handed over to the course responsible. The application is made with [Spring Boot]( https://spring.io/web-applications).

## Contributors
* [Ali Alladin](https://github.com/AliAlladin)
* [Samuel Dahlberg](https://github.com/Samdahl)
* [Oliver Ljung](https://github.com/l5hy)
* [Dylan Osolian](https://github.com/Nalyd1019)
* [Jonathan Nilsson](https://github.com/JWN1998)
* [Simon Granerus Wiberg](https://github.com/simongranerus)

## Requirements
* [Java](https://www.oracle.com/java/technologies/javase-downloads.html) (11 or newer)
* [Maven](https://maven.apache.org/download.cgi)
* [PostgreSQL](https://www.postgresql.org/download/) 

## Installation
1. Clone this repository.
### Setting up the database in the terminal
2. Open the terminal.
3. Start the PostgreSQL server.
    * MacOS: 
        ```bash
        pg_ctl -D /usr/local/var/postgres start
        ```
    * Windows:  
        ```bash
        pg_ctl -D "C:\Program Files\PostgreSQL\9.6\data" start
        ```
3. Enter the postgres shell.
    ```bash
    psql postgres (username)
    ```
4. Create the database.
    ```SQL
    CREATE DATABASE taportal;
    ```
### Connecting the database with the application
5. Change the database values (username and password) in the [application.properties](/TA-Portal/src/main/resources/application.properties) file.
    ```properties
    server.port=9090
    spring.datasource.url=jdbc:postgresql://localhost:5432/taportal
    spring.datasource.username= YOUR_USERNAME
    spring.datasource.password= YOUR_PASSWORD
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.properties.hibernate.format_sql=true
    ```
## Running the application
1. Open the terminal.
2. Change the directory to [TA-Portal](/TA-Portal/) in the cloned repository.
3. Run the application with the following command.
    * MacOS: 
        ```bash
        mvn spring-boot:run
        ```
    * Windows:  
        ```bash
        mvnw spring-boot:run
        ```
4. Visit [localhost:9090](http://localhost:9090) with any web-browser to start using the application.
