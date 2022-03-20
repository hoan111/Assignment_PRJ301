<%-- 
    Document   : wrapper
    Created on : Mar 3, 2022, 2:54:07 PM
    Author     : hoan
--%>
<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@attribute name="pageTitle" fragment="true" %>
<%@attribute name="js" fragment="true" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title><jsp:invoke fragment="pageTitle"/> - RIP AIM CSGO Server Rent Management</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
        <!-- jquery & jquery validation -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js" integrity="sha512-37T7leoNS06R80c8Ulq7cdCDU5MNQBwlYoy1TX/WUsLFC2eYNqtKlV0QjH7r8JpG/S0GUMZwebnVFLPd6SU5yg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script src="${pageContext.request.contextPath}/js/validate.js"></script>
    </head>
    <body class="sb-nav-fixed">
        <input type="hidden" id="servletContext" value="${pageContext.request.contextPath}">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand ps-3" href="${pageContext.request.contextPath}/dashboard">RIP AIM CSGO</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <<form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
                <!--<div class="input-group">
                    <input class="form-control" type="text" placeholder="Search for..." aria-label="Search for..." aria-describedby="btnNavbarSearch" />
                    <button class="btn btn-primary" id="btnNavbarSearch" type="button"><i class="fas fa-search"></i></button>
                </div>-->
            </form>
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/account/edit?id=${sessionScope.user.id}">Edit your account</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Dashboard
                            </a>
                            <div class="sb-sidenav-menu-heading">Server Management</div>
                            <a class="nav-link" href="${pageContext.request.contextPath}/server/add">
                                <div class="sb-nav-link-icon"><i class="fa fa-server" aria-hidden="true"></i></div>
                                Add a new server
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/server/list">
                                <div class="sb-nav-link-icon"><i class="fa fa-list" aria-hidden="true"></i></div>
                                View servers list
                            </a>
                            <div class="sb-sidenav-menu-heading">Match Management</div>
                            <a class="nav-link" href="${pageContext.request.contextPath}/match/register">
                                <div class="sb-nav-link-icon"><i class="fa fa-plus-square" aria-hidden="true"></i></div>
                                Register a match
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/match/orders">
                                <div class="sb-nav-link-icon"><i class="fa fa-list" aria-hidden="true"></i></div>
                                View match orders
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/match/history">
                                <div class="sb-nav-link-icon"><i class="fa fa-list" aria-hidden="true"></i></div>
                                View match history
                            </a>
                            <c:if test="${sessionScope.user.role == 1}">
                                <div class="sb-sidenav-menu-heading">Account management</div>
                                <a class="nav-link" href="${pageContext.request.contextPath}/account/create">
                                    <div class="sb-nav-link-icon"><i class="fa fa-user-plus" aria-hidden="true"></i></div>
                                    Create an account
                                </a>
                                <a class="nav-link" href="${pageContext.request.contextPath}/account/view">
                                    <div class="sb-nav-link-icon"><i class="fa fa-list" aria-hidden="true"></i></div>
                                    View account list
                                </a>
                            </c:if>
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Logged in as:</div>
                        ${sessionScope.user.username}
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <jsp:doBody/>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; RIP AIM CSGO</div>
                            <!--                            <div>
                                                            <a href="#">Privacy Policy</a>
                                                            &middot;
                                                            <a href="#">Terms &amp; Conditions</a>
                                                        </div>-->
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/assets/demo/chart-area-demo.js"></script>
        <script src="${pageContext.request.contextPath}/assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/js/datatables-simple-demo.js"></script>
    </body>
</html>