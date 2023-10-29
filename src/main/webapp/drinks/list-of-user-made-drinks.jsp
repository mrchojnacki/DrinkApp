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
</head>
<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
  <!-- Navbar Brand-->
  <a class="navbar-brand ps-3" href="../htmls/index.html">Start Bootstrap</a>
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
          <a class="nav-link" href="/list">
            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
            All Drinks
          </a>
          <c:if test="${isLogged=!null}">
            <a class="nav-link" href="../htmls/index.html">
              <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
              Favorite Drinks
            </a>
            <a class="nav-link" href="../htmls/index.html">
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
      <c:if test="${isLogged=!null}">
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
        <h1 class="mt-4">All Drinks</h1>
        <div class="row">
          <div class="col-xl-2">
            <div class="card mb-4 align-middle align-items-center">
              <h5>#</h5>
            </div>
          </div>
          <div class="col-xl-3">
            <div class="card mb-4 align-middle align-items-center">
              <h5>Name</h5>
            </div>
          </div>
          <div class="col-xl-6">
            <div class="card mb-4 align-middle align-items-center">
              <h5>Image</h5>
            </div>
          </div>
        </div>
        <c:forEach var="drink" items="${pageOfDrinks.content}">
          <c:set var="imageUrl" value="/images/${drink.id}" />
          <div class="row">
            <div class="col-xl-2">
              <div class="card mb-4 align-middle align-items-center">
                <h5>${drink.id}</h5>
              </div>
            </div>
            <div class="col-xl-3">
              <div class="card mb-4 align-middle align-items-center">
                <h5><a href="/list/drink/${drink.id}">${drink.name}</a></h5>
              </div>
            </div>
            <c:choose>
              <c:when test="${not empty imageUrl}">
                <div class="col-xl-6">
                  <div class="card mb-4">
                    <div class="container1 align-items-center">
                      <img class="scaled-image" src="${imageUrl}">
                    </div>
                  </div>
                </div>
              </c:when>
              <c:otherwise>
                <div class="col-xl-6">
                  <div class="card mb-4">
                    <div class="container1 align-items-center">
                      <img class="scaled-image" src="/images/no">
                    </div>
                  </div>
                </div>
              </c:otherwise>
            </c:choose>
          </div>
        </c:forEach>
        <div class="row">
          <nav aria-label="Page navigation">
            <ul class="pagination">
              <c:if test="${pageNumber!=1}">
                <li class="page-item"><a class="page-link" href="/userList/page/${pageNumber-1}">Previous</a></li>
              </c:if>
              <c:forEach var = "i" begin = "1" end = "${noOfPages}">
                <li class="page-item"><a class="page-link" href="/userList/page/${i}">${i}</a></li>
              </c:forEach>
              <c:if test="${pageNumber!=noOfPages&&userQuantity>5}">
                <li class="page-item"><a class="page-link" href="/userList/page/${pageNumber+1}">Next</a></li>
              </c:if>
            </ul>
          </nav>
        </div>
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
<%--
<script>
  const data = ${drinks}; // Dane przekazane z serwleta

  const itemsPerPage = 5;
  let currentPage = 1;

  // Funkcja do wyświetlania danych na danej stronie
  function displayData(page) {
    const dataContainer = document.getElementById("data-container");
    dataContainer.innerHTML = "";

    const start = (page - 1) * itemsPerPage;
    const end = start + itemsPerPage;

    for (let i = start; i < end && i < data.length; i++) {
      const item = `
                    <div class="row">
            <div class="col-xl-2">
                  <c:set var="imageUrl" value="/images/data[i].id" />
              <div class="card mb-4">
                  data[i].id
              </div>
            </div>
            <div class="col-xl-3">
              <div class="card mb-4">
                <a href="/list/drink/data[i].id">data[i].name</a>
              </div>
            </div>
            <c:choose>
              <c:when test="${not empty imageUrl}">
                <div class="col-xl-6">
                  <div class="card mb-4">
                    <img src="${imageUrl}">
                  </div>
                </div>
              </c:when>
              <c:otherwise>
                <div class="col-xl-6">
                  <div class="card mb-4">
                    <img src="/images/no">
                  </div>
                </div>
              </c:otherwise>
            </c:choose>
          </div>
                `;
      dataContainer.append(item);
    }
  }

  // Funkcja do obsługi przycisków paginacji
  document.getElementById("prev-button").addEventListener("click", () => {
    if (currentPage > 1) {
      currentPage--;
      displayData(currentPage);
    }
  });
  document.getElementById("next-button").addEventListener("click", () => {
    if (currentPage < Math.ceil(data.length / itemsPerPage)) {
      currentPage++;
      displayData(currentPage);
    }
  });
  // Wyświetl dane na pierwszej stronie
  displayData(currentPage);
</script>
--%>

</body>
</html>
