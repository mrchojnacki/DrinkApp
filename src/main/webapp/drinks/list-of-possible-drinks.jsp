<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="current" class="java.util.Date" />

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <title>Dashboard - SB Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
  <link href="/css/styles.css" rel="stylesheet" />
  <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
  <a class="navbar-brand ps-3" href="/">Drink App</a>
  <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
  <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
      <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
        <c:choose>
          <c:when test="${isLogged==true}">
            <li><a class="dropdown-item" href="/myAccount">My Account</a></li>
            <li><hr class="dropdown-divider"/></li>
            <li><a class="dropdown-item" href="/logout">Logout</a></li>
          </c:when>
          <c:otherwise>
            <li><a class="dropdown-item" href="/login">Log in</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </li>
  </ul>
</nav>
<div id="layoutSidenav">
  <div id="layoutSidenav_nav">
    <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
      <div class="sb-sidenav-menu">
        <div class="nav">
          <div class="sb-sidenav-menu-heading">Menu</div>
          <a class="nav-link" href="/list">
            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
            All Drinks
          </a>
          <c:if test="${isLogged==true}">
            <a class="nav-link" href="/favList">
              <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
              Favorite Drinks
            </a>
            <a class="nav-link" href="/userList">
              <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
              Drinks Created By You
            </a>
          </c:if>
          <a class="nav-link" href="/possibleDrink">
            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
            What kind of drink can you create?
          </a>
        </div>
      </div>
      <c:if test="${isLogged==true}">
        <div class="sb-sidenav-footer">
          <div class="small">Logged in as:</div>
            ${authenticatedUserName}
        </div>
      </c:if>
    </nav>
  </div>
  <div id="layoutSidenav_content">
    <main>
      <div class="container-fluid px-4">
        <h1 class="mt-4">Possible Drinks</h1>
        <div class="row">
          <div class="col-xl-3">
            <div class="card mb-4 align-middle align-items-center">
              <h5>Drinks You Can Make</h5>
            </div>
          </div>
          <div class="col-xl-3">
            <div class="card mb-4 align-middle align-items-center">
              <h5>Drinks For Which You Lack 1 Ingredient</h5>
            </div>
          </div>
          <div class="col-xl-3">
            <div class="card mb-4 align-middle align-items-center">
              <h5>Drinks For Which You Lack 2 Ingredients</h5>
            </div>
          </div>
        </div>
        <div class="row">
        <div class="col-xl-3">
        <c:forEach var="drink" items="${drinksUserCanMake}">
          <c:set var="imageUrl" value="/images/${drink.id}?_=${current.time}" />
              <div class="card mb-4">
                <div class="container2 align-items-center">
                <div class="row"><h5><a class="align-middle align-items-center" href="/list/drink/${drink.id}">${drink.name}</a></h5>
                </div>
              <c:choose>
                <c:when test="${not empty imageUrl}">
                        <div class="row"><img class="scaled-image" src="${imageUrl}"></div>
                </c:when>
                <c:otherwise>
                        <div class="row"><img class="scaled-image" src="/images/no"></div>
                </c:otherwise>
              </c:choose>
                </div>
              </div>
        </c:forEach>
        </div>
        <div class="col-xl-3">
          <c:forEach var="drink" items="${drinksUserCanMakeMinusOne}">
            <c:set var="imageUrl" value="/images/${drink.id}?_=${current.time}" />
            <div class="card mb-4">
            <div class="container2 align-items-center">
              <div class="row"><h5><a class="align-middle align-items-center" href="/list/drink/${drink.id}">${drink.name}</a></h5>
              <br>
              <c:choose>
                <c:when test="${not empty imageUrl}">
                  <div class="row"><img class="scaled-image" src="${imageUrl}"></div>
                </c:when>
                <c:otherwise>
                  <div class="row"><img class="scaled-image" src="/images/no"></div>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
            </div>
          </c:forEach>
        </div>
        <div class="col-xl-3">
          <c:forEach var="drink" items="${drinksUserCanMakeMinusTwo}">
            <c:set var="imageUrl" value="/images/${drink.id}?_=${current.time}" />
            <div class="card mb-4">
              <div class="container2 align-items-center">
                <div class="row"><h5><a class="align-middle align-items-center" href="/list/drink/${drink.id}">${drink.name}</a></h5>
                <br>
                <c:choose>
                  <c:when test="${not empty imageUrl}">
                    <div class="row"><img class="scaled-image" src="${imageUrl}"></div>
                  </c:when>
                  <c:otherwise>
                    <div class="row"><img class="scaled-image" src="/images/no"></div>
                  </c:otherwise>
                </c:choose>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
      </div>
    </div>
    </main>
    <footer class="py-4 bg-light mt-auto">
      <div class="container-fluid px-4">
        <div class="d-flex align-items-center justify-content-between small">
          <div class="text-muted">Copyright &copy; Your Website 2023</div>
        </div>
      </div>
    </footer>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
</body>
</html>
