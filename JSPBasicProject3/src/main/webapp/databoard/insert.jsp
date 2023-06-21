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
	font-family: 'Gasoek One', sans-serif;
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
<script type="text/javascript">
function board_write(){
	// 유효성 검사
	//form 읽기
	let frm = document.frm
	if(frm.name.value===""){
		frm.name.focus()
		return
	}
	if(frm.subject.value===""){
		frm.subject.focus()
		return
	}
	if(frm.content.value===""){
		frm.content.focus()
		return
	}
	if(frm.pwd.value===""){
		frm.pwd.focus()
		return
	}
	frm.submit;
}
</script>
</head>
<body>
<div class="container">
	<h1>글쓰기</h1>
	<div class="row">
		<form method="post" action="insert_ok.jsp" name="frm"
		enctype="multipart/form-data">
		<%--
			multipart/form-data : fileupload 프로토콜
			=> POST 
		 --%>
		<table class="table">
			<tr>
				<th class="text-center danger" width="15%">이름</th>
				<td width="85%">
					<input type="text" name="name" class="input-sm">
				</td>
			</tr>
			<tr>
				<th class="text-center danger" width="15%">제목</th>
				<td width="85%">
					<input type="text" name="subject" class="input-sm">
				</td>
			</tr>
			<tr>
				<th class="text-center danger" width="15%">내용</th>
				<td width="85%">
					<input type="text" name="content" class="input-sm">
				</td>
			</tr>
			<tr>
				<th class="text-center danger" width="15%">비번</th>
				<td width="85%">
					<input type="password" name="pwd" class="input-sm">
				</td>
			</tr>
			<tr>
				<th class="text-center danger" width="15%">첨부</th>
				<td width="85%">
					<input type="file" name="upload" class="input-sm">
				</td>
			</tr>
			<tr><td colspan="2">
				<button class="btn btn-sm btn-danger" onclick="board_write()" >글쓰기</button>
				<input  type="button" value="취소" >
				</td>
			</tr>	
		</table>
		</form>
		
		
	</div>

</div>
</body>
</html>