<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Drink</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h1>Add New Drink</h1>
<%--@elvariable id="drinkRequestDTO" type="pl.coderslab.app.domain.drinkRequestDTO.DrinkDTO"--%>
<form:form action="/addNewDrink" method="post" modelAttribute="drinkRequestDTO">
    <div id="alcoholFields">
        <div class="alcoholField">
            <form:input path="alcoholIngredients[0].alcoholType" placeholder="Type in alcohol type"/>
            <form:input path="alcoholIngredients[0].volumeMillilitres" placeholder="Volume"/>
            <button type="button" class="remove">Delete this entry</button>
        </div>
        <button type="button" id="addAlcohol">Add next alcohol</button>
    </div>
    <div id="ingredientFields">
        <div class="ingredientField">
            <form:input path="fillIngredients[0].fill" placeholder="Type in ingredient"/>
            <form:input path="fillIngredients[0].amount" placeholder="Amount of ingredient"/>
            <button type="button" class="remove">Delete this ingredient</button>
        </div>
        <button type="button" id="addIngredient">Add next ingredient</button>
    </div>
    <input type="submit" value="Add New Drink!">
</form:form>

<script>
    $(document).ready(function() {
        let counterAlcohol = 1;
        let counterIngredient = 1;
        $("#addAlcohol").click(function() {
            const newField = `
                    <div class="field">
                        <form:input path="alcoholIngredients[${counterAlcohol}].alcoholType" placeholder="Type in alcohol type"/>
                        <form:input path="alcoholIngredients[${counterAlcohol}].volumeMillilitres" placeholder="Volume"/>
                        <button type="button" class="remove">Delete this entry</button>
                    </div>
                `;
            $("#alcoholFields").append(newField);
            counterAlcohol++;
        });
        $("#addIngredient").click(function () {
            const newField = `
                    <div class="field">
                        <form:input path="fillIngredients[${counterIngredient}].fill" placeholder="Type in ingredient"/>
                        <form:input path="fillIngredients[${counterIngredient}].amount" placeholder="Amount of ingredient"/>
                        <button type="button" class="remove">Delete this ingredient</button>
                    </div>
                `;
            $("#ingredientField").append(newField);
            counterIngredient++;
        })
        $("#alcoholFields").on("click", ".remove", function() {
            $(this).parent().remove();
        });
        $("#ingredientField").on("click", ".remove", function() {
            $(this).parent().remove();
        });
    });
</script>
</body>
</html>