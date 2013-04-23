<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Ensue some space before the footer -->
<div style="padding-top:10pt"></div>

<hr/>

<div id="footer" class="clearfix">
	<span id="left">
		<img src="<c:url value="/images/pivotal-logo.png"/>" />
	</span>

	<span id="right">
		<c:if test="${!empty sessionScope['SPRING_SECURITY_CONTEXT']}">
			<a href="<c:url value="/j_spring_security_logout"/>">
				<fmt:message key="navigate.logout" />
			</a>
		</c:if>
	</span>

	<span id="center">
		<fmt:message key="footer.message" />
	</span>
</div>
