<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
	<title>Hotel - ${hotel.name}</title>
</head>
<link href="${pageContext.request.contextPath}/resources/css/star-rating.css" rel="stylesheet" />
<style type="text/css">
        .form-inline .form-group{
        margin-left: 0;
        margin-right: 0;
        }
        
        .form-inline .form-control{
        min-width:60px;
        }
        .form-inline .control-label{
        width:160px;
        }
        .error {
        	color:red;
        }
    </style>
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
                                <a href="<c:url value="/hotel/${hotel.cityId}"/>">${cityName}</a>
                            </li>
                            <li class="active">
                                <span>${hotel.name}</span>
                            </li>
                        </ul>
                    </div>
                    <c:if test="${message != null}">
             			<div class="error"><p>${message}</p></div>
             		</c:if>
                    <div class="tour-form-panel panel panel-default">
                        <div class="hotel-details">
                            <%-- <address>
                                <strong>Address: No 18 Lo Su - Hoan Kiem District</strong>
                                <br />
                                <strong>HanoiPhone: (84-4) 39351616</strong>
                                <br />
                                <strong>Fax: (84-4) 39351815</strong>
                                <br />
                                <strong>Website: <a href="http://www.hanoihappyhotel.com">http://www.hanoihappyhotel.com</a></strong>
                            </address> --%>
                            <p>
                                ${articles.lead}
                            </p>
                            <form action="#" method="POST">
                                <input id="input-21d" value="${hotel.stars}" type="number" class="rating" min="0" max="5" step="1" data-size="xs">
                            </form>
                        </div>
                    </div>

                    <div class="tour-form-panel panel panel-default">
                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs" role="tablist">
                            <li class="active"><a href="#tab-1" role="tab" data-toggle="tab">Itinéraire détaillé</a></li>
                            <li><a href="#tab-2" role="tab" data-toggle="tab">Demande de prix</a></li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div class="tab-pane active" id="tab-1">
                                <br />
                                ${articles.content}
                            </div>
                            
                           <div class="tab-pane" id="tab-2">
                                    <p>Nous vous remercions de l'intérêt que vous portez à PearlDragonTravel. Pour assurer la qualité de nos circuits, nous nous sommes spécialisés dans l'organisation de circuits individuels types. C'est à dire que vous voyagerez accompagnés d'un guide et d'un chauffeur. C'est la raison pour laquelle la date du départ est selon votre décision. C'est avec plaisir que nous sommes à votre entière disposition pour composer votre circuit à votre convenance et respectant votre budget.</p>
                                    <br />
                                    <p>Les champs précédés d'un <span class="text-danger">(*)</span> sont obligatoires</p>

                                    <form:form id = "createRequest" commandName="newRequest" action="${pageContext.request.contextPath}/hotel/${hotel.cityId}/${hotel.id}/${hotel.seoPath}/createRequest" method="post" class="form-horizontal" role="form">
                                    	<form:hidden path="productId"/>
                                    	<div class="form-group">
							                <label for="name-tour-register" class="col-sm-2 control-label">Nom du Tour <span class="text-danger">(*)</span>:</label>
							                <div class="col-sm-6">
							                    <input name=productName value="${hotel.name}" class="form-control" readonly="readonly" />
							                </div>
							            </div>
                                        <div class="form-group">
                                            <label for="fullname-tour-register" class="col-sm-2 control-label">Nom et prénom <span class="text-danger">(*)</span>:</label>
                                            <div class="col-sm-6">
                                                <form:input path="customer.fullName" class="form-control" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="email-tour-register" class="col-sm-2 control-label">Email <span class="text-danger">(*)</span>:</label>
                                            <div class="col-sm-6">
                                                <form:input path="customer.email" class="form-control" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="address-tour-register" class="col-sm-2 control-label">Nationalité <span class="text-danger">(*)</span>:</label>
                                            <div class="col-sm-6">
                                                <form:input path="customer.nationality"  class="form-control" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="address-tour-register" class="col-sm-2 control-label">Ville </label>
                                            <div class="col-sm-6">
                                                <form:input path="customer.address"  class="form-control" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="phone-tour-register" class="col-sm-2 control-label">Téléphone <span class="text-danger">(*)</span></label>
                                            <div class="col-sm-6">
                                                <form:input path="customer.phone" class="form-control" />
                                            </div>
                                        </div>
                                        <div class="form-group">
							                <label for="phone-tour-register" class="col-sm-2 control-label">Nombre de <span class="text-danger">(*)</span></label>
							                <div class="col-sm-6">
							                     <div>
							                        <div class="form-inline">
								                        <label class="col-sm-2 control-label"> Adulte</label><span class="text-danger">(*)</span>
							                            <form:input path="numberOfAdults" class="form-control" placeholder="1"/>
							                        </div>
							                        <div class="form-inline">
							                            <label class="col-sm-2 control-label"> old de 2 à 9 ans </label><span class="text-danger">(*)</span>
							                            <form:input path="numberOfChildren2to9" type="text" class="form-control" placeholder="0"/>
							                        </div>
							                        <div class="form-inline">
														<label class="col-sm-2 control-label"> Moins de 2 ans</label><span class="text-danger">(*)</span>
							                            <form:input path="numberOfChildrenLessThan2" type="text" class="form-control" placeholder="0"/>
							                        </div>
							                    </div>
							                </div>
							            </div>
							            <div class="form-group">
                                            <label for="subject-tour-register" class="col-sm-2 control-label">Période prévue de voyage </label>
                                            <div class="col-sm-6">
                                                <form:input path="plannedPeriod" type="text" class="form-control" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="subject-tour-register" class="col-sm-2 control-label">Durée prévue de voyage</label>
                                            <div class="col-sm-6">
                                                <form:input path="expectedTime" type="text" class="form-control" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="subject-tour-register" class="col-sm-2 control-label">Sujet <span class="text-danger">(*)</span>:</label>
                                            <div class="col-sm-6">
                                                <form:input path="subject" id="subject-tour-register" type="text" class="form-control" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="body-tour-register" class="col-sm-2 control-label">Description de vos souhaits <span class="text-danger">(*)</span>:</label>
                                            <div class="col-sm-6">
                                                <form:textarea path="detail" class="form-control" rows="5" cols="20"/>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-6">
                                                <button type="submit" class="btn btn-primary"><spring:message code="jsp.common.button.send"/></button>
                                                <button type="reset" class="btn btn-default"><spring:message code="jsp.common.button.reset"/></button>
                                            </div>
                                        </div>
                                    </form:form>
                                </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <tiles:insertAttribute name="contactbox"/>

                    <div class="region-filters tour-box">
                        <div class="tour-head head-info">
                            <h5 class="text-uppercase">Hotels au Vietnam</h5>
                            <span class="head-arrow"></span>
                        </div>
                        <div class="tour-content">
                            <ul>
                            <c:if test="${not empty relatedHotels}">
                        		<c:forEach var = "h" items="${relatedHotels}">
                        			<li>
                                    	<a href="<c:url value="/hotel/${h.cityId}/${h.id}/${h.seoPath}"/>">${h.name}</a>
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
