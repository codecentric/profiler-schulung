<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Java Profiling Memory</title>
</head>
<body>

<h2>Prime Manager</h2>

<form:form method="POST">
	<table>
	 <tr>
	  <td>Maximum:</td><td><input type="text" name="maximum"/></td>
	 </tr>
	</table>
	<input type="submit" name="calculate" value="Calculate Primes" />
</form:form>


<h3>Current list of prime counts</h3>

<form:form method="POST">
	<input type="submit" name="clear" value="Clear calculated primes" />
</form:form>

<table>
	<tr>
		<td width="200">Maximum</td>
		<td width="300">Prime Count</td>
	</tr>
	<c:forEach items="${primecounts}" var="entry">
		<tr>
			<td><c:out value="${entry.key}" /></td>
			<td><c:out value="${entry.value}" /></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>