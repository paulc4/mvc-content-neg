<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<h1>
	<fmt:message key="accounts.show.title" />
</h1>

<div>
	<spring:url var="showUrl" value="/accounts/{number}.json">
		<spring:param name="number" value="${account.number}" />
	</spring:url>
	<a href="${showUrl}"><fmt:message key="accounts.show.command.asJson"/></a>
</div>

<div id="entityDetails">
	<div id="accountDetails">
		<ul>
			<li>
				<fmt:message key="label.Account.number"/>: ${account.number} (${account.type})
			</li>
			<li>
				<fmt:message key="label.Account.name"/>: ${account.owner.name}
			</li>
			<li>
				<fmt:message key="label.Account.dateOfBirth"/>: <fmt:formatDate value="${account.owner.dateOfBirth}"/>
			</li>
			<li>
				<fmt:message key="label.Account.email"/>: ${account.owner.email}
			</li>
			<li>
				<fmt:message key="label.Account.receiveNewsletter"/>: 
				<fmt:message key="label.${account.owner.receiveNewsletter}"/>
			</li>
			<li>
				<fmt:message key="label.Account.receiveMonthlyEmailUpdate"/>: 
				<fmt:message key="label.${account.owner.receiveMonthlyEmailUpdate}"/>
			</li>
			<li>
				<fmt:message key="label.Account.balance"/>: ${account.balance}
			</li>
			<li>
				<fmt:message key="label.Account.creditCardNumber"/>:
				<c:choose>
					<c:when test="${!empty account.creditCardNumber}">
						${account.creditCardNumber}
					</c:when>
					<c:otherwise>N/A</c:otherwise>
				</c:choose>
			</li>
		</ul>
	</div>

	<div id="transactions">
		<h2>
			<fmt:message key="label.Account.transactions"/>
		</h2>
		<c:if test="${!empty account.transactions}">
			<div style="margin-left:3em">  <!-- Indent -->
				<table>
					<thead>
						<tr>
							<th>
								<fmt:message key="label.Account.transactions.name"/>
							</th>
							<th>
								<fmt:message key="label.Account.transactions.number"/>
							</th>
							<th>
								<fmt:message key="label.Account.transactions.code"/>
							</th>
							<th>
								<fmt:message key="label.Account.transactions.amount"/>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tr" items="${account.transactions}">
							<tr>
								<td style="text-align: left">${tr.accountName}</td>
								<td style="text-align: center">${tr.accountNumber}</td>
								<td style="text-align: center">${tr.bankCode}</td>
								<td style="text-align: right">${tr.amount}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		<c:if test="${empty account.transactions}">
			<fmt:message key="accounts.show.label.noTransactions"/>
		</c:if>
	</div>
</div>