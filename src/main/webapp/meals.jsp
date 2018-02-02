<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${mealList}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
            <td>${f:formatLocalDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="">Update</a></td>
            <td><a href="meal?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>

</html>