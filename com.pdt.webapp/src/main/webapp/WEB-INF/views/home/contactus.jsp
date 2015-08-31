<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<head>
	<title>Nous Contacter</title>
</head>    
<style type="text/css">
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
                                 <a href='<c:url value="/home"/>'>Home</a>
                            </li>
                            <li class="active">
                                <span>Nous Contacter</span>
                            </li>
                        </ul>
                    </div>

                    <div class="tour-form-panel panel panel-default">
                        <form:form id="createMessage" commandName="newMessage" action="${pageContext.request.contextPath}/sendContactMessage" method="post" class="form-horizontal">
                            <div class="form-group">
                                    <label for="fullname-tour-register" class="col-sm-2 control-label">Nom et prénom <span class="text-danger">(*)</span>:</label>
                                    <div class="col-sm-6">
                                        <form:input path="customer.fullName" class="form-control" />
                                        <form:errors path="customer.fullName" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="email-tour-register" class="col-sm-2 control-label">Email <span class="text-danger">(*)</span>:</label>
                                    <div class="col-sm-6">
                                        <form:input path="customer.email" class="form-control" />
                                        <form:errors path="customer.email" />
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
                                        <form:errors path="customer.phone" />
                                    </div>
                                </div>
                            <div class="form-group">
                                <label for="subject-contact" class="col-sm-2 control-label">Sujet</label>
                                <div class="col-sm-10">
                                    <form:input id="subject-contact" path="subject" class="form-control" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="body-contact" class="col-sm-2 control-label">Contenu</label>
                                <div class="col-sm-10">
                                    <form:textarea id="body-contact" class="form-control" path="content" rows="10" cols="20"></form:textarea>
                                </div>
                            </div>
                            <!-- <div class="form-group">
                                <label for="captcha-contact" class="col-sm-2 control-label">Captcha</label>
                                <div class="col-sm-4">
                                    <input id="captcha-contact" class="form-control" />
                                </div>
                                <div class="col-sm-4">
                                    BCF
                                </div>
                            </div> -->
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-4">
                                    <button type="submit" class="btn btn-primary"><spring:message code="jsp.common.button.send"/></button>
                                    <button type="reset" class="btn btn-default"><spring:message code="jsp.common.button.reset"/></button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
                <div class="col-sm-3">
                	<tiles:insertAttribute name="contactbox"/>

                    <div class="region-filters tour-box">
                       <!--  <div class="tour-head head-info">
                            <h5 class="text-uppercase">Du lịch miền bắc</h5>
                            <span class="head-arrow"></span>
                        </div> -->
                        <!-- <div class="tour-content">
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
                                <li>
                                    <a href="#">Địa danh du lịch khác</a>
                                </li>
                            </ul>
                        </div> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
    <script>
    $("#createMessage").validate({
	            rules: {
	            	"customer.fullName": "required",
	            	"customer.email": {
	            		required:true,
	            		email:true
	            	},
	            	"customer.nationality": "required",
	            	"customer.phone": "required",
	            	//plannedPeriod: "required",
	            	//expectedTime: "required",
	            	subject: "required",
	            	content: "required",
	                },
	            messages: {
		            	"customer.fullName": "Vous n'avez pas entrer un nom de contact",
		            	"customer.email": "Vous n'avez pas entrer une adresse e-mail!",
		            	"customer.nationality":"Vous n'avez pas entrer une Nationalité",
		            	"customer.phone": "Vous n'avez pas entré un téléphone!",
		            	subject: "Vous n'avez pas entré de titre!",
		            	content:"champ est requis"
	                }
	        });
    </script>