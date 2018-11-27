<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">采销部SLA监控</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">促销 <span class="caret"></span></a>
                    <ul class="dropdown-menu" id="first-menu">
                        <c:forEach var="api" items="${apis.c1}">
                            <li>
                                <a href="#${api.apiId},${api.sysId},${api.logs},${api.stattype},${api.apiUrl},${api.apiName}"
                                   class="api-menu-link">${api.apiName}</a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">促销依赖API <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="api" items="${apis.c11}">
                            <li>
                                <a href="#${api.apiId},${api.sysId},${api.logs},${api.stattype},${api.apiUrl},${api.apiName}"
                                   class="api-menu-link">${api.apiName}</a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">价格 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="api" items="${apis.c2}">
                            <li>
                                <a href="#${api.apiId},${api.sysId},${api.logs},${api.stattype},${api.apiUrl},${api.apiName}"
                                   class="api-menu-link">${api.apiName}</a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">商家 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="api" items="${apis.c3}">
                            <li>
                                <a href="#${api.apiId},${api.sysId},${api.logs},${api.stattype},${api.apiUrl},${api.apiName}"
                                   class="api-menu-link">${api.apiName}</a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">库存 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="api" items="${apis.c4}">
                            <li>
                                <a href="#${api.apiId},${api.sysId},${api.logs},${api.stattype},${api.apiUrl},${api.apiName}"
                                   class="api-menu-link">${api.apiName}</a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li class="dropdown active">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">图片 <span class="caret"></span></a>
		          <ul class="dropdown-menu">
		           <c:forEach var="api" items="${apis.c9}">
		          		 <li>
                                <a href="#${api.apiId},${api.sysId},${api.logs},${api.stattype},${api.apiUrl},${api.apiName}"
                                   class="api-menu-link">${api.apiName}</a></li>
		          	</c:forEach>
		          </ul>
		        </li>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">购物车WEB <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="api" items="${apis.c5}">
                            <li>
                                <a href="#${api.apiId},${api.sysId},${api.logs},${api.stattype},${api.apiUrl},${api.apiName}"
                                   class="api-menu-link">${api.apiName}</a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">购物车无线 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="api" items="${apis.c6}">
                            <li>
                                <a href="#${api.apiId},${api.sysId},${api.logs},${api.stattype},${api.apiUrl},${api.apiName}"
                                   class="api-menu-link">${api.apiName}</a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">购物车结算 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="api" items="${apis.c7}">
                            <li>
                                <a href="#${api.apiId},${api.sysId},${api.logs},${api.stattype},${api.apiUrl},${api.apiName}"
                                   class="api-menu-link">${api.apiName}</a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">购物车外部 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="api" items="${apis.c8}">
                            <li>
                                <a href="#${api.apiId},${api.sysId},${api.logs},${api.stattype},${api.apiUrl},${api.apiName}"
                                   class="api-menu-link">${api.apiName}</a></li>
                        </c:forEach>
                    </ul>
                </li>
            </ul>

            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <select id="ip" name="ip" class="form-control" style="width : 110px">
                        <option value="0" class="reserved">集群</option>
                    </select>
                </div>
            </form>
        </div>
    </div>
</nav>