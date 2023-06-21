<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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

</head>
<body>
<div class="container">
	<h1>사원정보</h1>
	<div class="row">
	<form method="post" action="output.jsp">
		<table class="table">
			<tr>
				<th width="25%">이금</th>
				<td width="75%"><input type="text" name="name" size="15" class="input-sm"></td>
			</tr>
			<tr>
				<th width="25%">성별</th>
				<td width="75%"><input type="text" name="sex" size="15" class="input-sm"></td>
			</tr>
			<tr>
				<th width="25%">부서</th>
				<td width="75%"><input type="text" name="dept" size="15" class="input-sm"></td>
			</tr>
			<tr>
				<th width="25%">직위</th>
				<td width="75%"><input type="text" name="job" size="15" class="input-sm"></td>
			</tr>
			<tr>
				<th width="25%">연봉</th>
				<td width="75%"><input type="number" min="100" max="100000" step="100" name="pay"></td>
			</tr>
			<tr>
				<td colspan="2" class="text-center">
					<button class="btn btn-sm btn-danger" >보내기</button>
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>