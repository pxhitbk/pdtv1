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
	<title>${tour.name}</title>
	<link href="${pageContext.request.contextPath}/resources/plugins/bxslider/jquery.bxslider.css" rel="stylesheet" />
</head>
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

            <div class="tour-breadcrumb">
                <ul class="breadcrumb">
                    <li>
                        <a href="<c:url value="/home"/>">Home</a>
                    </li>
                    <li class="active">
                        <span>${tour.name}</span>
                    </li>
                </ul>
            </div>
            <c:if test="${message != null}">
             		<div class="error"><p>${message}</p></div>
             	</c:if>
            <div class="row">
                <div class="col-sm-6">
                	<c:if test="${fn:length(images) > 0 }">
                    <div class="tour-items-slider tour-box">
                        <!--<img src="Uploads/sliders/slide-2.jpg" alt="" class="img-responsive" />-->
                        <ul class="bx-slider">
                        <c:forEach var = "image" items="${images}">
                        	<li><img src="${pageContext.request.contextPath}${image}" /></li>
                        </c:forEach>
                            <%-- <li><img src="${pageContext.request.contextPath}/resources/Uploads/sliders/slide-2.jpg" /></li>
                            <li><img src="${pageContext.request.contextPath}/resources/Uploads/sliders/slide-2.jpg" /></li>
                            <li><img src="${pageContext.request.contextPath}/resources/Uploads/sliders/slide-2.jpg" /></li> --%>
                        </ul>

                        <div id="bx-pager">
                        	<c:forEach var = "image" varStatus="status" begin="0" end="${fn:length(images) - 1}" step="1" items="${images}">
	                        	<a data-slide-index="${status.count - 1}" href=""><img src="${pageContext.request.contextPath}${image}" /></a>
	                        </c:forEach>
                            <%-- <a data-slide-index="0" href=""><img src="${pageContext.request.contextPath}/resources/Uploads/sliders/slide-2.jpg" /></a>
                            <a data-slide-index="1" href=""><img src="${pageContext.request.contextPath}/resources/Uploads/sliders/slide-2.jpg" /></a>
                            <a data-slide-index="2" href=""><img src="${pageContext.request.contextPath}/resources/Uploads/sliders/slide-2.jpg" /></a> --%>
                        </div>
                    </div>
                    </c:if>
                </div>
                <div class="col-sm-6">
                    <div class="tour-form-panel panel panel-default">
                        <%-- <p>
                            Để nói về vẻ đẹp của Vịnh Hạ Long thì dường như chẳng có bút mực nào tả xiết, chỉ biết rằng đó là một trong những nơi bạn nên đến một lần trong đời. Với voucher lần này của Hotdeal, bạn không chỉ được tham quan, khám phá Vịnh Hạ Long mà còn được tận hưởng những ngày nghỉ thật thoải mái và trải nghiệm những dịch vụ đẳng cấp trên  thuyền Majestic và các khách sạn 4 sao tại Hạ Long. Chỉ 2.380.000 đồng cho một chuyến du lịch 3 ngày 2 đêm với rất nhiều điều lý thú, đừng bỏ qua cơ hội này bạn nhé!
                            <br />
                            <strong>- Tham quan cơ sở nuôi cấy ngọc trai – Làng Ngọc Trai</strong>
                            <br />
                            <strong>- Hang Sửng Sốt</strong>
                            <br />
                            <strong>- Nhiều hoạt động thú vị và bổ ích</strong>
                        </p> --%>
                        <p>
                        ${articles.lead}
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-3">
					<tiles:insertAttribute name="contactbox"/>
                    <div class="others-tour-filters tour-box">
                    <c:forEach items="${relatedTours}" var="rtour">
                        <div class="tour-list-item panel panel-default">
                            <div class="tour-item">
                                <img src="${pageContext.request.contextPath}${rtour.thumbnail}" alt="" class="img-responsive">
                                <div class="desc">
                                    <a href="<c:url value="/tour/${tourTypePath}/${pathId}/${rtour.id}/${rtour.seoPath}"/>" class="text-uppercase" title="Du lịch núi rừng tây bắc">${rtour.name}</a>
                                </div>
                            </div>
                            <div class="tour-time">
                                <ul>
                                    <li><strong>Jour:</strong> 
	                                    <c:if test="${rtour.tour.minDay != 0}">
	                                    	từ ${rtour.tour.minDay}
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
	                                    </c:if> 
	                                    €
                                    </li>
                                </ul>
                            </div>
                        </div>
						</c:forEach>
                        <%-- <div class="tour-list-item panel panel-default">
                            <div class="tour-item">
                                <img src="Uploads/tour-hot-2.jpg" alt="" class="img-responsive">
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

                        <div class="tour-list-item panel panel-default">
                            <div class="tour-item">
                                <img src="Uploads/tour-hot-3.jpg" alt="" class="img-responsive">
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
                        </div>

                        <div class="tour-list-item panel panel-default">
                            <div class="tour-item">
                                <img src="Uploads/tour-hot-3.jpg" alt="" class="img-responsive">
                                <div class="desc">
                                    <a href="tour-details.html" class="text-uppercase" title="Thưởng ngoạn Hạ Long">Thưởng ngoạn Hạ Long</a>
                                </div>
                            </div>
                            <div class="tour-time">
                                <ul>
                                    <li><strong>Thời gian:</strong> từ 7 - 10 ngày</li>
                                    <li><strong>Giá tour:</strong> 500$ - 700$</li>
                                </ul>
                            </div>
                        </div>--%>
                    </div> 
                    
                </div> <!-- End related tours sidebar -->

                <div class="col-sm-9">
                    <div class="tour-details-content tour-box">
                        <div class="tour-form-panel panel panel-default">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="active"><a href="#tab-1" role="tab" data-toggle="tab">Itinéraire détaillé</a></li>
                                <li><a href="#tab-2" role="tab" data-toggle="tab">Commentaire</a></li>
                                <li><a href="#tab-3" role="tab" data-toggle="tab">Demande de prix</a></li>
                            </ul>

                            <!-- Tab panes -->
                            <div class="tab-content">
                                <div class="tab-pane active" id="tab-1">
                                   ${articles.content}
                                </div>
                                <div class="tab-pane" id="tab-2">
                                    <br />
                                    <div class="tour-comments">
                                        <form:form id="addComment" class="form-horizontal" role="form" method="POST" action="${pageContext.request.contextPath}/tour/createComment.json" modelAttribute="newComment" commandname="newComment">
                                                <div class="form-group">
                                                <label for="tour-comment-name" class="col-sm-2 control-label">Nom et prénom <span class="text-danger">(*)</span>:</label>
                                                <div class="col-sm-6">
                                                    <form:input class="form-control" path="author"/>
                                                </div>
                                                </div>
                                                <div class="form-group">
                                                <label for="tour-comment-email" class="col-sm-2 control-label">Email :</label>
                                                <div class="col-sm-6">
                                                    <form:input class="form-control" path="email"/>
                                                </div>
                                                </div>
                                                <div class="form-group">
                                                <label for="tour-comment-address" class="col-sm-2 control-label">Adresse:</label>
                                                <div class="col-sm-6">
                                                    <form:input class="form-control" path="address"/>
                                                </div>
                                                </div>
                                                <div class="form-group">
                                                <label for="tour-comment-content" class="col-sm-2 control-label">Content <span class="text-danger">(*)</span>:</label>
                                                <div class="col-sm-6">
                                                    <form:textarea path="content" class="form-control" placeholder="Entrez commentaire..." rows="4" cols="20"/>
                                                </div>
                                                </div>
                                                <div class="form-group">
                                                <div class="col-sm-offset-2 col-sm-6">
                                                    <button type="submit" class="btn btn-primary"><spring:message code="jsp.common.button.send"/></button>
                                                    <button type="reset" class="btn btn-default"><spring:message code="jsp.common.button.reset"/></button>
                                                </div>
                                                </div>
                                                <%-- <table>
                                                    <tr>
                                                        <td style="width: 99px;">
                                                            <img src="img/avartar.jpg" alt="" />
                                                        </td>
                                                        <td valign="top">
                                                        		Họ tên
                                                        		<form:input class="form-control" path="author"></form:input></td>
                                                            	<form:textarea path="content" class="form-control" placeholder="Nhập nội dung bình luận..." rows="4" cols="20"></form:textarea>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td></td>
                                                        <td align="right">
                                                            <button type="submit" class="btn btn-primary">Send</button>
                                                        </td>
                                                    </tr>
                                                </table> --%>
                                        </form:form>
                                        <div class="others-comments">
                                            <ul>
                                            	<c:forEach items="${comments}" var="cmt">
                                                <li>
                                                    <table>
                                                        <tr>
                                                            <td style="width: 99px;">
                                                                <img src='<c:url value="/resources/img/avartar.jpg"/>' alt="" />
                                                            </td>
                                                            <td valign="top">
                                                                <div class="user-display-name">
                                                                    <strong>${cmt.author}</strong>
                                                                    <div><span style="font-size:12px;"><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${cmt.createdDate}" /></span></div>
                                                                </div>
                                                                <br />
                                                                <p>${cmt.content}</p>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="tab-pane" id="tab-3">
                                    <p>Nous vous remercions de l'intérêt que vous portez à PearlDragonTravel. Pour assurer la qualité de nos circuits, nous nous sommes spécialisés dans l'organisation de circuits individuels types. C'est à dire que vous voyagerez accompagnés d'un guide et d'un chauffeur. C'est la raison pour laquelle la date du départ est selon votre décision. C'est avec plaisir que nous sommes à votre entière disposition pour composer votre circuit à votre convenance et respectant votre budget.</p>
                                    <br />
                                    <p>Les champs précédés d'un <span class="text-danger">(*)</span> sont obligatoires</p>

                                    <form:form id = "createRequest" commandName="newRequest" action="${pageContext.request.contextPath}/tour/${tourTypePath}/${pathId}/${tour.id}/${tour.seoPath}/createRequest" method="post" class="form-horizontal" role="form">
                                    	<form:hidden path="productId"/>
                                    	<div class="form-group">
							                <label for="name-tour-register" class="col-sm-2 control-label">Nom du Tour <span class="text-danger">(*)</span>:</label>
							                <div class="col-sm-6">
							                    <input name=productName value="${tour.name}" class="form-control" readonly="readonly" />
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
							                <label for="sex-tour-register" class="col-sm-2 control-label">Type d'hôtels</label>
							                <div class="col-sm-6">
							                	<%-- <form:select path="hotelLevel"> 
							                		<form:option items="${hotelLevels}" />
							                	</form:select> --%>
							                	<select name="hotelLevel" class="form-control">
							                		<option value="ANY">Tout	</option>
							                		<option value="TWO_STAR">2 Étoiles</option>
							                		<option value="THREE_STAR">3 Étoiles</option>
							                		<option value="FOUR_STAR">4 Étoiles</option>
							                		<option value="FIVE_STAR">5 Étoiles</option>
							                	</select>
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
                            </div> <!-- End tab content pane -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
    	 $(document).ready(function() {
    	      $('#addComment').submit(function(event) {
    	    	  var ctx = "${pageContext.request.contextPath}";
    	    	  var author = $('#author').val();
    	    	  var content = $('#content').val();
    	    	  var email = $('#email').val();
    	    	  var address = $('#address').val();
    	    	  //var price = $('#price').val();
    	    	  var json = { "author" : author, "content" : content, "email" : email, "address" : address};
    	    	  
    	        $.ajax({
    	        	url: $("#addComment").attr( "action"),
    	        	data: JSON.stringify(json),
    	        	type: "POST",
    	        	
    	        	beforeSend: function(xhr) {
    	        		xhr.setRequestHeader("Accept", "application/json");
    	        		xhr.setRequestHeader("Content-Type", "application/json");
    	        	},
    	        	success: function(comment) {
    	        		var cd = new Date(comment.createdDate);
    	        		console.log(cd);
    	        		var commentContent = "<li><table><tr><td style='width: 99px;'> <img src='" + ctx + "/resources/img/avartar.jpg' alt=''/></td>";
    	    	    	  commentContent += "<td valign='top'> <div class='user-display-name'><strong><a href='#'>";
    	    	    	  commentContent += comment.author;
    	    	    	  commentContent += "</a></strong></div>";
    	    	    	  commentContent += "<div><span style='font-size:12px;'>" + cd + "</span></div> <p>";
    	    	    	  commentContent += comment.content;
    	    	    	  commentContent += "</p></td></tr></table></li>";
    	    	    	  $('div.others-comments ul').prepend(commentContent);
    	    	    	  $(this).reset();
    	        	}
    	        });
    	         
    	        event.preventDefault();
    	      }); 
    	       
    	    });  
    </script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
	 <script>
	    /* $.validator.setDefaults({
	        submitHandler: function() {
	            alert("submitted!");
	        }
	    }); */
	
	    $().ready(function() {
	
	        $("#addComment").validate({
	            rules: {
	                author: "required",
	                content: "required",
	                },
	            messages: {
	                author: "Please enter your name",
	                content: "Please enter your comment",
	                }
	        });

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
	            	numberOfChildren2to9: {
	            		required:true,
	            		number:true
	            	},
	            	numberOfChildrenLessThan2: {
	            		required:true,
	            		number:true
	            	},
	            	//plannedPeriod: "required",
	            	//expectedTime: "required",
	            	subject: "required",
	            	detail: "required",
	                },
	            messages: {
		            	"customer.fullName": "Vous n'avez pas entrer un nom de contact",
		            	"customer.email": "Vous n'avez pas entrer une adresse e-mail!",
		            	"customer.nationality":"Vous n'avez pas entrer une Nationalité",
		            	"customer.phone": "Vous n'avez pas entré un téléphone!",
		            	subject: "Vous n'avez pas entré de titre!",
		                detail:"champ est requis"
	                }
	        });
	        
	    });
    </script>
	    
    <script src="${pageContext.request.contextPath}/resources/plugins/bxslider/jquery.bxslider.min.js"></script>
