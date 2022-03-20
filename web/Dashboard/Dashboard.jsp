<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<t:wrapper>
    <jsp:attribute name="pageTitle">Dashboard</jsp:attribute>
    <jsp:body>
        <div class="container-fluid px-4">
            <h1 class="mt-4">Dashboard</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active">Dashboard</li>
            </ol>
            <div class="row" style="margin-bottom: 20px">
                <div class="col-xl-3 col-lg-6">
                    <div class="card card-stats mb-4 mb-xl-0">
                        <div class="card-body">
                            <div class="row">
                                <div class="col">
                                    <h5 class="card-title text-uppercase text-muted mb-0">Total Servers</h5>
                                    <span class="h2 font-weight-bold mb-0">${requestScope.TotalServer}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-lg-6">
                    <div class="card card-stats mb-4 mb-xl-0">
                        <div class="card-body">
                            <div class="row">
                                <div class="col">
                                    <h5 class="card-title text-uppercase text-muted mb-0">Total Match Orders</h5>
                                    <span class="h2 font-weight-bold mb-0">${requestScope.TotalOrder}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-lg-6">
                    <div class="card card-stats mb-4 mb-xl-0">
                        <div class="card-body">
                            <div class="row">
                                <div class="col">
                                    <h5 class="card-title text-uppercase text-muted mb-0">Current playing match</h5>
                                    <span class="h2 font-weight-bold mb-0">${requestScope.PlayingMatch}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-lg-6">
                    <div class="card card-stats mb-4 mb-xl-0">
                        <div class="card-body">
                            <div class="row">
                                <div class="col">
                                    <h5 class="card-title text-uppercase text-muted mb-0">Total admins</h5>
                                    <span class="h2 font-weight-bold mb-0">${requestScope.TotalAdmin}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xl-6">
                    <div class="card mb-4">
                        <div class="card-header">
                            <i class="fas fa-chart-area me-1"></i>
                            Game server status
                        </div>
                        <div class="card-body">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Server ID</th>
                                        <th scope="col">Server Name</th>
                                        <th scope="col">Server IP</th>
                                        <th scope="col">Server port</th>
                                        <th scope="col">Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.servers}" var="s">
                                        <tr>
                                            <th scope="row">${s.id}</th>
                                            <td>${s.serverName}</td>
                                            <td>${s.ip}</td>
                                            <td>${s.port}</td>
                                            <c:if test="${s.isActive == true}">
                                                <td>
                                                    <span class="badge bg-success">Active</span>
                                                </td>
                                            </c:if>
                                            <c:if test="${s.isActive == false}">
                                                <td>
                                                    <span class="badge bg-info">In-use</span>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="col-xl-6">
                    <div class="card mb-4">
                        <div class="card-header">
                            <i class="fas fa-chart-area me-1"></i>
                            Live match information
                        </div>
                        <div class="card-body">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Match ID</th>
                                        <th scope="col">Start time</th>
                                        <th scope="col">Score</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.matchHistory}" var="mh">
                                        <tr>
                                            <c:if test="${mh.state == 1}">
                                            <th scope="row">${mh.matchid}</th>
                                            <td>
                                                <fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${mh.startTime}" />
                                            </td>
                                            <td>
                                                <c:if test="${mh.state == 1 && mh.matchScore == null}">
                                                    No data yet
                                                </c:if>
                                                <c:if test="${mh.state == 1 && mh.matchScore != null}">
                                                    (${mh.matchScore.ctName}) ${mh.matchScore.ctScore} - ${mh.matchScore.tScore} (${mh.matchScore.tName}) [LIVE]
                                                </c:if>
                                            </td>
                                            </c:if>
                                        </tr> 
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:wrapper>