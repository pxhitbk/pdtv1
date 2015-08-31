<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
 <div class="footer-panel">
        <div class="container">
            <div class="row">
                <div class="col-sm-8">
                    <div class="row">
                        <div class="col-sm-2">
                            <h5 class="text-uppercase"><b>Région</b></h5>
                            <ul class="list-links">
                                <li>
                                    <a href="<c:url value="/tour/region/north"/>">Nord</a>
                                </li>
                                <li>
                                    <a href="<c:url value="/tour/region/central"/>">Centre</a>
                                </li>
                                <li>
                                    <a href="<c:url value="/tour/region/south"/>">Sud</a>
                                </li>
                            </ul>
                        </div>
                        <div class="col-sm-2">
                            <h5 class="text-uppercase"><b>Sujet</b></h5>
                            
                            	<c:if test="${not empty requestScope.TOP_MENUBAR.tourBySubjects}">
                                <ul class="list-links">
                                 	<c:forEach var="subject" items="${requestScope.TOP_MENUBAR.tourBySubjects}">
                                     <li><a href="<c:url value="/tour/subject/${subject.key }"/>">${subject.value}</a></li>
                                     </c:forEach>
                                 </ul>
                            </c:if>
                        </div>
                        <div class="col-sm-2">
                            <h5 class="text-uppercase"><b>Event</b></h5>
                            <ul class="list-links">
                               <!--  <li>
                                    <a href="#">Culture</a>
                                </li>
                                <li>
                                    <a href="#">Festival</a>
                                </li>
                                <li>
                                    <a href="#">Fair</a>
                                </li> -->
                            </ul>
                        </div>
                        <div class="col-sm-2">
                            <h5 class="text-uppercase"><b>Hotel</b></h5>
                            <c:if test="${not empty requestScope.TOP_MENUBAR.hotelsByCities }">
                                 <ul class="list-links">
                                 	<c:forEach var="cityElement" items="${requestScope.TOP_MENUBAR.hotelsByCities}">
                                     <li><a href="<c:url value="/hotel/${cityElement.key }"/>">${cityElement.value}</a></li>
                                     </c:forEach>
                                 </ul>
                           </c:if>
                        </div>
                        <div class="col-sm-2">
                            <h5 class="text-uppercase"><b>Services</b></h5>
                        </div>
                        <div class="col-sm-2">
                            <h5 class="text-uppercase"><b>Tips</b></h5>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="socials-network text-right">
                        <ul>
                            <li>
                                <a href="https://facebook.com/">
                                    <img src='<c:url value="/resources/img/socials/facebook.png" />' alt="Facebook" />
                                </a>
                            </li>
                            <li>
                                <a href="https://twitter.com/">
                                    <img src='<c:url value="/resources/img/socials/twitter.png" />' alt="Twitter" />
                                </a>
                            </li>
                            <li>
                                <a href="https://plus.google.com/">
                                	<img src='<c:url value="/resources/img/socials/google-plus.png" />' alt="Google+" />
                                </a>
                            </li>
                            <li>
                                <a href="https://youtube.com">
                                	<img src='<c:url value="/resources/img/socials/youtube.png" />' alt="Youtube" />
                                </a>
                            </li>
                            <li>
                                <a id="linkin" href="#linkin">
                                	<img src='<c:url value="/resources/img/socials/in.png" />' alt="linkin" />
                                </a>
                            </li>
                            <li>
                                <a id="o" href="#o">
                                	<img src='<c:url value="/resources/img/socials/o.png" />' alt="o" />
                                </a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>

                    <div class="tour-address text-right">
                        <address>
                            <strong>Hiep Nguyen</strong><br />
                            Email: <a href="mailto:hiepamway@gmail.com">hiepamway@gmail.com</a><br />
                            <abbr title="Tel">Tel:</abbr> +84 94 309 68 84<br />
                            Address: Hanoi, Vietnam
                        </address>
                    </div>
                </div>
            </div>
        </div>
    </div>