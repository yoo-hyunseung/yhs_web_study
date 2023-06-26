<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js">
</script>
<script type="text/javascript">
$(function(){
	 /*
	 	문서 객체 (태그) 조작
	 	----------------
	 	text()  : getter()/setter()
	 	           값 읽기  값 설정
	 	           -----  ------
	 	            |        |
	 	                    $(태그).text(값)
	 	            $(태그).text()
	 	            <태그>값</태그>
	 	            => text를 이용해서 값을 첨부 -> html은 그대로 출력
	 	val()   :
	 	attr()  : 태그의 속성에 값을 첨부 -> 읽기() => $(태그).attr("속성명")
	 	                             쓰기() => $(태그).attr("속성명","첨부할 값")
	 	html()
	 	append() 
	 */
	 /* $('h1').text('<span>Hello</span>') */
	 $('h1').html('<span>Hello</span>')
	 $('img').attr("src",'cgv1.jpg')
	 $('input[type=text]').val('value','admin');
	$('img').css('width','250px')
	$('img').css('height','300px')
	$('img').css('width':'250px','height':'300px','border':'2px soild red')
})
</script>
</head>
<body>
	<h1></h1>
	<img>
	<input type="text" id="id" size="20">
</body>
</html>