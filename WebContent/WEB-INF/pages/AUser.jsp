<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Index</title>
</head>
<body>
<h3>${message}</h3>
<a href="./Aindex">返回上层</a>
<table>
<tr>
	<th>ID</th>
	<th>Username</th>
	<th>Password</th>
</tr>
<c:forEach items="${list}" var="user">
<tr>
	<td>${user.id}</td>
	<td>${user.username}</td>
	<td>${user.password}</td>
	<td><a href="./AUser?userid=${user.id}">删除</a>
</tr>
</c:forEach>
</table>

</body>
</html>