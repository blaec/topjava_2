<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
</body>

<table>
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach items="${mealList}" var="meal">
        <tr style="${meal.exceed ? 'color: red':'color: green'}">
            <td>${f:formatLocalDateTime(meal.dateTime, 'dd/MM/yyyy')}</td>
            <td>${meal.calories}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>

</html>