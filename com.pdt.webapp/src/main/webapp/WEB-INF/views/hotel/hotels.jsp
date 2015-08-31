<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<head>
	<title>Hotel - ${cityName}</title>
</head>
<link href="${pageContext.request.contextPath}/resources/css/star-rating.css" rel="stylesheet" />
<div class="main-panel">
        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <div class="tour-breadcrumb">
                        <ul class="breadcrumb">
                            <li>
                                <a href="<c:url value="/home"/>">Home</a>
                            </li>
                            <li>
                                <a href="#">Hôtel</a>
                            </li>
                            <li class="active">
                                <span>${cityName}</span>
                            </li>
                        </ul>
                    </div>
                    <div class="tours-list">
                        <ul>
                        <c:if test="${not empty hotelList}">
                        		<c:forEach var = "vhotel" items="${hotelList}">
                            <li>
                                <div class="tour-list-item">
                                    <div class="photo">
                                        <c:if test="${vhotel.thumbnail != null}">
		                                        	<img src='${pageContext.request.contextPath}${vhotel.thumbnail}' alt="" class="img-thumbnail" />
		                                        </c:if>
                                    </div>
                                    <div class="detail">
                                        <h4>
                                            <a href='<c:url value="/hotel/${cityId}/${vhotel.id}/${vhotel.seoPath}"/>'>${vhotel.name}</a>
                                        </h4>
                                        <div class="text-danger">
                                        <c:if test="${vhotel.fromPrice != 0}">
	                                    	<strong>Prix : à partir de ${vhotel.fromPrice} €</strong>
	                                    </c:if>
	                                    
                                        </div>
                                        <form action="#" method="POST">
                                            <input id="input-21d" readonly="readonly" value="${vhotel.stars}" type="number" class="rating" min="0" max="5" step="1" data-size="xs">
                                        </form>
                                        <p class="text-justify">${vhotel.description}</p>
                                        <div class="text-right">
                                            <a href='<c:url value="/hotel/${cityId}/${vhotel.id}/${vhotel.seoPath}"/>'>Details <span class="arrow"></span><span class="arrow"></span></a>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </li>
                            </c:forEach>
                            </c:if>
                        </ul>
                    </div>

                    <div class="text-right">
                        <ul class="pagination pagination-sm pagination-lg">
                            <c:if test="${pagination.numberOfPage > 1}">
                    <div class="text-right">
                        <ul class="pagination pagination-sm pagination-lg">
                        	<c:if test="${pagination.currentPage == 1}">
                            <li class="disabled"><a href="#">«</a></li>
                            </c:if>
                            <c:if test="${pagination.currentPage > 1}">
                            <li class="active"><a href="<c:url value= "/${cityId}/${p - 1}"/>">«</a></li>
                            </c:if>
							<c:forEach begin="1" end="${pagination.numberOfPage}" step="1" var="p">
                            <c:if test="${pagination.currentPage == p}">
                            <li class="active"><a href="${pageContext.request.contextPath}/${cityId}/${p}">${p} <span class="sr-only">(current)</span></a></li>
                            </c:if>
                            <c:if test="${pagination.currentPage != p}">
							/${cityId}uest.contextPath}/${cityId}/${p}">${p} <span class="sr-only">(current)</span></a></li>
                            </c:if>
                            </c:forEach>
                            <c:if test="${pagination.currentPage == pagination.numberOfPage}">
                            <li class="disabled"><a href="#">«</a></li>
                            </c:if>
                            <c:if test="${pagination.currentPage < pagination.numberOfPage}">
                            <li class="active"><a href="<c:url value= "/cityId/${p + 1}"/>">«</a></li>
                            </c:if>
                            <li><a href="#">»</a></li>
                        </ul>
                    </div>
                    </c:if>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-3">
                    <tiles:insertAttribute name="contactbox"/>

                    <div class="region-filters tour-box">
                        <div class="tour-head head-info">
                            <h5 class="text-uppercase">Hôtel à ${regionName}</h5>
                            <span class="head-arrow"></span>
                        </div>
                        <div class="tour-content">
                            <ul>
                            <c:if test="${not empty relatedCities}">
                        		<c:forEach var = "city" items="${relatedCities}">
                        			<li>
                                    	<a href="<c:url value="/hotel/${city.id}"/>">${city.name}</a>
                                	</li>
                        		</c:forEach>
                       		</c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/resources/js/star-rating.js"></script>
