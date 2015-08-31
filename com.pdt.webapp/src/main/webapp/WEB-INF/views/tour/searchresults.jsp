<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<head>
	<title>Search result</title>
</head>
<div class="main-panel">
        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <div class="tour-breadcrumb">
                        <ul class="breadcrumb">
                            <li>
                                <a href="<c:url value="/" />">Home</a>
                            </li>
                            <li class="active">
                                <span>Search result</span>
                            </li>
                        </ul>
                    </div>
                    <div class="tours-list">
                     <div><h3>Tim thay ${fn:length(tourList)} ket qua</h3></div>
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
			                                            	<a href="<c:url value="/tour/${tourTypePath}/result/0/${vtour.id}/${vtour.seoPath}"/>">${vtour.name}</a>
			                                        </h4>
			                                        <p class="text-justify">${vtour.description}</p>
		                                        </c:if>
		                                        <div class="text-right">
		                                            <a href="<c:url value="/tour/result/0/${vtour.id}/${vtour.seoPath}"/>">détails <span class="arrow"></span><span class="arrow"></span></a>
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
                            <li class="disabled"><a href="#">«</a></li>
							<c:forEach begin="1" end="${pagination.numberOfPage}" step="1" var="p">
                            <c:if test="${pagination.currentPage == p}">
                            <li class="active"><a href="${pageContext.request.contextPath}/tour/${tourTypePath}/${pathId}/${p}">${p} <span class="sr-only">(current)</span></a></li>
                            </c:if>
                            <c:if test="${pagination.currentPage != p}">
								<li><a href="${pageContext.request.contextPath}/tour/${tourTypePath}/${pathId}/${p}">${p} <span class="sr-only">(current)</span></a></li>
                            </c:if>
                            </c:forEach>
                            <!-- <li><a href="/tour/">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li> -->
                            <li><a href="#">»</a></li>
                        </ul>
                    </div>
                    </c:if>
                </div>
                <div class="col-sm-3">
 					<tiles:insertAttribute name="contactbox"/>
                                    </div>
            </div>
        </div>
    </div>