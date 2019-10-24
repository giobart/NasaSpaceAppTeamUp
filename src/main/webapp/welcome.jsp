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
    <title>Team Up</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="${contextPath}/resources/css/common.css">
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
                <a href="${contextPath}/accountinfo"
                   class="nav-item nav-link active btn btn-primary logout">Account </a>
                <a class="nav-item nav-link disabled"> </a>
                <a onclick="document.forms['logoutForm'].submit()"
                   class="nav-item nav-link active btn btn-secondary logout">Logout</a>
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

    <div class="row">

        <!----------------- (non Mentors only) GROUP INFO ----------------->
        <div class="col-sm-6">
            <c:if test="${isMentor == false}">
                <div class="card">
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"><h5> My Group </h5></li>
                            <li class="list-group-item">
                                <c:if test="${groupinfo != null}">
                                    <div id="newClient">
                                        <p>
                                        <h4><c:out value="${groupinfo.name}"/></h4>
                                        <i>Challenge: <c:out value="${groupinfo.description}"/></i> <br>
                                        <c:if test="${groupinfo.telegramGroup != ''}">
                                            <a href="${groupinfo.telegramGroup}">Telegram group link</a>
                                        </c:if>
                                        </p>
                                    </div>
                                    <h4>Participants: </h4>
                                    <ul>
                                        <c:forEach items="${groupinfo.groupmates}" var="users">
                                            <li>
                                                <div id="newClient">
                                                    <p>
                                                        <c:out value="${users.name}"/>
                                                        <i><c:out value="${users.surname}"/></i>
                                                        <i><c:out value="${users.email}"/></i>
                                                        <b><c:out value="${users.skills}"/></b>
                                                    </p>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <c:if test="${ ! empty groupinfo.groupFollowers}">
                                        <h4>Followers: </h4>
                                    </c:if>
                                    <ul>
                                        <c:forEach items="${groupinfo.groupFollowers}" var="users">
                                            <li>
                                                <div id="newClient">
                                                    <p>
                                                        <c:out value="${users.name}"/>
                                                        <i><c:out value="${users.surname}"/></i>
                                                        <i><c:out value="${users.email}"/></i>
                                                        <b><c:out value="${users.skills}"/></b>
                                                    </p>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>

                                    <c:if test="${groupinfo != null}">
                                        <span>
                                        <form:form name="leaveGroupForm" method="POST" action="/group/leaveGroup">
                                            <input type="hidden" name="groupname" value="${groupinfo.name}">
                                            <input type="submit" name="LeaveGroupButton"  class="btn btn-primary active" value="Leave Group" />
                                        </form:form>
                                        </span>
                                    </c:if>
                                    <c:if test="${groupinfo != null}">
                                        <a href="${contextPath}/groupinfo"
                                           class="btn btn-secondary active">Edit Group</a>
                                    </c:if>
                                </c:if>
                                <c:if test="${groupinfo == null}">
                                    <h4>Create a new group</h4>
                                    <form:form action="/group/new" method="POST" modelAttribute="group">
                                        <div class="form-group">
                                            <form:label path="name">Name</form:label>
                                            <form:input path="name" class="form-control"></form:input>
                                            <form:errors path="name"></form:errors>
                                        </div>
                                        <div class="form-group">
                                            <form:label path="description">Challenge name:</form:label>
                                            <form:input path="description" class="form-control"></form:input>
                                            <form:errors path="description"></form:errors>
                                        </div>
                                        <div class="form-group">
                                            <form:label path="telegramGroup">Telegram group link:</form:label>
                                            <form:input path="telegramGroup" class="form-control"></form:input>
                                            <form:errors path="telegramGroup"></form:errors>
                                        </div>
                                        <input type="submit" class="btn btn-lg btn-primary btn-block"/>
                                    </form:form>
                                </c:if>
                            </li>
                        </ul>
                    </div>
                </div>
            </c:if>

            <!------- (Mentors only) FOLLOWING GROUPS --------->

            <c:if test="${isMentor == true}">
                <div class="card">
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <h5 class="card-title"><i class="fas fa-users"></i> Following Groups </h5>
                            <c:forEach items="${followingGroups}" var="groupinfo">
                                <li class="list-group-item">
                                    <c:if test="${groupinfo != null}">
                                        <div id="newClient">
                                            <p>
                                            <h4><c:out value="${groupinfo.name}"/></h4>
                                            <i><c:out value="${groupinfo.description}"/></i><br>
                                            <c:if test="${groupinfo.telegramGroup != ''}">
                                                <a href="${groupinfo.telegramGroup}">Telegram group link</a>
                                            </c:if>
                                            </p>
                                        </div>
                                        <h4>Participants: </h4>
                                        <ul>
                                            <c:forEach items="${groupinfo.groupmates}" var="users">
                                                <li>
                                                    <div id="newClient">
                                                        <p>
                                                            <c:out value="${users.name}"/>
                                                            <i><c:out value="${users.surname}"/></i>
                                                            <i><c:out value="${users.email}"/></i>
                                                            <b><c:out value="${users.skills}"/></b>
                                                        </p>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                        <c:if test="${ ! empty groupinfo.groupFollowers}">
                                            <h4>Followers: </h4>
                                        </c:if>
                                        <ul>
                                            <c:forEach items="${groupinfo.groupFollowers}" var="users">
                                                <li>
                                                    <div id="newClient">
                                                        <p>
                                                            <c:out value="${users.name}"/>
                                                            <i><c:out value="${users.surname}"/></i>
                                                            <i><c:out value="${users.email}"/></i>
                                                            <b><c:out value="${users.skills}"/></b>
                                                        </p>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>

                                        <c:if test="${groupinfo != null}">
                                            <span>
                                            <form:form name="unfollowGroupForm" method="POST" action="/group/unfollowGroup" >
                                                <input type="hidden" name="groupname" value="${groupinfo.name}">
                                                <input type="submit" name="LeaveGroupButton"  class="btn btn-primary active" value="Unfollow Group" />
                                            </form:form>
                                            </span>
                                        </c:if>
                                    </c:if>

                                </li>
                            </c:forEach>

                            <c:if test="${ empty followingGroups }">
                                <li class="list-group-item"><h4>You are not following any group</h4></li>
                            </c:if>

                        </ul>
                    </div>
                </div>
            </c:if>
            <!----------------- GROUP LIST ----------------->
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title"><i class="fas fa-users"></i> Groups available </h5>
                    <ul class="list-group">
                        <c:forEach items="${groupsAvailables}" var="groupsAvailables">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <div class="row w-100">
                                    <div class="col-sm-9">
                                        <div id="newClient">
                                            <p>
                                            <h4>
                                                <c:out value="${groupsAvailables.name}"/>
                                                <span class="badge badge-primary badge-pill">${fn:length(groupsAvailables.groupmates)}</span>
                                            </h4>
                                            <div class="dropdown-divider"></div>
                                            <i>Challenge: <c:out value="${groupsAvailables.description}"/> </i><br>
                                            <c:if test="${ ! empty groupsAvailables.groupFollowers}">
                                                <br>
                                                <p>
                                                    <button class="btn btn-secondary" type="button"
                                                            data-toggle="collapse"
                                                            data-target="#collapseGroup${groupsAvailables.id}"
                                                            aria-expanded="false" aria-controls="collapseExample">
                                                        Followers
                                                    </button>
                                                </p>
                                            </c:if>
                                            <div class="collapse" id="collapseGroup${groupsAvailables.id}">
                                                <div class="card card-body">
                                                    <ul>
                                                        <c:forEach items="${groupsAvailables.groupFollowers}"
                                                                   var="users">
                                                            <li>
                                                                <div id="newClient">
                                                                    <p>
                                                                        <c:out value="${users.name}"/>
                                                                        <i><c:out value="${users.surname}"/></i>
                                                                    </p>
                                                                </div>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="col-sm-3 my-auto">
                                        <c:if test="${groupinfo == null && isMentor == false && fn:length(groupsAvailables.groupmates)<5 }">
                                            <span>
                                            <form:form name="joinGroupForm" method="POST" action="/group/joinGroup" >
                                                <input type="hidden" name="groupname" value="${groupsAvailables.name}">
                                                <input type="submit" name="LeaveGroupButton"  class="btn btn-primary active" value="Join Group" />
                                            </form:form>
                                            </span>
                                        </c:if>
                                        <c:if test="${isMentor == true}">
                                            <span>
                                                <form:form name="followGroupForm" method="POST" action="/group/followGroup" >
                                                <input type="hidden" name="groupname" value="${groupsAvailables.name}">
                                                <input type="submit" name="LeaveGroupButton"  class="btn btn-primary active" value="Follow Group" />
                                            </form:form>
                                            </span>
                                        </c:if>

                                    </div>
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
                    <ul class="list-group">
                        <c:forEach items="${users}" var="users">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <div id="newClient">
                                    <p>
                                        <c:if test="${users.group != null}">
                                        <span class="badge badge-primary badge-pill">busy</span>
                                        </c:if>

                                        <!-- show mentor badge -->
                                        <c:forEach var="item" items="${users.roles}">
                                        <c:if test="${item.name eq 'MENTOR'}">
                                        <span class="badge badge-dark badge-pill">mentor</span>
                                        </c:if>
                                        </c:forEach>
                                        <!-- end mentor badge -->

                                        <b><c:out value="${users.name}"/> <c:out value="${users.surname}"/></b>
                                    <div class="dropdown-divider"></div>
                                    Contact: <i><c:out value="${users.email}"/></i>
                                    <br>
                                    Skills: <b><c:out value="${users.skills}"/></b>
                                    <br>
                                    Degree: <b><c:out value="${users.degreeCourse}"/></b>
                                    <br>
                                    <c:if test="${isAdmin == true}">
                                        <form:form name="toggleMentorForm" method="POST" action="/admin/toggleMentor" >
                                            <input type="hidden" name="username" value="${users.username}">
                                            <input type="submit" name="ToggleMentorButton"  class="btn btn-danger active" value="Toggle Mentor" />
                                        </form:form>
                                    </c:if>
                                    </p>

                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="form-group col-12">
        <hr>
    </div>

    <i>In case of problems please send an email to: giobarty@gmail.com</i>

</div>
<!-- fine row -->

</c:if>
<br><br>
<p class="blue footer"> Made with <i class="fas fa-heart"></i> by <a href="https://gdgpisa.it">GDG Pisa</a></p>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
