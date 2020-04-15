<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>

<tags:template>
<html>
<head>
    <title>Login</title>
</head>
<body>
<c:if test="${param.loginError eq 'true'}">
    <div class="alert alert-warning" role="alert">
            Invalid username/password.
    </div>
</c:if>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <form action="<c:url value='/login'/>" method="POST">
            <div class="form-group">
                <label for="username">Username</label>
                <input class="form-control" id="username" name="username"/>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password"/>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
        </div>
    </div>
</div>
</body>
</html>
</tags:template>