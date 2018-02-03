<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal Edit</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meal Edit</h2>

<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="post">
    <input type="hidden" name="id" value=${meal.id}>
    <dl>
        <dt>Date Time:</dt>
        <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"><br></dd>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dd><input type="text" value="${meal.description}" size=40 name="description"><br></dd>
    </dl>
    <dl>
        <dt>Calories:</dt>
        <dd><input type="text" value="${meal.calories}" name="calories"><br></dd>
    </dl>
    <button type="submit">Save Changes</button>
    <button onclick="window.history.back()">Cancel</button>
</form>

</body>
</html>
