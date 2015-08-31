<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="home-contact tour-box">
    <img src="${pageContext.request.contextPath}/resources/img/contact.jpg" alt="" class="img-responsive" />

    <table style="width: 100%;">
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
        <spring:message code="jsp.common.label.hotline"/><spring:message code="jsp.pref.hotline"/> <br />
        <spring:message code="jsp.common.label.email"/> 
        	<a href="mailto:<spring:message code="jsp.pref.email"/>">
        		<spring:message code="jsp.pref.email"/>
        	</a>
    </address>
</div> <!-- End contact segment -->