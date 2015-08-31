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
	<title>Demander un Devis</title>
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
                                <a href="<c:url value="/home"/>">Home</a>
                            </li>
                            <li class="active">
                                <span>Demander un Devis</span>
                            </li>
                        </ul>
                    </div>
                    <div class="tour-form-panel panel panel-default">
                        <div class="tours-register tour-box">
                            <form:form id = "createRequest" commandName="newRequest" action="${pageContext.request.contextPath}/createDesignedTour" method="post" class="form-horizontal">
                                <h4 class="text-danger text-uppercase text-center">Demande de devis pour les circuits au Vietnam</h4>
                                <br />
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
                                    <label for="rg-6-register" class="col-sm-6 control-label">Date prévue pour l'arrivée: <span class="text-danger">(*)</span></label>
                                    <div class="col-sm-6">
                                        <form:input id="rg-6-register" type="text" path="expectedTime" class="form-control" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="rg-7-register" class="col-sm-6 control-label">Indiquer le nombre d'adultes participants: <span class="text-danger">(*)</span></label>
                                    <div class="col-sm-6">
                                        <form:input id="rg-7-register" type="text" path="numberOfAdults" class="form-control" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="rg-8-register" class="col-sm-6 control-label">Indiquer parmis eux le nombre de couples: <span class="text-danger">(*)</span></label>
                                    <div class="col-sm-6">
                                        <form:input id="rg-8-register" type="text" path="numberOfCouple" class="form-control" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="rg-9-register" class="col-sm-6 control-label">Indiquer le nombre d'enfants mineurs participants: <span class="text-danger">(*)</span></label>
                                    <div class="col-sm-6">
                                        <form:input id="rg-9-register" type="text" path="numberOfparticipantMinors" class="form-control" />
                                    </div>
                                </div>
                                <br />
                                <div class="text-center">
                                    <strong>Indiquer les lieux que vous souhaitez decouvrir</strong>
                                </div>
                                <div class="row">
                                    <div class="col-sm-offset-2 col-sm-8">
                                        <div class="row">
                                            <div class="col-sm-4">
                                                <h4>Nord</h4>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Ha Long"/>
                                                         Baie d'Halong
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Cao Bang"/>
                                                        Cao Bang
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Ninh Binh"/>
                                                        Ninh Binh
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Sa pa"/>
                                                        SA PA
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Ha Giang"/>
                                                        Ha Giang
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Dien Bien"/>
                                                        Dien Bien
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Son La"/>
                                                        Son La
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Mai Chau"/>
                                                        Mai Chau
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Hanoi"/>
                                                        Hanoi
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Tuyen Quang"/>
                                                        Tuyen Quang
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Ba Be"/>
                                                        Ba Be
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Yen Bai"/>
                                                        Yên Bai
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Viet Tri"/>
                                                        Viet Tri
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Hoa Binh"/>
                                                        Hoa Binh
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <h4>Centre</h4>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Da Nang"/>
                                                        Da Nang
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="My Son"/>
                                                        My Son
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Hue"/>
                                                        Hue
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Hoi An"/>
                                                        Hoi An
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <h4>Sud</h4>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Can Tho"/>
                                                        Can Tho
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Mui Ne"/>
                                                        Mui Ne
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value=" Ile de Phu Quoc"/>
                                                         Ile de Phu Quoc
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Tunnel Cu Chi"/>
                                                        Tunnel Cu Chi
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Vung Tau"/>
                                                        Vung Tau
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="HCM"/>
                                                        Ho Chi Minh ville
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Nha Trang"/>
                                                        Nha Trang
                                                    </label>
                                                </div>
                                                <div>
                                                    <label>
                                                        <form:checkbox path="places" value="Da Lat"/>
                                                        Da Lat
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label  class="col-xs-12 col-sm-6 col-md-8" for="rg-9-register">Indiquer les moyens de transport que vous voulez prendre pour votre voyage: </label>
                                    <div class="col-xs-6 col-md-4">
                                        <form:checkbox path="transportTypes" value="Voiture bus"/> Voiture/bus
                                        <form:checkbox path="transportTypes" value="Train"/> Train
                                    </div>
                                </div>
                                 <div class="form-group">
                                    <label  class="col-xs-6" for="rg-9-register">Indiquer le type de voyage que vous souhaitez effectuer: </label>
                                    <div class="col-xs-6 ">
                                        <form:checkbox path="tourTypes" value="Découverte culturelles et classique"/> Découverte culturelles et classique <br />
                                        <form:checkbox path="tourTypes" value="Voyager hors des sentiers battus"/> Voyager hors des sentiers battus <br />
                                        <form:checkbox path="tourTypes" value="Ciruit d'aventures( randonnées, kayak, vélo ..)"/> Ciruit d'aventures( randonnées, kayak, vélo ..) <br />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label  class="col-xs-6" for="rg-9-register">Indiquer le type d'hebergement que vous preferez: </label>
                                    <div class="col-xs-6 ">
                                        <form:checkbox path="accommodation" value="Chez l'habitant"/> Chez l'habitant <br />
                                        <form:checkbox path="accommodation" value="Economie"/> Economie : &lt 30 Euros <br />
                                        <form:checkbox path="accommodation" value="Standard"/>  Standard : 30 - 45 Euros <br />
                                        <form:checkbox path="accommodation" value="Première classe"/>  Première classe : 46 - 70 Euros <br />
                                        <form:checkbox path="accommodation" value="Supérieur"/>  Supérieur : 80 - 100 Euros <br />
                                        <form:checkbox path="accommodation" value="Deluxe"/>  Deluxe : 100 -150 Euros et plus <br />
                                    </div>
                                </div>    

                                <div class="form-group">
                                    <label  class="col-xs-6" for="rg-9-register">Indiquer les repas que vous souhaitez inclure dans le prix de votre voyage: </label>
                                    <div class="col-xs-6 ">
                                        <form:checkbox path="meal" value="B"/> Petit déjeuner (B) inclu normalement à l'hôtel <br />
                                        <form:checkbox path="meal" value="L"/> Déjeunner (L) <br />
                                        <form:checkbox path="meal" value="D"/> Dîner (D)  <br />
                                    </div>
                                </div>

                                <div>
                                    <label for="rg-10-register" class="control-label" style="">Précisez d'éventuelles informations complémentaires</label>
                                    <form:textarea id="rg-10-register" path="detail" rows="5" cols="20" class="form-control"></form:textarea>
                                </div>
                                <br />
                                <div class="text-center">
                                    <button type="submit" class="btn btn-primary"><spring:message code="jsp.common.button.send"/></button>
                                    <button type="reset" class="btn btn-default"><spring:message code="jsp.common.button.reset"/></button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
					<tiles:insertAttribute name="contactbox"/>
                    <%-- <div class="region-filters tour-box">
                        <div class="tour-head head-info">
                            <h5 class="text-uppercase">Du lịch Việt Nam</h5>
                            <span class="head-arrow"></span>
                        </div>
                        <div class="tour-content">
                            <ul>
                                <li>
                                    <a href="#">Hạ Long</a>
                                </li>
                                <li>
                                    <a href="#">Cao Bằng</a>
                                </li>
                                <li>
                                    <a href="#">Ninh Bình</a>
                                </li>
                                <li>
                                    <a href="#">SAPA</a>
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
                        </div>
                    </div> --%>
                </div>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
    <script>
    $("#createRequest").validate({
        rules: {
        	"customer.fullName": "required",
        	"customer.email": {
        		required:true,
        		email:true
        	},
        	"customer.nationality": "required",
        	"customer.address": "required",
        	"customer.phone": "required",
        	numberOfAdults: {
        		required:true,
        		number:true
        	},
        	numberOfCouple: {
        		required:true,
        		number:true
        	},
        	numberOfparticipantMinors: {
        		required:true,
        		number:true
        	},
        	//plannedPeriod: "required",
        	//expectedTime: "required",
            },
        messages: {
            	"customer.fullName": "Vous n'avez pas entrer un nom de contact",
            	"customer.email": "Vous n'avez pas entrer une adresse e-mail!",
            	"customer.nationality":"Vous n'avez pas entrer une Nationalité",
            	"customer.phone": "Vous n'avez pas entré un téléphone!"
            }
    });
    </script>