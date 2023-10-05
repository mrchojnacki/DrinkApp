<%--
  Created by IntelliJ IDEA.
  User: micha
  Date: 20.09.2023
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Add New Drink!</title>
</head>
<body>
<%--@elvariable id="drinkRequestDTO" type="pl.coderslab.app.domain.drinkRequestDTO.DrinkDTO"--%>
  <form:form action="/addNewDrink" method="post" modelAttribute="drinkRequestDTO">
    Name of the drink: <form:input path="name"/>
    How to make it: <form:textarea path="method"/>
    Ingredients:
    Type of alcohol: <form:input path="alcoholIngredients[0].alcoholType"/>
    And its volume (ml): <form:input path="alcoholIngredients[0].volumeMillilitres"/>
    Type of alcohol: <form:input path="alcoholIngredients[1].alcoholType"/>
    And its volume (ml): <form:input path="alcoholIngredients[1].volumeMillilitres"/>
    Type of alcohol: <form:input path="alcoholIngredients[2].alcoholType"/>
    And its volume (ml): <form:input path="alcoholIngredients[2].volumeMillilitres"/>

    Type of filler ingredient (juices, fruits, compounds): <form:input path="fillIngredients[0].fill"/>
    And amount of it: <form:input path="fillIngredients[0].amount"/>

    Type of filler ingredient (juices, fruits, compounds): <form:input path="fillIngredients[1].fill"/>
    And amount of it: <form:input path="fillIngredients[1].amount"/>

    Type of filler ingredient (juices, fruits, compounds): <form:input path="fillIngredients[2].fill"/>
    And amount of it: <form:input path="fillIngredients[2].amount"/>

    Type of filler ingredient (juices, fruits, compounds): <form:input path="fillIngredients[3].fill"/>
    And amount of it: <form:input path="fillIngredients[3].amount"/>

    Type of filler ingredient (juices, fruits, compounds): <form:input path="fillIngredients[4].fill"/>
    And amount of it: <form:input path="fillIngredients[4].amount"/>
    <input type="submit" value="Add New Drink!">
  </form:form>
</body>
</html>
