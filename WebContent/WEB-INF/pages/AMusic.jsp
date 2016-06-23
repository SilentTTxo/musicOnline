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
	<th>歌曲名</th>
	<th>歌手</th>
	<th>专辑</th>
</tr>
<c:forEach items="${list}" var="music">
<tr>
	<td>${music.id}</td>
	<td>${music.title}</td>
	<td>${music.artist}</td>
	<td>${music.album}</td>
</tr>
</c:forEach>
</table>

</body>
</html>