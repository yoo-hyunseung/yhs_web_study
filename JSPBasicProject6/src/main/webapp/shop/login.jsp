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
				<td width="80%"><input type="text" name="id" size="15" class="input-sm" id="id" required="required"></td>
			</tr>
			<tr>
				<td width="20%">PWD</td>
				<td width="80%"><input type="password" name="pwd" size="15" class="input-sm" id="pwd" required="required"></td>
			</tr>
			<tr>
				<td colspan="2" class="text-center">
					<span id="print" style="color: red;"></span>
				</td>
			</tr>
			<tr><td colspan="2" class="text-center">
				<input  type="submit" value="login" class="btn btn-sm btn-danger" id="logBtn" >
				<a href="goods.jsp" class="btn btn-sm btn-success">상품목록</a>
				</td>
			</tr>	
			
		</table>
		</form>
	</div>
</div>
</body>
</html>