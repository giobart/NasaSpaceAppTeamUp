<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create an account</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${name} ${surname} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>

        <h3> Groups available </h3>
        <ul>
        <c:forEach items="${groupsAvailables}" var="groupsAvailables">
            <li><div id="newClient">
                <p>
                    <h4> <c:out value="${groupsAvailables.name}" /> </h4>
                    <i><c:out value="${groupsAvailables.description}" /></i>
                <c:if test="${groupinfo == null}">
                    <a href="${contextPath}/welcome?joinGroup=${groupsAvailables.name}" >Join Group</a>
                </c:if>
                </p>
            </div>
            </li>
        </c:forEach>
        </ul>

        <h3> Users available </h3>
        <ul>
        <c:forEach items="${users}" var="users">
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

        <h3> My Group </h3>
        <c:if test="${groupinfo != null}">
                <div id="newClient">
                    <p>
                    <h4> <c:out value="${groupinfo.name}" /> </h4>
                    <i><c:out value="${groupinfo.description}" /></i>
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
            <form:form action="${contextPath}/groupregistration" method="POST" modelAttribute="group">
                <div class="form-group">
                    <form:label path="name">Name</form:label>
                    <form:input path="name"></form:input>
                </div>
                <div class="form-group">
                    <form:label path="description">Description</form:label>
                    <form:input path="description"></form:input>
                </div>
                <input type="submit" />
            </form:form>
        </c:if>

    </c:if>
  </div>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
