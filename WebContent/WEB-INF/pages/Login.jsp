<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登陆</title>
</head>
<body>
<h3>${message}</h3>
<form method="POST" action="./Login">
	<input type="text" name="username" />
	<input type="password" name="password" />
	<button type="submit">登陆</button>
</form>
</body>
</html>