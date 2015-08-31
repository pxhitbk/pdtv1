<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<head>
	<title>List - Tour ${path}</title>
</head>

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
                                <span>${tourTypePath}</span>
                            </li>
                            <li class="active">
                                <span>${path}</span>
                            </li>
                        </ul>
                    </div>
                    <div class="tours-list">
                        <ul>
                        	<c:if test="${not empty tourList}">
                        		<c:forEach var = "vtour" items="${tourList}">
									<li>
		                                <div class="tour-list-item">
		                                    <div class="photo">
		                                    	<c:if test="${vtour.thumbnail != null}">
		                                        	<img src='${pageContext.request.contextPath}${vtour.thumbnail}' alt="" class="img-thumbnail" />
		                                        </c:if>
		                                    </div>
		                                    <div class="detail">
		                                        <c:if test="${not empty vtour.name}">
			                                        <h4>
<%-- 			                                            	<a href="<c:url value="/tour/${tourTypePath}/${path}/${vtour.id}"/>">${vtour.name}</a> --%>
			                                            	<a href="<c:url value="/tour/${tourTypePath}/${pathId}/${vtour.id}/${vtour.seoPath}"/>">${vtour.name}</a>
			                                        </h4>
			                                        <p class="text-justify">${vtour.description}</p>
		                                        </c:if>
		                                        <div class="text-right">
		                                            <a href="<c:url value="/tour/${tourTypePath}/${pathId}/${vtour.id}/${vtour.seoPath}"/>">détails <span class="arrow"></span><span class="arrow"></span></a>
		                                        </div>
		                                    </div>
		                                    <div class="clearfix"></div>
		                                </div>
		                            </li>	                        		
                        		</c:forEach>
                        	</c:if>
                        </ul>
                    </div>
					<c:if test="${pagination.numberOfPage > 1}">
                    <div class="text-right">
                        <ul class="pagination pagination-sm pagination-lg">
                        	<c:if test="${pagination.currentPage == 1}">
                            <li class="disabled"><a href="#">«</a></li>
                            </c:if>
                            <c:if test="${pagination.currentPage > 1}">
                            <li class="active"><a href="<c:url value= "/tour/${tourTypePath}/${pathId}/${p - 1}"/>">«</a></li>
                            </c:if>
							<c:forEach begin="1" end="${pagination.numberOfPage}" step="1" var="p">
                            <c:if test="${pagination.currentPage == p}">
                            <li class="active"><a href="${pageContext.request.contextPath}/tour/${tourTypePath}/${pathId}/${p}">${p} <span class="sr-only">(current)</span></a></li>
                            </c:if>
                            <c:if test="${pagination.currentPage != p}">
								<li><a href="${pageContext.request.contextPath}/tour/${tourTypePath}/${pathId}/${p}">${p} <span class="sr-only">(current)</span></a></li>
                            </c:if>
                            </c:forEach>
                            <c:if test="${pagination.currentPage == pagination.numberOfPage}">
                            <li class="disabled"><a href="#">«</a></li>
                            </c:if>
                            <c:if test="${pagination.currentPage < pagination.numberOfPage}">
                            <li class="active"><a href="<c:url value= "/tour/${tourTypePath}/${pathId}/${p + 1}"/>">«</a></li>
                            </c:if>
                            <li><a href="#">»</a></li>
                        </ul>
                    </div>
                    </c:if>
                </div>
                <div class="col-sm-3">
                    <%-- <div class="home-contact tour-box">
                        <img src="${pageContext.request.contextPath}/resources/img/contact.jpg" alt="" class="img-responsive" />

                        <table width="100%">
                            <tr>
                                <td align="center">
                                    <a href="#"><img src="${pageContext.request.contextPath}/resources/img/yahoo.jpg" alt="Yahoo" /></a>
                                </td>
                                <td align="center">
                                    <a href="#"><img src="${pageContext.request.contextPath}/resources/img/skype.jpg" alt="Skype" /></a>
                                </td>
                            </tr>
                        </table>

                        <address>
                            Hotline: +84 912 343 443 15<br />
                            Email: <a href="mailto:contact@isoftz.com">contact@isoftz.com</a>
                        </address>
                    </div>
 --%>
 					<tiles:insertAttribute name="contactbox"/>
                    <div class="region-filters tour-box">
                        <div class="tour-head head-info">
                            <h5 class="text-uppercase">Du lịch miền bắc</h5>
                            <span class="head-arrow"></span>
                        </div>
                        <div class="tour-content">
                            <ul>
                                <li>
                                    <a href="#">Hà Nội & vùng lân cận</a>
                                </li>
                                <li>
                                    <a href="#">Sapa & Đông Tây Bắc</a>
                                </li>
                                <li>
                                    <a href="#">Du lịch Hạ Long, Cát Bà</a>
                                </li>
                                <li>
                                    <a href="#">Lai Châu</a>
                                </li>
                                <li>
                                    <a href="#">Điện Biên Phủ</a>
                                </li>
                                <li>
                                    <a href="#">Sơn La</a>
                                </li>
                                <li>
                                    <a href="#">Ninh Bình</a>
                                </li>
                                <li>
                                    <a href="#">Hà Giang</a>
                                </li>
                                <li>
                                    <a href="#">Lạng Sơn</a>
                                </li>
                                <li>
                                    <a href="#">Cao Bằng</a>
                                </li>
                                <li>
                                    <a href="#">Quảng  Ninh</a>
                                </li>
                                <li>
                                    <a href="#">Lào Cai</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>