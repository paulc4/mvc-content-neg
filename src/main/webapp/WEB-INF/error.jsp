<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>
	<fmt:message key="error.title" />
</h1>
<h2>
	<fmt:message key="error.caption" />
</h2>

<p>URL: ${url}</p>
<p>Exception: ${exception}</p>

<!--
  Exception: ${exception.message}.

<c:forEach items="${exception.stackTrace}" var="ste">       ${ste}
</c:forEach>
-->