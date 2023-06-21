<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, com.sist.vo.*"%>
    <%--
    
    	메모리 할
     --%>
     <jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"/>
     <%
     // detail.jsp?no=
     	String no = request.getParameter("no");
     DataBoardVO vo = dao.databoardDetailData(Integer.parseInt(no));
     %>
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
<script type="text/javascript">
let i=0
function rm(){
	if(i==0){
		document.querySelector("#del").style.display=''
		document.querySelector("#delBtn").textContent='취소'
		i = 1
	}
	else{
		document.querySelector("#del").style.display='none'
		document.querySelector("#delBtn").textContent='삭제'
		i = 0
	}
}
</script>
</head>
<body>
<div class="container">
	<h1>내용보기</h1>
	<div class="row">
		<table class="table">
			<tr>
				<th width="20%" class="text-center danger">번호</th>
				<td width=30% class="text-center"><%=vo.getNo() %></td>
				<th width="20%" class="text-center danger">작성일</th>
				<td width=30% class="text-center"><%=vo.getDbday() %></td>
			</tr>
			<tr>
				<th width="20%" class="text-center danger">이름</th>
				<td width=30% class="text-center"><%=vo.getName() %></td>
				<th width="20%" class="text-center danger">조회수</th>
				<td width=30% class="text-center"><%=vo.getHit() %></td>
			</tr>
			<tr>
				<th width="20%" class="text-center danger">제목</th>
				<td colspan="3"><%=vo.getSubject() %></td>
			</tr>
			<% 
				if(vo.getFilesize()!=0){
			%>
			<tr>
				<th width="20%" class="text-center danger">첨부파일</th>
				<td colspan="3">
				<a href="download.jsp?fn=<%=vo.getFilename() %>"><%=vo.getFilename() %></a>(<%=vo.getFilesize() %>Bytes)
				</td>
			</tr>
			<%
				}
			%>
			<tr>
				<td colspan="4" class="text-left" valign="top" height="200">
					<pre style="white-space:pre-wrap; background-color: white;border: none;"><%=vo.getContent() %></pre>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="text-right">
					<a href="#" class="btn btn-xs btn-info">수정</a>
					<span class="btn btn-xs btn-success" id="delBtn" onclick="rm()">삭제</span>
					<a href="list.jsp" class="btn btn-xs btn-warning">목록</a>
					
				</td>
			</tr>
			<tr style="display:none" id="del">
				<td colspan="4" class="text-right">
				비밀번호:<input type="password" name="pwd" size="15" class="input-sm">
				<input type="button" value="삭제"class="btn btn-sm btn-danger">
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>