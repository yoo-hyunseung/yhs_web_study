<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<title>Insert title here</title>
<style type="text/css">
.row{
	margin: 0px auto;
	width: 600px;
}
</style>
</head>
<body>
<div class="wrapper row3">
  <main class="container clear"> 
  
  	<h2 class="sectiontitle">글수정쓰기</h2>
  	<div class="row">
  	<form action="../board/update_ok.do" method="post">
  	<table class="table">
  		<tr>
  			<th width="15%">이름</th>
  			<td width="85%">
  				<input type="text" name="name" size="20" class="input-sm" value="${vo.name }">
				<input type="hidden" value="${vo.no }" name="no">
  			</td>
  		</tr>
  		<tr>
  			<th width="15%">제목</th>
  			<td width="85%">
  				<input type="text" name="subject" size="50" class="input-sm" value="${vo.subject }">
  			</td>
  		</tr>
  		<tr>
  			<th width="15%">내용</th>
  			<td width="85%">
  				<textarea rows="10" cols="50" name="content">${vo.content }</textarea>
  			</td>
  		</tr>
  		<tr>
  			<th width="15%">비밀번호</th>
  			<td width="85%">
  				<input type="password" name="pwd" size="10" class="input-sm">
  			</td>
  		</tr>
  		<tr>
  			<td colspan="2" class="text-center">
  				<input type="submit" value="글수정" class="btn btn-sm btn-success">
  				<input type="button" value="취소" class="btn btn-sm btn-info" onclick="javascript:history.back()">
  			</td>
  		</tr>
  	</table>
  	</form>
  	</div>
</main>
</div>
</body>
</html>