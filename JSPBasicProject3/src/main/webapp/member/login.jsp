<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins&family=Varela+Round&display=swap" rel="stylesheet">

<style type="text/css">
*{
	font-family: 'Poppins', sans-serif;
	font-family: 'Varela Round', sans-serif;
}
.container{
	margin-top: 50px;
}
.row{
	margin: 0px auto;
	width: 900px;
}
h1{
	text-align: center;
}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
// 자바 스크립트 라이브러리
/*
 	let id = document.querySelector("#id")
 	         ---------------------- > $
 	         => 값을 읽은 경우
 	             id.value -> $('#id').val()
 	             --------
 	             id.textContent => $('#id').text()
 	             --------------
 	             id.innerHTML => $('#id').html()
 	             ---------------
 	             속성  -> $('id').attr()
 	JQUERY => $('#id')
 
 */ 

$(function(){ // window.onload = function(){}
$('#logBtn').on('click', function(){
		let id = $('#id').val()
		if(id.trim()===""){
			$('#id').focus()
			return;
		}
		let pwd = $('#pwd').val()
		/* $('frm').submit(); */
		
		// 화면을 이동하지 않고 데이터 받기;;
		$.ajax({
			type : "post",
			url : "login_ok.jsp",
			data : { "id":id , "pwd" : pwd },
			success:function(result){
				let res = result.trim()
				console.log(res)
				
				if(res=="NOID"){
					$('#id').val('')
					$('#pwd').val('')
					$('#id').focus()
					$('#print').text("no id")
				} else if(res=='NOPWD'){
					$('#pwd').val('')
					$('#pwd').focus()
					$('#print').text("wrong pwd")
				} else{
					location.href='../databoard/list.jsp'
				}
				/* location.href='../databoard/list.jsp' */
			}
		})
	})
}) 

</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
	<h1>Login</h1>
	<div class="row">
		<form method="post" action="login_ok.jsp" id="frm">
		<table class="table">
			<tr>
				<td width="20%">ID</td>
				<td width="80%"><input type="text" name="id" size="15" class="input-sm" id="id"></td>
			</tr>
			<tr>
				<td width="20%">PWD</td>
				<td width="80%"><input type="password" name="pwd" size="15" class="input-sm" id="pwd"></td>
			</tr>
			<tr>
				<td colspan="2" class="text-center">
					<span id="print" style="color: red;"></span>
				</td>
			</tr>
			<tr><td colspan="2" class="text-center">
				<input  type="button" value="login" class="btn btn-sm btn-danger" id="logBtn" >
				</td>
			</tr>	
		</table>
		</form>
	</div>
</div>
</body>
</html>