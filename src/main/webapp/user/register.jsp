<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <title>Register</title>
  <link href="/css/styles.css" rel="stylesheet" />
  <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="bg-primary">
<div id="layoutAuthentication">
  <div id="layoutAuthentication_content">
    <main>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-7">
            <div class="card shadow-lg border-0 rounded-lg mt-5">
              <div class="card-header"><h3 class="text-center font-weight-light my-4">Create Account</h3></div>
              <div class="card-body">
                <form:form method="post" action="register" modelAttribute="userRegister">
                  <div class="row mb-3">
                    <div class="col-md-6">
                      <div class="form-floating mb-3 mb-md-0">
                        <form:input class="form-control" path="userName"  placeholder="Enter your user name" />
                        <div class="text-danger"><form:errors path="userName"/></div>
                        <c:if test="${userNameTaken!=null}">
                          <div class="text-danger">${userNameTaken}</div>
                        </c:if>
                        <form:label path="userName">User Name</form:label>
                      </div>
                    </div>
                  </div>
                  <div class="form-floating mb-3">
                    <form:input class="form-control" path="email" type="email" placeholder="name@example.com" />
                    <div class="text-danger"><form:errors path="email"/></div>
                    <c:if test="${emailTaken!=null}">
                      <div class="text-danger">${emailTaken}</div>
                    </c:if>
                    <form:label path="email">Email address</form:label>
                  </div>
                  <div class="row mb-3">
                    <div class="col-md-6">
                      <div class="form-floating mb-3 mb-md-0">
                        <form:password class="form-control" path="password" placeholder="Create a password" />
                        <div class="text-danger"><form:errors path="password"/></div>
                        <form:label path="password">Password</form:label>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-floating mb-3 mb-md-0">
                        <form:password class="form-control" path="passwordConfirmation" placeholder="Confirm password" />
                        <form:label path="passwordConfirmation">Confirm Password</form:label>
                      </div>
                    </div>
                    <c:if test="${registerError!=null}">
                      <div class="text-danger">${registerError}</div>
                    </c:if>
                  </div>
                  <div class="form-check mb-3">
                    <form:checkbox class="form-check-input" path="rememberPassword" value="true" />
                    <form:label class="form-check-label" path="rememberPassword">Remember Password</form:label>
                  </div>
                  <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                    <input type="submit" value="Register">
                  </div>
                </form:form>
              </div>
              <div class="card-footer text-center py-3">
                <div class="small"><a href="/login">Have an account? Go to login</a></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
  <div id="layoutAuthentication_footer">
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
</body>
</html>
