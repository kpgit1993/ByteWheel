PROJECT DESCRIPTION : 
=====================
The project is a backend application implemented using Spring Boot, REST and JPA.
The application exposes the REST endpoints for several tasks like:
  a. Place booking
  b. list all vehicles
  c. list vehicles available between dates 
  d. add, delete, update cars, categories, etc. 
  
The project uses h2 in-memory database that can be accessed by:

http://localhost:<port>/h2-console
<port> no. is defined in application.properties file under the resource section.

Default user: sa
Password: <none> 
  
The application performs booking test, add category, add car, get tests using maven surefire plugin. 
Spring boot will use in build tomcat server to deploy the application. 
 
STEPS TO RUN THE APPLICATION: 
=============================
1. Import the project in eclipse
2. Update the proxies in settings.xml in .m2 folder if your network is using any poxy
3. use: mvn clean install to build the project (or build from eclipse)
4. After successful build run the class -> BytewheelApplication.java as a simple java application.
5. After the server is started use rest client to perform any operation. 
   Once the server is started it loads initial data to the database provided in the assignment statement.

   This can be viewed by logging into the h2 database. 
   
Note: 
	Any frontend application developed by AngularJs/ReactJs can make call to this application through REST webserices.
	The UI is not built in this application.

ERRORS WHILE RUNNING THE APPLICATION: 
=====================================
Caused by: java.net.BindException: Address already in use: bind => Change the port no. in application.properties file
Database h2 is locked error => Change the name of the h2 database 
Example: 
		jdbc\:h2\:file\:~/testDB1;DB_CLOSE_ON_EXIT\=FALSE
		jdbc\:h2\:file\:~/testDB2;DB_CLOSE_ON_EXIT\=FALSE
		jdbc\:h2\:file\:~/testDB3;DB_CLOSE_ON_EXIT\=FALSE
	and run the application.
	
	
	