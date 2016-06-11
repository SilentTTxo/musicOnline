<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>测试用播放器</title>
	<!-- 新 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

	<!-- 可选的Bootstrap主题文件（一般不用引入） -->
	<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="./js/TT.js"></script>
	<link rel="stylesheet" href="./css/loaders.css"></head>
<body style="background-color: #003333;">
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="progress" style="height:50px;margin-bottom:0px" >
			<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;" id="plen">
				<p style="font-size:xx-large;margin-top:15px" id="psent">0%</p>
			</div>
		</div>
	</nav>
	<div class="container">
		<div class="col-lg-12 well" style="margin-top: 200px;">
			<audio src="#" controls="controls" style="width: 100%" id="player" ontimeupdate="timeupdate()">辣鸡浏览器</audio>
			<div class="input-group">
				<input type="text" class="form-control" placeholder="Search for..." id = "name">
				<span class="input-group-btn">
					<button class="btn btn-default" type="button" id="search">查找!</button>
				</span>
			</div>
			<!-- /input-group -->
			<hr/ style="border-top:1px solid #8a6d3b">
			<div class="list-group" id="list"></div>
			<div class="loader" id="load">
				<div class="spinner">
					<div class="double-bounce1"></div>
					<div class="double-bounce2"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>