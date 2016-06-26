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
<a href="./AMusic">专辑管理</a>
<hr/>
<form action="./AMusicAlbum">

<label>搜歌手</label><input type="text" name="artist"/>
<button type="submit">Serch</button>
</form>
<hr/>
<table>
<tr>
	<th>专辑</th>
	<th>操作</th>
</tr>
<c:forEach items="${list}" var="music">
<tr>
	<td>${music.album}</td>
	<td><a href="./AMusicAlbum?delAlbum=${music.album}&artist=${artist}">删除</a></td>
</tr>
</c:forEach>
</table>

</body>
</html>