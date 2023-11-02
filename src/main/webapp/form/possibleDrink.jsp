<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: micha
  Date: 29.09.2023
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <title>Possible Drinks</title>
  <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
  <link href="/css/styles.css" rel="stylesheet" />
  <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
          <li><hr class="dropdown-divider" /></li>
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
      <div class="sb-sidenav-footer">
        <c:if test="${isLogged==true}">
        <div class="small">Logged in as:</div>
        ${authenticatedUserName}
        </c:if>
      </div>
    </nav>
  </div>
  <div id="layoutSidenav_content">
    <main>
      <div class="container-fluid px-4">
        <h1 class="mt-4">Search for possible Drink To Make!</h1>
        <h5 class="mt-4">Choose from Ingredients Shown Below, They Are Divided Into Alcohol Ingredients and Filler Ingredients.
          After Choosing Any Number of Given Ingredients We Will Show You 3 Lists of Drink From Our Website:
        Which You Can Make, For Which You Lack Single Ingredient and For Which You Lack 2 Ingredients</h5>
        <form action="/possibleDrink" method="post">
          <div class="row">
            <div class="col-xl-2">
              <div class="card">
                <div class="card-body">
                  <div id="existingAlcohols">
                    <div class="existingAlcohol">
                      <select name="existingAlcoholNames" id="existingAlcoholNames">
                        <c:forEach items="${alcoholIngredients}" var="alcIng">
                          <option value="${alcIng}">${alcIng}</option>
                        </c:forEach>
                      </select>
                      <button class="btn btn-danger btn-sm remove" type="button">Delete this entry</button>
                    </div>
                    <button class="btn btn-primary btn-sm" type="button" id="add-select-alcohol">Add Next Alcohol</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-2">
              <div class="card">
                <div class="card-body">
                  <div id="existingFillers">
                    <div class="existingFiller">
                      <select name="existingFillNames" id="existingFillNames">
                        <c:forEach items="${fillIngredients}" var="fillIng">
                          <option value="${fillIng}">${fillIng}</option>
                        </c:forEach>
                      </select>
                      <button class="btn btn-danger btn-sm remove" type="button">Delete this entry</button>
                    </div>
                    <button class="btn btn-primary btn-sm" type="button" id="add-select-filler">Add next filler</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <button type="submit" class="btn btn-success">Search For Drinks!</button>
        </form>
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
<script>
  $(document).ready(function () {
    $('#add-select-alcohol').on('click', function () {
      const newSelect = `
            <div class="existingAlcohol">
              <select name="existingAlcoholNames" id="existingAlcoholNames">
                <c:forEach items="${alcoholIngredients}" var="alcIng">
                  <option value="${alcIng}">${alcIng}</option>
                </c:forEach>
              </select>
              <button class="btn btn-danger btn-sm remove" type="button">Delete this entry</button>
            </div>
      `;

      $('#existingAlcohols').append(newSelect);
    });
    $("#existingAlcohols").on("click", ".remove", function () {
      $(this).parent().remove();
    });
    $('#add-select-filler').on('click', function () {
      const newSelect = `
       <div class="existingFiller">
              <select name="existingFillNames" id="existingFillNames">
                <c:forEach items="${fillIngredients}" var="fillIng">
                  <option value="${fillIng}">${fillIng}</option>
                </c:forEach>
              </select>
              <button class="btn btn-danger btn-sm remove" type="button">Delete this entry</button>
              </div>
      `;

      $('#existingFillers').append(newSelect);
    });
    $("#existingFillers").on("click", ".remove", function () {
      $(this).parent().remove();
    });
  });
</script>

</body>
</html>
