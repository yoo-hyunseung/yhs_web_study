<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- import -->
<script type="text/javascript" src="http://code.jquery.com/jquery.js">
</script>
<script type="text/javascript">
$(function(){
	alert($('h1').text()) // getter;
	alert($('h1').html())
	$('#h').html("<span style=color:green>hello Jquery</span>")
})
</script>

</head>
<body>
	<img alt="" src="" width="250" height="300">
	<h1>hello JQuery</h1>
	<h1><span style="color: red"></span>helloJquery</h1>
	<h1 id="h"></h1>
</body>
</html>