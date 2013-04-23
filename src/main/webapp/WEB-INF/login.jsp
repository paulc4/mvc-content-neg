<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div style="padding-left: 180px">
	<h1>
		<fmt:message key="login.title" />
	</h1>

	<c:if test="${!empty param.login_error}">
		<div style="color: red; margin-bottom: 1em; font-size: large;">
			<fmt:message key="login.failure" />
		</div>
	</c:if>

	<table style="background-color: white">
		<tr>
			<td>
				<h2>
					<fmt:message key="login.caption" />
				</h2>

				<form action="<c:url value='/j_spring_security_check'/>"
					method="post">
					<table>
						<tr>
							<td><fmt:message key="login.username" />:</td>
							<td><input type="text" name="j_username" /></td>
						</tr>
						<tr>
							<td><fmt:message key="login.password" />:</td>
							<td><input type="password" name="j_password" /></td>
						</tr>
						<tr>
							<td colspan='2'><input name="submit" type="submit"
								value="Login" /></td>
						</tr>
					</table>
				</form>
			</td>
			<td>
				<div style="text-align: left;">
					<p>
						<b><fmt:message key="login.prompt" /></b>
					</p>
					<ul>
						<c:forEach var="user" items="${users}">
							<li>${user} / ${user}</li>
						</c:forEach>
					</ul>
				</div>
			</td>
		</tr>
	</table>
</div>
