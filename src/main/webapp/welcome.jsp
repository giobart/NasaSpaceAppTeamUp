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
            <a href="${contextPath}/accountinfo" class="btn btn-primary logout">Account Information</a>
        </h4>
        <i>In case of problems please send an email to: giobarty@gmail.com</i>
        <br><br>

        <!----------------- GROUP INFO ----------------->
        <div class="row">
            <div class="col-sm-6">
                <div class="card">
                <div class="card-body">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><h5> My Group </h5></li>
                    <li class="list-group-item">
                    <c:if test="${groupinfo != null}">
                            <div id="newClient">
                                <p>
                                <h4> <c:out value="${groupinfo.name}" /> </h4>
                                <i><c:out value="${groupinfo.description}"/> [members ${fn:length(groupinfo.groupmates)}/5] </i>
                                </p>
                            </div>
                        <h4>Participants: </h4>
                        <ul>
                        <c:forEach items="${groupinfo.groupmates}" var="users">
                            <li>
                                <div id="newClient">
                                    <p>
                                        <c:out value="${users.name}" />
                                        <i><c:out value="${users.surname}" /></i>
                                        <i><c:out value="${users.email}" /></i>
                                        <b><c:out value="${users.skills}" /></b>
                                    </p>
                                </div>
                            </li>
                        </c:forEach>
                        </ul>

                        <c:if test="${groupinfo != null}">
                            <a href="${contextPath}/welcome?leaveGroup=${groupinfo.name}" >Leave Group</a>
                        </c:if>
                    </c:if>
                    <c:if test="${groupinfo == null}">
                        <h4>Create a new group</h4>
                        <form:form action="${contextPath}/welcome" method="POST" modelAttribute="group">
                            <div class="form-group">
                                <form:label path="name">Name</form:label>
                                <form:input path="name" class="form-control"></form:input>
                                <form:errors path="name"></form:errors>
                            </div>
                            <div class="form-group">
                                <form:label path="description">Description</form:label>
                                <form:input path="description" class="form-control"></form:input>
                                <form:errors path="description"></form:errors>
                            </div>
                            <input type="submit" class="btn btn-lg btn-primary btn-block" />
                        </form:form>
                    </c:if>
                 </li>
                </div>
                </div>
            </div>
        </div> <!-- fine row -->

        <br><br>

        <!----------------- GROUP LIST ----------------->
        <div class="row">
            <div class="col-sm-6">
                <div class="card">
                  <div class="card-body">
                    <h5 class="card-title"><i class="fas fa-users"></i> Groups available </h5>
                    <ul>
                    <c:forEach items="${groupsAvailables}" var="groupsAvailables">
                        <li><div id="newClient">
                            <p>
                                <h4> <c:out value="${groupsAvailables.name}" /> </h4>
                                <i><c:out value="${groupsAvailables.description}" /> </i>
                            <i>[members <b>${fn:length(groupsAvailables.groupmates)}</b>/5]</i>
                            <c:if test="${groupinfo == null}">
                                <a href="${contextPath}/welcome?joinGroup=${groupsAvailables.name}" >Join Group</a>
                            </c:if>
                            </p>
                        </div>
                        </li>
                    </c:forEach>
                    </ul>
                    </div>
                </div>
            </div>

            <!----------------- USER LIST ----------------->
            <div class="col-sm-6">
                <div class="card">
                  <div class="card-body">
                    <h5 class="card-title"><i class="fas fa-user-alt"></i> Users list </h5>
                      (<s>this</s> means user already in a group)
                    <ul>
                    <c:forEach items="${users}" var="users">
                        <li>
                            <div id="newClient">
                                <p>
                                    <c:if test="${users.group != null}">
                                        <s>
                                    </c:if>
                                        <c:out value="${users.name}" />
                                        <i><c:out value="${users.surname}" /></i>
                                    <c:if test="${users.group != null}">
                                        </s>
                                    </c:if>
                                    <i><c:out value="${users.email}" /></i>
                                    <b><c:out value="${users.skills}" /></b>
                                </p>
                            </div>
                        </li>
                    </c:forEach>
                    </ul>
                </div>
            </div>
            </div>
        </div> <!-- fine row -->
    </c:if>
    <br><br>
    <p class="blue footer"> Made with <i class="fas fa-heart"></i> by <a href="https://gdgpisa.it">GDG Pisa</a> </p>
  </div>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
