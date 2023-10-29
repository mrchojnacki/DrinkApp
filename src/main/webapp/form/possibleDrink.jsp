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
  <title>Dashboard - SB Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
  <link href="/css/styles.css" rel="stylesheet" />
  <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
  <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
  <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
  <!-- Navbar Brand-->
  <a class="navbar-brand ps-3" href="/">Drink App</a>
  <!-- Sidebar Toggle-->
  <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
  <!-- Navbar-->
  <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
      <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
        <li><a class="dropdown-item" href="/myAccount">My Account</a></li>
        <li><hr class="dropdown-divider" /></li>
        <li><a class="dropdown-item" href="#!">Logout</a></li>
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
          <a class="nav-link" href="/">
            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
            Dashboard
          </a>
          <!-- Get ALL Drinks-->
          <a class="nav-link" href="/list">
            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
            All Drinks
          </a>
          <c:if test="${isLogged!=null}">
            <a class="nav-link" href="/favList">
              <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
              Favorite Drinks
            </a>
            <a class="nav-link" href="/userList">
              <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
              Drinks Created By You
            </a>
          </c:if>
          <a class="nav-link" href="../htmls/index.html">
            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
            All Ingredients
          </a>
        </div>
      </div>
      <div class="sb-sidenav-footer">
        <c:if test="${isLogged!=null}">
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
        <h3 class="mt-4">Choose from Ingredients Shown Below, They Are Divided Into Alcohol Ingredients and Filler Ingredients.
          After Choosing Any Number of Given Ingredients We Will Show You 3 Lists of Drink From Our Website:
        Which You Can Make, For Which You Lack Single Ingredient and For Which You Lack 2 Ingredients</h3>
        <form action="/possibleDrink" method="post">
          <div class="row">
            <div class="col-xl-2">
              <div id="existingAlcohols">
                <div id="existingAlcohol">
                  <select name="existingAlcoholIds" id="existingAlcoholIds">
                    <c:forEach items="${alcoholIngredients}" var="alcIng">
                      <option value="${alcIng.alcoholType}">${alcIng.alcoholType}</option>
                    </c:forEach>
                  </select>
                  <input type="text" id="filterInputAI" placeholder="Search...">
                  <button type="button" class="remove">Delete this entry</button>
                </div>
                <button type="button" id="add-select-alcohol">Next Alcohol Ingredient</button>
              </div>
            </div>
            <div class="col-xl-2">
              <div id="existingFillers">
                <div id="existingFiller">
                  <select name="existingFillIds" id="existingFillIds">
                    <c:forEach items="${fillIngredients}" var="fillIng">
                      <option value="${fillIng.fill}">${fillIng.fill}</option>
                    </c:forEach>
                  </select>
                  <input type="text" id="filterInputFI" placeholder="Search...">
                  <button type="button" class="remove">Delete this ingredient</button>
                </div>
                <button type="button" id="add-select-filler">Next Filler Ingredient</button>
              </div>
            </div>
            <div class="col-xl-2">
          </div>
          <input type="submit" value="Search For Drinks!">
        </form>
      </div>
    </main>
    <footer class="py-4 bg-light mt-auto">
      <div class="container-fluid px-4">
        <div class="d-flex align-items-center justify-content-between small">
          <div class="text-muted">Copyright &copy; Your Website 2023</div>
          <div>
            <a href="#">Privacy Policy</a>
            &middot;
            <a href="#">Terms &amp; Conditions</a>
          </div>
        </div>
      </div>
    </footer>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="../js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="../assets/demo/chart-area-demo.js"></script>
<script src="../assets/demo/chart-bar-demo.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
<script src="../js/datatables-simple-demo.js"></script>
<script>
  $(document).ready(function () {
    let selectCounter = 1;
    $('#add-select-alcohol').on('click', function () {
      selectCounter++;
      const newSelect = `
       <div id="existingAlcohol">
              <select name="existingAlcoholIds" id="existingAlcoholIds">
                <c:forEach items="${alcoholIngredients}" var="alcIng">
                    <option value="${alcIng.alcoholType}">${alcIng.alcoholType}</option>
                </c:forEach>
              </select>
              <input type="text" id="filterInputAI" placeholder="Search...">
              <button type="button" class="remove">Delete this alcohol</button>
              </div>
      `;

      $('#existingAlcohols').append(newSelect);
    });
    $("#existingAlcohols").on("click", ".remove", function() {
      $(this).parent().remove();
    });
    $('#add-select-filler').on('click', function () {
      selectCounter++;
      const newSelect = `
       <div class="existingFiller">
              <select name="existingFillIds" id="existingFillIds">
                <c:forEach items="${fillIngredients}" var="fillIng">
                    <option value="${fillIng.fill}">${fillIng.fill}</option>
                </c:forEach>
              </select>
              <input type="text" id="filterInputFI" placeholder="Search...">
              <button type="button" class="remove">Delete this ingredient</button>
              </div>
      `;

      $('#existingFillers').append(newSelect);
    });
    $("#existingFillers").on("click", ".remove", function() {
      $(this).parent().remove();
    });
  });
</script>
<script>
  $(document).ready(function () {
    $('#existingAlcoholNames').select2();

    $('#filterInputAI').on('keyup', function () {
      const filterText = $(this).val().toLowerCase();
      $('#existingAlcoholNames option').each(function () {
        if ($(this).text().toLowerCase().includes(filterText)) {
          $(this).show();
        } else {
          $(this).hide();
        }
      });
    });
  });
</script>
<script>
  $(document).ready(function () {
    $('#existingFillNames').select2();

    $('#filterInputFI').on('keyup', function () {
      const filterText = $(this).val().toLowerCase();
      $('#existingFillNames option').each(function () {
        if ($(this).text().toLowerCase().includes(filterText)) {
          $(this).show();
        } else {
          $(this).hide();
        }
      });
    });
  });
</script>

</body>
</html>
