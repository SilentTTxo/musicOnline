<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登陆</title>
</head>
<body>
<h3>${message}</h3>
<form method="POST" action="./AMusicFix" }>
	<input type="text" name="id" value="${id}" hidden="true"/>
	<input type="text" name="title" />
	<input type="text" name="aritist" />
	<input type="text" name="album" />
	<button type="submit">修改</button>
</form>
</body>
</html>