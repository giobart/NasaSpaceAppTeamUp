<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create an account</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <link rel="stylesheet" href="${contextPath}/resources/css/common.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/12c37f6554.js" crossorigin="anonymous"></script>
    <style type="text/css">
        .logout {
            color: #fff !important;
        }
    </style>
</head>
<body>

<form id="logoutForm" method="POST" action="${contextPath}/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="container-fluid">
    <nav class="navbar navbar-expand-md navbar-light bg-light fixed-top" style="z-index:9999">
        <div class="brand mx-3 blue "><i class="fas fa-rocket"></i> Team-Up</div>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="navbar-collapse collapse order-3 " id="navbarNavAltMarkup">
            <div class="navbar-nav ml-auto">
                <a class="nav-item nav-link disabled"> </a>
                <a href="${contextPath}/welcome" class="btn btn-primary logout">Home</a>
                <a class="nav-item nav-link disabled"> </a>
                <a onclick="document.forms['logoutForm'].submit()" class="nav-item nav-link active btn btn-secondary logout ">Logout</a>
            </div>
        </div>
    </nav>
</div>

<div class="container-fluid">

    <c:if test="${pageContext.request.userPrincipal.name != null}">

        <div class="dropdown-divider"></div>
        <div class="form-group col-12">
            <hr>
        </div>

        <!----------------- ACCOUNT INFO ----------------->
        <div class="container-fluid">

            <form:form action="${contextPath}/groupinfo" method="POST" modelAttribute="groupInfo" class="form-signin">

                <form:label type="text" path="name">Group name: <b> ${groupName} </b> </form:label><br>

                <spring:bind path="telegramGroup">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="telegramGroup" class="form-control" placeholder="Telegram group: ${telegramGroup}"
                                    autofocus="true"></form:input>
                        <form:errors path="telegramGroup"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="description">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="description" class="form-control" placeholder="Challenge: ${description}"
                                    autofocus="true"></form:input>
                        <form:errors path="description"></form:errors>
                    </div>
                </spring:bind>

                <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
            </form:form>


        </div>
        <!-- fine row -->

        <br><br>

    </c:if>
    <br><br>
    <p class="blue footer"> Made with <i class="fas fa-heart"></i> by <a href="https://gdgpisa.it">GDG Pisa</a></p>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
