<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
	<title>Bienvenue au Vietnam</title>
</head>
<div class="main-panel">
        <div class="container">
            <div class="slider-box">
                <div class="row">
                    <div class="col-sm-8">
                        <div class="tour-slider tour-box">
                            <div id="carousel-tour-generic" class="carousel slide" data-ride="carousel">
                            <%-- <c:if test="${fn:length(images) > 0 }">
				                    <div class="tour-items-slider tour-box">
				                        <!--<img src="Uploads/sliders/slide-2.jpg" alt="" class="img-responsive" />-->
				                        <ul class="bx-slider">
				                        <c:forEach var = "image" items="${images}"> 
				                        	<li><img src="${pageContext.request.contextPath}${image}" /></li>
				                        	
		                        	<c:forEach var = "image" varStatus="status" begin="0" end="${fn:length(images) - 1}" step="1" items="${images}">
                       	<a data-slide-index="${status.count - 1}" href=""><img src="${pageContext.request.contextPath}${image}" /></a>
	                        </c:forEach>
                        	--%>
                                <!-- Indicators -->
                                <c:if test="${fn:length(slideImages) > 0 }">
                                <ol class="tour-carousel-indicators carousel-indicators">
                              <%--   <c:if test="${status.count - 1 == 0}">
                                    <li data-target="#carousel-tour-generic" data-slide-to="${status.count - 1}" class="active"></li>
                                   </c:if> --%>
                                   <c:forEach var = "image" varStatus="status" begin="0" end="${fn:length(slideImages) - 1}" step="1" items="${slideImages}">
                                   <c:choose>
								      <c:when test="${status.count - 1 == 0}">
								      	<li data-target="#carousel-tour-generic" data-slide-to="${status.count - 1}" class="active"></li>
								      </c:when>
								      <c:otherwise>
								      	<li data-target="#carousel-tour-generic" data-slide-to="${status.count - 1}"></li>
								      </c:otherwise>
									</c:choose>
									</c:forEach>
                                   <!--  <li data-target="#carousel-tour-generic" data-slide-to="1"></li>
                                    <li data-target="#carousel-tour-generic" data-slide-to="2"></li> -->
                                </ol>
								
                                <!-- Wrapper for slides -->
                                <div class="carousel-inner">
                                <c:forEach var = "image" varStatus="status" begin="0" end="${fn:length(slideImages) - 1}" step="1" items="${slideImages}">
                                <c:choose>
                                    <c:when test="${status.count - 1 == 0}">
                                    <div class="item active">
                                        <img src="${pageContext.request.contextPath}${image.tempPath}" alt="" class="img-responsive" />
                                        <c:if test="${empty image.linkCaption}">
                                        <div class="carousel-caption">
                                            <h3><a href="${image.link}">${image.linkCaption}</a></h3>
                                        </div>
                                        </c:if>
                                    </div>
                                    </c:when>
                                    <c:otherwise>
								      	<div class="item">
                                        <img src="${pageContext.request.contextPath}${image.tempPath}" alt="" class="img-responsive" />
                                        <c:if test="${empty image.linkCaption}">
                                        <div class="carousel-caption">
                                            <h3><a href="${image.link}">${image.linkCaption}</a></h3>
                                        </div>
                                        </c:if>
                                    </div>
								      </c:otherwise>
								      </c:choose>
                                </c:forEach>
                                    <!-- <div class="item">
                                        <img src="resources/Uploads/sliders/slide-1.jpg" alt="" class="img-responsive" />
                                        <div class="carousel-caption">
                                             <h3><a href="/north">Núi biển đảo</a></h3>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <img src="resources/Uploads/sliders/slide-1.jpg" alt="" class="img-responsive" />
                                        <div class="carousel-caption">
                                            <h3><a href="/north">Núi rừn</a></h3>
                                        </div>
                                    </div> -->
                                </div>

                                <!-- Controls -->
                                <a class="left carousel-control" href="#carousel-tour-generic" role="button" data-slide="prev">
                                    <span class="glyphicon glyphicon-chevron-left"></span>
                                </a>
                                <a class="right carousel-control" href="#carousel-tour-generic" role="button" data-slide="next">
                                    <span class="glyphicon glyphicon-chevron-right"></span>
                                </a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="tour-filters tour-box">
                            <div class="tour-head head-info">
                                <h5 class="text-uppercase">filtre</h5>
                                <span class="head-arrow"></span>
                            </div>
                            <div class="tour-content">
                            
                                <form class="form-horizontal" role="form" method="GET" action="${pageContext.request.contextPath}/tour/search">
                                    <div class="form-group">
                                        <label for="region-search-filter" class="control-label col-sm-3">Région</label>
                                        <div class="col-sm-9">
                                            <%-- <select id="region-search-filter" class="form-control">
                                                <option>- Peu improte</option>
                                            </select> --%>
                                            <select name="region" class="form-control">
                                            		<option value="NONE" selected="selected">Peu importe</option>
							                		<option value="NORTH">Nord</option>
							                		<option value="CENTRAL">Centre</option>
							                		<option value="SOUTH">Sud</option>
							                	</select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="subject-search-filter" class="control-label col-sm-3">Sujet</label>
                                        <div class="col-sm-9">
                                        <select class="form-control" name="subject">
                                        <c:if test="${not empty requestScope.TOP_MENUBAR.tourBySubjects}">
                                      		<option value="NONE" selected="selected">Peu importe</option>
		                                 	<c:forEach var="subject" items="${requestScope.TOP_MENUBAR.tourBySubjects}">
		                                 		<option value="${subject.key}">${subject.value}</option>
		                                    </c:forEach>
			                            </c:if>
			                            </select>
                                            <%-- <select id="subject-search-filter" class="form-control">
                                                <option>- Peu improte</option>
                                            </select> --%>
                                        </div>
                                    </div>
                                    <!-- <div class="form-group">
                                        <label for="from-search-filter" class="control-label col-sm-3" title="day(s)">Form</label>
                                        <div class="col-sm-9">
                                            <input id="from-search-filter" type="text" class="form-control" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="to-search-filter" class="control-label col-sm-3" title="day(s)">To</label>
                                        <div class="col-sm-9">
                                            <input id="to-search-filter" type="text" class="form-control" />
                                        </div>
                                    </div> -->

                                    <div class="form-group">
                                        <div class="col-sm-offset-3 col-sm-9">
                                            <label class="control-label">
                                                <input type="checkbox" name="hasEvent"/>
                                                Inclure événement
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-7 col-sm-offset-3">
                                            <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> <spring:message code="jsp.common.button.send"/></button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="suggession-tour">
                <div class="row">
                    <div class="col-sm-9">
                        <div class="suggession-tour-top tour-box">
                            <div class="row">
                            <c:forEach var="rtour" items="${recommendTours}">
                                <div class="col-sm-4">
                                    <div class="tour-item">
                                    	<c:if test="${rtour.thumbnail != null}">
                                        	<img src='${pageContext.request.contextPath}${rtour.thumbnail}' alt="" class="img-responsive" />
                                        </c:if>
                                        <div class="desc">
                                        	<c:if test="${not empty rtour.name}">
                                        		<a href="<c:url value="/tour/recommend/0/${rtour.id}/${rtour.seoPath}"/>">${rtour.name}</a>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="tour-time">
                                        <ul>
                                            <li><strong>Jour:</strong> 
                                            	<c:if test="${rtour.tour.minDay != 0}">
			                                    	à partir de ${rtour.tour.minDay}
			                                    </c:if>
			                                    <c:if test="${rtour.tour.maxDay != 0}">
			                                    	- ${rtour.tour.maxDay}
			                                    </c:if> jours
                                            </li>
                                            <li><strong>Prix:</strong>
                                            <c:if test="${rtour.tour.fromPrice != 0}">
		                                    	à partir de ${rtour.tour.fromPrice}
		                                    </c:if>
		                                    <c:if test="${rtour.tour.toPrice != 0}">
		                                    	- ${rtour.tour.toPrice}
		                                    </c:if> €
											</li>
                                        </ul>
                                    </div>
                                </div>
                                </c:forEach>
                               <%--  <div class="col-sm-4">
                                    <div class="tour-item">
                                        <img src="resources/Uploads/tour-hot-2.jpg" alt="" class="img-responsive" />
                                        <div class="desc">
                                            <a href="tour-details.html" class="text-uppercase" title="Khám phá đà lạt về đêm">Khám phá đà lạt về đêm</a>
                                        </div>
                                    </div>
                                    <div class="tour-time">
                                        <ul>
                                            <li><strong>Thời gian:</strong> từ 5 - 7 ngày</li>
                                            <li><strong>Giá tour:</strong> 500$ - 700$</li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="tour-item">
                                        <img src="resources/Uploads/tour-hot-3.jpg" alt="" class="img-responsive" />
                                        <div class="desc">
                                            <a href="tour-details.html" class="text-uppercase" title="Du lịch cung đình huế">Du lịch cung đình huế</a>
                                        </div>
                                    </div>
                                    <div class="tour-time">
                                        <ul>
                                            <li><strong>Thời gian:</strong> từ 5 - 7 ngày</li>
                                            <li><strong>Giá tour:</strong> 500$ - 700$</li>
                                        </ul>
                                    </div>
                                </div> --%>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <!-- <div class="home-contact tour-box">
                            <img src="resources/img/contact.jpg" alt="" class="img-responsive" />

                            <table width="100%;">
                                <tr>
                                    <td align="center">
                                        <a href="#"><img src="resources/img/yahoo.jpg" alt="Yahoo" /></a>
                                    </td>
                                    <td align="center">
                                        <a href="#"><img src="resources/img/skype.jpg" alt="Skype" /></a>
                                    </td>
                                </tr>
                            </table>

                            <address>
                                Hotline: +84 912 343 443 15<br />
                                Email: <a href="mailto:contact@isoftz.com">contact@isoftz.com</a>
                            </address>
                        </div> -->
                        <tiles:insertAttribute name="contactbox"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-6 col-md-7">
                    <div class="event-most-tour">
                        <div class="event-list tour-box">
                            <div class="tour-head head-info inline-block">
                                <h5 class="text-uppercase">Prochain rendez-vous</h5>
                                <span class="head-arrow"></span>
                            </div>
                            <div class="tour-content">
                                <div class="box-scroll">
                                    <table>
                                    	<c:forEach var="e" items="${events}">
                                        <tr>
                                            <td><a href = '<c:url value = "/tour/event/${e.id}"/>'>${e.name}</a></td>
                                            <td class="text-center">
                                            <fmt:formatDate type="date" dateStyle="short" timeStyle="short" value="${e.beginDate}" /> - 
                                            <fmt:formatDate type="date" dateStyle="short" timeStyle="short" value="${e.endDate}" /> 
                                            <c:if test="${not empty e.destination.city}">
                                            	<td>${e.destination.city.city}</td>
                                            </c:if>
                                        </tr>	
                                        <!-- <tr>
                                            <td>Lễ hội Chùa Hương</td>
                                            <td class="text-center">15/02 - 20/02</td>
                                            <td>Hà Nội</td>
                                        </tr>
                                        <tr>
                                            <td>Fatival Huế 2014</td>
                                            <td class="text-center">12/04 - 14/04</td>
                                            <td>Thừa thiên huế</td>
                                        </tr>
                                        <tr>
                                            <td>Tuần lễ Vàng Du lịch</td>
                                            <td class="text-center">26/03 - 01/04</td>
                                            <td>Huế</td>
                                        </tr>
                                        <tr>
                                            <td>Lễ hội Đền Hùng</td>
                                            <td class="text-center">27/03 - 31/04</td>
                                            <td>Phú Thọ</td>
                                        </tr>
                                        <tr>
                                            <td>Ngày quốc khánh 2/9</td>
                                            <td class="text-center">01/09 - 02/09</td>
                                            <td>Toàn quốc</td>
                                        </tr>
                                        <tr>
                                     beginDate       <td>Ngày quốc khánh 2/9</td>
                                            <td class="text-center">01/09 - 02/09</td>
                                            <td>Toàn quốc</td>
                                        </tr> -->
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="most-favorite tour-box">
                            <div class="tour-head head-danger inline-block">
                                <h5 class="text-uppercase">La plupart des voyages de faveur</h5>
                                <span class="head-arrow"></span>
                            </div>
                            <div class="tour-content">
                                <ul>
                                	<c:forEach var="ftour" items="${favourTours}">
	                                    <li>
	                                        <table>
	                                            <colgroup>
	                                                <col />
	                                                <col width="100" />
	                                            </colgroup>
	                                            <tr>
	                                                <td>
	                                                    <h6><a href='<c:url value="/tour/favour/0/${ftour.id}/${ftour.seoPath}"></c:url>'>${ftour.name}</a></h6>
	                                                    <c:if test="${ftour.description != null}">
	                                                    	<p>${fn:substringBefore(ftour.description, '.')}</p>
	                                                    </c:if>
	                                                    
	                                                </td>
	                                                <td class="text-center">
	                                                    <a href='<c:url value="/tour/favour/0/${ftour.id}/${ftour.seoPath}"></c:url>'>
	                                                        <img src='<c:url value = "/resources/img/see-more.jpg"></c:url>' alt="" />
	                                                    </a>
	                                                </td>
	                                            </tr>
	                                        </table>
	                                    </li>
                                    </c:forEach>
                                   <%--  <li>
                                        <table>
                                            <colgroup>
                                                <col />
                                                <col width="100" />
                                            </colgroup>
                                            <tr>
                                                <td>
                                                    <h6><a href="#">Population Du Viet Nam</a></h6>
                                                    <p>Vietnam compte, en 2014, 90 millions d’habitants dont le tiers a moins de 20 ans ...</p>
                                                </td>
                                                <td class="text-center">
                                                    <a href="#">
                                                        <img src="resources/img/see-more.jpg" alt="" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </table>
                                    </li>
                                    <li>
                                        <table>
                                            <colgroup>
                                                <col />
                                                <col width="100" />
                                            </colgroup>
                                            <tr>
                                                <td>
                                                    <h6><a href="#">Population Du Viet Nam</a></h6>
                                                    <p>Vietnam compte, en 2014, 90 millions d’habitants dont le tiers a moins de 20 ans ...</p>
                                                </td>
                                                <td class="text-center">
                                                    <a href="#">
                                                        <img src="resources/img/see-more.jpg" alt="" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </table>
                                    </li> --%>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-md-5">
                    <div class="culture-travel tour-box">
                    	<c:if test= "${not empty stickSubjectTours}">
                    	
                        <div class="tour-head">
                            <img src='<c:url value = "/resources/img/viet-nam-tour.png" />' alt="" />
                            <h5 class="text-uppercase">${stickedSubjectName}</h5>
                        </div>
                        <div class="tour-content">
                            <ul>
                            <c:forEach items="${stickSubjectTours}" var="st">
                                <li>
                                    <div class="culture-item">
                                        <img src="${pageContext.request.contextPath}${st.thumbnail}" alt="" class="img-responsive" />

                                        <div class="desc">
                                            <a href="tour-details.html" class="text-uppercase" title="${st.name}">${st.name}</a>
                                            <!-- <p class="text-uppercase">
                                                Nơi lưu giữ những giá trị văn hóa truyền thống
                                            </p> -->
                                        </div>
                                    </div>
                                </li>
                                </c:forEach>
                                <!-- <li>
                                    <div class="culture-item">
                                        <img src="resources/Uploads/culture-2.jpg" alt="" class="img-responsive" />

                                        <div class="desc">
                                            <a href="tour-details.html" class="text-uppercase" title="Múa rối nước">Múa rối nước</a>
                                            <p class="text-uppercase">
                                                Múa rối nước đã ra đời chừng hơn 10 thế kỷ trước ở vùng châu thổ sông Hồng.
                                            </p>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="culture-item">
                                        <img src="resources/Uploads/culture-3.jpg" alt="" class="img-responsive" />

                                        <div class="desc">
                                            <a href="tour-details.html" class="text-uppercase" title="Khám Phá Vẻ Đẹp Vịnh Hạ Long">Khám Phá Vẻ Đẹp Vịnh Hạ Long</a>
                                            <p class="text-uppercase">
                                                Vịnh Hạ Long là một điểm du lịch bạn nên đi một lần trong đời. Bởi phong cảnh thiên nhiên được tạo
                                            </p>
                                        </div>
                                    </div>
                                </li> -->
                            </ul>
                        </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>