# DrinkApp

In this application you can create your own account, search for drinks from the existing database
powered by Hibernate, create your own drinks, manage them: add them to the database, edit,
delete them. You can rate, comment and add to favorites any drinks you search for. An additional
option provided by my application is the ability to list the ingredients you keep in your fridge or in
your home bar and check what drink you can make right away and what individual ingredients
you are missing to create your unique drink for tonight!
The drinks have their own graphics and when adding a drink to the application, you can also
upload your own photo. There are 21 pre-loaded drinks in the database, so you can browse them
from the start.
Everything is divided into layers using services, controllers and repositories, handling drinks,
ingredients, comments and user ratings, DTO classes were created separately.

NOTE: Before starting the app please create an empty database called "drinkApp"
and insert into file opt/apache-tomcat/conf/context.xml following lines:

	//<Resources antiResourceLocking="false" cachingAllowed="false" />
	<WatchedResource>images</WatchedResource>//

in between the //<Context>// brackets.

The technologies used are: Java 1.8+, Spring MVC, Spring Data, Hibernate, JavaScript, CSS,
Tomcat 9, MySQL/JPQL, JSP, JSTL, HTTP.
