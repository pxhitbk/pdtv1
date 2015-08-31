<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
 <div class="header-panel">
        <div class="header-top">
            <div class="container">
                <div class="text-right">
                    <ul class="header-link">
                        <li>
                            <a class="text-uppercase" href="<c:url value="/tourdesign"/>">Demander un Devis</a>
                        </li>
                        <li>
                            <a class="text-uppercase" href="<c:url value="/contactus"/>">Nous Contacter</a>
                        </li>
                        <li>
                            <a class="text-uppercase" href="<c:url value="/aboutus"/>">À propos de nous</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="header-menu">
            <div class="container">
                <nav class="nav-main-menu">
                    <ul class="main-menu">
                        <li>
                            <a href="<c:url value="/home"/>">
                                <span class="glyphicon glyphicon-home"></span>
                            </a>
                        </li>
                       <%--  <li class="has-sub-menu">
                            <a href="#">Region</a>
                            <div class="main-sub-menu" style="right: 0">
                                <table width="510">
                                    <thead>
                                        <tr>
                                            <td>
                                                <h6><a href="<c:url value="/tour/region/north"/>">North</a></h6>
                                            </td>
                                            <td>
                                                <h6><a href="<c:url value="/tour/region/central"/>">Central</a></h6>
                                            </td>
                                            <td>
                                                <h6><a href="<c:url value="/tour/region/south"/>">South</a></h6>
                                            </td>
                                        </tr>
                                    </thead>
                                    <tr>
                                        <td valign="top">
                                            <c:if test="${not empty listTourNorth}">
                                            <ul>
                                            	<c:forEach var="elementTourNorth" items="${listTourNorth}">
                                                	<li><a href="#">${elementTourNorth.name}</a></li>
                                            	</c:forEach>
                                            </ul>
                                            </c:if>
                                        </td>
                                        <td valign="top">
                                            <c:if test="${not empty listTourCentral}">
                                            <ul>
                                            	<c:forEach var="elementTourCentral" items="${listTourCentral}">
                                                	<li><a href="#">${elementTourCentral.name}</a></li>
                                            	</c:forEach>
                                            </ul>
                                            </c:if>
                                        </td>
                                        <td valign="top">
                                        	<c:if test="${not empty listTourSouth}">
                                            <ul>
                                            	<c:forEach var="elementTourSouth" items="${listTourSouth}">
                                                	<li><a href="#">${elementTourSouth.name}</a></li>
                                            	</c:forEach>
                                            </ul>
                                            </c:if>
                                        </td>
                                    </tr>
                                </table>
                            </div> 
                            </li> --%>
                            <li class="has-sub-menu menu-type-2">
                            <a href="#">Région</a>
                            <div>
                                 <ul>
                                      <li><a href="<c:url value="/tour/region/north"/>">Nord</a></li>
                                      <li><a href="<c:url value="/tour/region/central"/>">Centre</a></li>
                                      <li><a href="<c:url value="/tour/region/south"/>">Sud</a></li>
                                 </ul>
                            </div>
                        </li>
                        
                        <li class="has-sub-menu menu-type-2">
                            <a href="#">Sujet</a>
                            <c:if test="${not empty requestScope.TOP_MENUBAR.tourBySubjects}">
                                 <ul>
                                 	<c:forEach var="subject" items="${requestScope.TOP_MENUBAR.tourBySubjects}">
                                     <li><a href="<c:url value="/tour/subject/${subject.key }"/>">${subject.value}</a></li>
                                     </c:forEach>
                                 </ul>
                            </c:if>
                        </li>
                        <li>
                            <a href="<c:url value="/tour/event/0"/>">Events</a>
                        </li>
                        <li class="has-sub-menu menu-type-2">
                            <a href="#">Hôtel</a>
                            <c:if test="${not empty requestScope.TOP_MENUBAR.hotelsByCities }">
                                 <ul>
                                 	<c:forEach var="cityElement" items="${requestScope.TOP_MENUBAR.hotelsByCities}">
                                     <li><a href="<c:url value="/hotel/${cityElement.key }"/>">${cityElement.value}</a></li>
                                     </c:forEach>
                                 </ul>
                           </c:if>
                        </li>
                        <li class="has-sub-menu menu-type-2">
                            <a href="#">Service</a>
                            <c:if test="${not empty requestScope.TOP_MENUBAR.services }">
                                 <ul>
                                 	<c:forEach var="serviceElement" items="${requestScope.TOP_MENUBAR.services}">
                                     <li><a href='<c:url value="/service/${serviceElement.key}"/>'>${serviceElement.value}</a></li>
                                     </c:forEach>
                                 </ul>
                            </c:if>
                        </li>
                        <li class="has-sub-menu menu-type-2">
                            <a href="#">Conseils</a>
							<c:if test="${not empty requestScope.TOP_MENUBAR.tips }">	
                                 <ul>
                                 	<c:forEach var="tipElement" items="${requestScope.TOP_MENUBAR.tips}">
                                     <li><a href='<c:url value="/tip/${tipElement.key}"/>'>${tipElement.value}</a></li>
                                     </c:forEach>
                                 </ul>
                            </c:if>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </nav>
            </div>
        </div>
    </div>