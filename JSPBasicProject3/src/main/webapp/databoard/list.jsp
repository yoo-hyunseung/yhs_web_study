<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, java.util.*,com.sist.vo.*"%>
    <%@ page import="java.text.*" %>
<%
	// 1. 사용자가 보내준 요청값을 받는다 -> page
	String strPage =request.getParameter("page");
	/*
		list.jsp?           : null
		list.jsp?page=      : 공백
		list.jsp?page=2     : 2
	*/ 
	if(strPage==null){
		strPage="1";
	}
	int curpage = Integer.parseInt(strPage);
	// 2, DAO 에서 요청한 페이지의 값을 읽ㅇ어온다.
	DataBoardDAO dao = DataBoardDAO.newInstance();
	List<DataBoardVO> list = dao.databoardListData(curpage);
	int count = dao.databoardRowCount();
	//85
	int totalpage=(int)(Math.ceil(count/10.0));
	// 블록별 처리 []1[]2[]3
	final int BLOCK = 5;
	int startPage = ((curpage-1)/BLOCK*BLOCK)+1;
	int endPage = ((curpage-1)/BLOCK*BLOCK)+BLOCK;
	if(endPage>totalpage){
		endPage=totalpage;
	}
	// 3. 오라클에서 가지고 온 데이터를 화면에 출력
	// ==> 자바로 이동(Model)
	count =(count-((curpage*10)-10));
	/*
		count = 85;
		1page => 85
		2page =>75
	*/
	String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
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

</head>
<body>
	<div class="container">
		<h1>자료시</h1>
		<div class="row">
			<table class="table">
				<tr>
					<td>
						<a href="insert.jsp" class="btn btn-sm btn-warning">새글</a>
					</td>
				</tr>
			</table>
			<table class="table table-hover">
				<tr class="danger">
					<th class="text-center" width="10%">번호</th>
					<th class="text-center" width="45%">제목</th>
					<th class="text-center" width="15%">이름</th>
					<th class="text-center" width="20%">작성일</th>
					<th class="text-center" width="10%">조회수</th>
				</tr>
				<%
					for(DataBoardVO vo : list){
				%>		
						<tr>
							<td class="text-center" width="10%"><%=count-- %></td>
							<td width="45%">
							<a href="detail.jsp?no=<%=vo.getNo()%>"><%=vo.getSubject() %></a>
							&nbsp;
							<%
								if(today.equals(vo.getDbday())){
							%>
									<sup style="color:red">new</sup>
							<%
								}
							%>
							</td>
							<td class="text-center" width="15%"><%=vo.getName() %></td>
							<td class="text-center" width="20%"><%=vo.getDbday() %></td>
							<td class="text-center" width="10%"><%=vo.getHit() %></td>
						</tr>
				<%		
					}
				%>
			</table>
		</div>
		<div class="row">
			<div class="text-center">
				<ul class="pagination">
					<% 
						if(startPage>1){
					%>
					  <li><a href="list.jsp?page=<%= startPage-1%>">&lt;</a></li>
					  <%
						}
					  %>
					  <%
					  	for(int i =startPage; i <=endPage;i++){
					  %>		
					  		<li <%=curpage==i ? "class=active":""%>><a href="list.jsp?page=<%=i %>"><%=i %></a></li>
					  <%		
					  	}
					  %>
					  <%
					  	if(endPage <totalpage){
					  %>
					  <li><a href="list.jsp?page=<%= endPage+1%>">&gt;</a></li>
					  <%
					  	}
					  %>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>