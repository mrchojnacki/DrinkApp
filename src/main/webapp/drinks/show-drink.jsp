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
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
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
                <div class="row">
                <div class="col-xl-5">
                <h1 class="mt-4">${drink.name}</h1>
                </div>
                <div class="col-xl-3">
                <div class="container">
                    <c:if test="${isLogged==true}">
                        <div class="rating-box align-middle align-items-center">
                        <header>Rate This Drink!</header>
                        <div class="stars">
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                        </div>
                    </div>
                    <input type="hidden" name="rating" id="rating">
                    </c:if>
                    <div class="card mb-4 align-middle align-items-center">
                    <h4 class="justify-content-center">Rating: ${avgRating}</h4>
                    </div>
                </div>
                </div>
                    <div class="col-xl-1">
                        <form action="/toggleToFavorites" method="post">
                            <input type="hidden" name="drinkId" value="${drinkId}">
                            <c:choose>
                                <c:when test="${!isFavorite}">
                                    <button type="submit" class="btn btn-warning">Add To Favorites!</button>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" class="btn btn-warning">Remove From Favorites</button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </div>
                    <c:if test="${isUserMade}">
                        <div class="col-xl-2">
                            <a href="/editDrink/${drink.id}">
                                <button class="btn btn-info">Edit Your Drink</button>
                            </a>
                            <br>
                            <br>
                            <br>
                            <a href="/delete/${drink.id}">
                                <button class="btn btn-danger">Delete This Drink</button>
                            </a>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="container-fluid px-4">
            <div class="row">
                <div class="col-xl-2">
                    <h3>Alcohol Ingredients</h3>
                    <ul>
                        <c:forEach var="alcoholIngredient" items="${drink.alcoholIngredientList}">
                            <li>
                                <c:out value="${alcoholIngredient}"/>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-xl-2">
                    <h3>Fill Ingredients</h3>
                    <ul>
                        <c:forEach var="fillIngredient" items="${drink.fillIngredientList}">
                            <li>
                                <c:out value="${fillIngredient}"/>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-xl-5">
                    <img src="/images/${drink.id}?_=${current.time}">
                </div>
            </div>
            </div>
            <div class="container-fluid px-4">
            <div class="row align-items-center align-middle">
                <div class="col-lg-8">
                <c:out value="${drink.method}"/>
                </div>
            </div>
            </div>
            <section style="background-color: #eee;">
                <div class="container my-5 py-5">
                    <div class="row d-flex justify-content-center">
                        <div class="col-md-12 col-lg-10 col-xl-8">
                            <div class="card">
                                <c:choose>
                                    <c:when test="${commentList!=null|| not empty commentList}">
                                        <c:forEach var="comment" items="${commentList}">
                                            <div class="card-body">
                                                <div class="d-flex flex-start align-items-center">
                                                    <div>
                                                        <h6 class="fw-bold text-primary mb-1">${comment.userName}</h6>
                                                        <p class="text-muted small mb-0">
                                                                ${comment.createdOn}
                                                        </p>
                                                    </div>
                                                </div>
                                                <p class="mt-3 mb-4 pb-2">
                                                        ${comment.commentContent}
                                                </p>
                                            </div>
                                        </c:forEach>
                                        </c:when>
                                    <c:otherwise>
                                        <div class="row">
                                            <div class="col-xl-5">
                                                <h3 class="fw-bold text-primary mb-1">No comments yet, be first to comment this drink!</h3>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                <c:when test="${isLogged==true}">
                                <form action="/addComment" method="post">
                                <div class="card-footer py-3 border-0" style="background-color: #f8f9fa;">
                                    <div class="d-flex flex-start w-100">
                                        <div class="form-outline w-100">
                                            <textarea name="commentContent" class="form-control" id="commentContent" rows="4"
                                                      style="background: #fff;" placeholder="Enter your comment here"/></textarea>
                                        </div>
                                        <input type="hidden" id="drinkId" name="drinkId" value="${drink.id}">
                                    </div>
                                    <div class="float-end mt-2 pt-1">
                                        <button type="submit" class="btn btn-primary btn-sm">Post comment</button>
                                    </div>
                                </div>
                                </form>
                                </c:when>
                                <c:otherwise>
                                    <div class="row">
                                        <div class="col-xl-8">
                                            <h5 class="fw-bold text-primary mb-1">Log in to comment on the Drink!</h5>
                                        </div>
                                    </div>
                                </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
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
<script>
    let drinkId = ${drink.id};
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
<script src="/js/addons/rating.js"></script>
</body>
</html>
