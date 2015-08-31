<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
 <%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>  
 <html>  
 <head>  
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-custom.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" />
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="Scripts/html5shiv.min.js"></script>
      <script src="Scripts/respond.min.js"></script>
    <![endif]--> 
 </head>  
 <body>  
     <div class="main_wrapper">
           <div class="header"><tiles:insertAttribute name="header"/> </div>  
           <div class="content"><tiles:insertAttribute name="content"/> </div>  
           <div class="footer"><tiles:insertAttribute name="footer"/> </div>  
      </div> 
      
    
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/tour.js"></script>
 </body>  
 </html> 