<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="header" class="clearfix">
	<div id="nav">
		<c:choose>
			<c:when test="${!empty sessionScope['SPRING_SECURITY_CONTEXT']}">
				<p style="text-align: right; vertical-align: top; font-size: large;">
					<sec:authentication property="principal.username" />
					<a href="<c:url value="/j_spring_security_logout"/>">
						<img alt="logout" title="logout"
							 src="<c:url value="/images/icon-exit.png"/>" />
					</a>
				</p>
			</c:when>
			<c:otherwise>
				<div style="padding-top: 30px"></div>
			</c:otherwise>
		</c:choose>
	</div>
</div>