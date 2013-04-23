<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message>
			<tiles:insertAttribute name="title" />
		</fmt:message>
	</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/springsource.css"/>" ></link>
</head>
<body>
	<div id="page">
		<tiles:insertAttribute name="header" />
		<div id="content" class="clearfix">
			<div id="main">
				<tiles:insertAttribute name="main" />
			</div>

			<tiles:importAttribute name="navigationTab" />
			<c:if test="${navigationTab ne 'login'}">
				<div id="nav">
					<ul class="clearfix">
						<c:choose>
							<c:when test="${navigationTab ne 'home'}">
								<li><strong> <a href="<c:url value="/"/>"> <fmt:message
												key="navigate.home" />
									</a>
								</strong></li>
							</c:when>
							<c:otherwise>
								<!-- Not needed -->
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${navigationTab eq 'error'}">
								<!--  No account link -->
							</c:when>
							<c:when test="${navigationTab ne 'accounts'}">
								<li><a href="<c:url value="/accounts"/>"> <fmt:message
											key="navigate.accounts" />
								</a></li>
							</c:when>
						</c:choose>
						<c:if test="${!empty sessionScope['SPRING_SECURITY_CONTEXT']}">
							<li><a href="<c:url value="/j_spring_security_logout"/>">
									<fmt:message key="navigate.logout" />
							</a></li>
						</c:if>
					</ul>
				</div>
			</c:if>
		</div>
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>