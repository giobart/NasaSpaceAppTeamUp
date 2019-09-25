<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create an account</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
    <link rel="stylesheet" href="${contextPath}/resources/css/common.css">
    <script src="https://kit.fontawesome.com/12c37f6554.js" crossorigin="anonymous"></script>
    <style type="text/css">
        .logout{
            color: #fff !important;
        }
    </style>
</head>
<body>
  <div class="container-fluid">
    <div class="brand mx-3 blue"> <i class="fas fa-rocket"></i> Team-Up </div>
    
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <br>
        <h4>Welcome ${name} ${surname}
            <a onclick="document.forms['logoutForm'].submit()" class="btn btn-primary logout">Logout</a>
            <a href="${contextPath}/welcome" class="btn btn-primary logout">Home</a>
        </h4>        <i>In case of problems please send an email to: giobarty@gmail.com</i>
        <br><br>

        <!----------------- ACCOUNT INFO ----------------->
        <div class="row">
            <div class="col-sm-6">
                <div class="card">
                <div class="card-body">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><h5> My Group </h5></li>
                    <li class="list-group-item">
                        <h1>My Account</h1>

                        <form:form action="${contextPath}/accountinfo" method="POST" modelAttribute="userInfo" class="form-signin">

                            <spring:bind path="name">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <form:input type="text" path="name" class="form-control" placeholder="Name: ${name}"
                                                autofocus="true"></form:input>
                                    <form:errors path="name"></form:errors>
                                </div>
                            </spring:bind>

                            <spring:bind path="surname">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <form:input type="text" path="surname" class="form-control" placeholder="Surname: ${surname}"
                                                autofocus="true"></form:input>
                                    <form:errors path="surname"></form:errors>
                                </div>
                            </spring:bind>

                            <spring:bind path="email">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <form:input type="text" path="email" class="form-control" placeholder="email: ${email}"
                                                autofocus="true"></form:input>
                                    <form:errors path="email"></form:errors>
                                </div>
                            </spring:bind>

                            <spring:bind path="skills">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <form:input type="text" path="skills" class="form-control" placeholder="Skills list: ${skills}"
                                                autofocus="true"></form:input>
                                    <form:errors path="skills"></form:errors>
                                </div>
                            </spring:bind>

                            <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
                        </form:form>

                    </li>
                </ul>
                </div>
                </div>
            </div>
        </div> <!-- fine row -->

        <br><br>

    </c:if>
    <br><br>
    <p class="blue footer"> Made with <i class="fas fa-heart"></i> by <a href="https://gdgpisa.it">GDG Pisa</a> </p>
  </div>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
