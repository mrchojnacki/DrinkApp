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
  <a class="navbar-brand ps-3" href="/">Drink App</a>
  <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>

  <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
      <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
        <li><a class="dropdown-item" href="/myAccount">My Account</a></li>
        <li><hr class="dropdown-divider"/></li>
        <li><a class="dropdown-item" href="/logout">Logout</a></li>
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
          <a class="nav-link" href="favList">
            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
            Favorite Drinks
          </a>
          <a class="nav-link" href="/userList">
            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
            Drinks Created By You
          </a>
          <a class="nav-link" href="/possibleDrink">
            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
            What kind of drink can you create?
          </a>
        </div>
      </div>
      <div class="sb-sidenav-footer">
        <div class="small">Logged in as:</div>
        ${authenticatedUserName}
      </div>
    </nav>
  </div>
  <div id="layoutSidenav_content">
    <main>
      <div class="container-fluid px-4">
        <h1 class="mt-4">Edit Your Drink!</h1>
        <form action="/editDrink" method="post" enctype="multipart/form-data">
          <div class="row">
            <div class="col-lg-6">
              <div class="card">
                <div class="card-body">
                  <input class="form-control" type="text" name="name" id="name" placeholder="Name of the Drink" value="${drink.name}">
                  <c:if test="${nameTakenError!=null}">
                    <div class="text-danger">${nameTakenError}</div>
                  </c:if>
                  <c:if test="${nameError!=null}">
                    <div class="text-danger">${nameError}</div>
                  </c:if>
                </div>
              </div>
            </div>
            <div class="col-lg-6">
              <div class="card">
                <div class="card-body">
                  <textarea class="form-control" name="method" id="method" placeholder="Drink Making Method" rows="5" cols="50">${drink.method}</textarea>
                </div>
              </div>
            </div>
          </div>
          <br>
          <div class="row">
            <c:if test="${noIngredientsError!=null}">
              <div class="text-danger">${noIngredientsError}</div>
              <br>
            </c:if>
            <div class="col-lg-3">
              <div class="card">
                <div class="card-body">
                  <div id="existingAlcohols">

                    <button class="btn btn-primary btn-sm" type="button" id="add-select-alcohol">Add Next Alcohol</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-lg-3">
              <div class="card">
                <div class="card-body">
                  <div id="existingFillers">

                    <button class="btn btn-primary btn-sm" type="button" id="add-select-filler">Add next filler</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-lg-3">
              <div class="card">
                <div class="card-body">
                  <c:if test="${nonInPairIngredientInputAmountError!=null}">
                    <br>
                    <div class="text-danger">${nonInPairIngredientInputAmountError}</div>
                    <br>
                  </c:if>
                  <div id="alcoholFields">
                    <c:forEach var="dAlcIng" items="${drink.alcoholIngredientList}">
                      <div class="alcoholField">
                        <input class="form-control" name="newAlcoholName" id="newAlcoholName" placeholder="Type in alcohol type" value="${dAlcIng.alcoholType}"/>
                        <input class="form-control" name="newAlcoholVolume" id="newAlcoholVolume" placeholder="Volume" value="${dAlcIng.volumeMillilitres}"/>
                        <c:if test="${parseError!=null}">
                          <br>
                          <div class="text-danger">${parseError}</div>
                          <br>
                        </c:if>
                        <button class="btn btn-danger btn-sm remove" type="button">Delete this entry</button>
                      </div>
                    </c:forEach>
                    <div class="alcoholField">
                      <input class="form-control" name="newAlcoholName" id="newAlcoholName" placeholder="Type in alcohol type"/>
                      <input class="form-control" name="newAlcoholVolume" id="newAlcoholVolume" placeholder="Volume"/>
                      <c:if test="${parseError!=null}">
                        <br>
                        <div class="text-danger">${parseError}</div>
                        <br>
                      </c:if>
                      <button class="btn btn-danger btn-sm remove" type="button">Delete this entry</button>
                    </div>
                    <button class="btn btn-primary btn-sm" type="button" id="addAlcohol">Add Next Alcohol</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-lg-3">
              <div class="card">
                <div class="card-body">
                  <div class="card">
                    <div id="ingredientFields">
                      <c:forEach var="dFillIng" items="${drink.fillIngredientList}">
                        <div class="ingredientField">
                          <input class="form-control" name="newFillName" placeholder="Type in Filler Ingredient" value="${dFillIng.fill}"/>
                          <input class="form-control" name="newFillAmount" placeholder="Amount of Filler Ingredient" value="${dFillIng.amount}"/>
                          <button class="btn btn-danger btn-sm remove" type="button">Delete this entry</button>
                        </div>
                      </c:forEach>
                      <div class="ingredientField">
                        <input class="form-control" name="newFillName" id="newFillName" placeholder="Type in Filler Ingredient"/>
                        <input class="form-control" name="newFillAmount" id="newFillAmount" placeholder="Amount of Filler Ingredient"/>
                        <button class="btn btn-danger btn-sm remove" type="button">Delete this entry</button>
                      </div>
                      <button class="btn btn-primary btn-sm" type="button" id="addIngredient">Add next Filler</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <br>
          <br>
          <div class="row-cols-1">
            <div class="card">
              <div class="card-body">
                <br>
                <div class="mb-3">
                  <label for="image" class="form-label">Add/Change photo if you want:</label>
                  <input class="form-control" type="file" id="image" name="image">
                </div>
              </div>
              <br>
            </div>
          </div>
          <input type="hidden" name="drinkId" value="${drink.id}">
          <button class="btn btn-success" type="submit">Edit Drink!</button>
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
<script src="/js/dynamicForm.js"></script>
<script>
  $(document).ready(function () {
  $('#add-select-alcohol').on('click', function () {
    const newSelect = `
       <div class="existingAlcohol">
              <select name="existingAlcoholIds" id="existingAlcoholIds">
                <c:forEach items="${alcoholIngredients}" var="alcIng">
                  <option value="${alcIng.id}">${alcIng}</option>
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
              <select name="existingFillIds" id="existingFillIds">
                <c:forEach items="${fillIngredients}" var="fillIng">
                  <option value="${fillIng.id}">${fillIng}</option>
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
