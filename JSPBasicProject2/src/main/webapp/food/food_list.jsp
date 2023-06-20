<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
    <%
    // JSP -> 데이터 수집(자바)
    /*
    1. 사용자가 받은 데이터 받기
    2. 받은 데이터를 이용해서 데이터베이스 연동
    3. 오라클에서 데이터 읽기 ->HTML화면에 이동
   
    순서          모델     뷰    => MV
    요청 (JSP) -> 자바 -> JSP
                  |
                 오라클
    */
    FoodDAO dao = FoodDAO.newInstance();
    List <FoodVO> list = dao.foodListData();
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
.container{
	margin-top: 50px; 
}
.row{
	margin: 0px auto;
	width: 800px;
	height: 700px;
}
h1{
	text-align: center;
}
</style>
</head>
<body>
<div class="container">
	<div class="row">
		<%
			for(FoodVO vo : list){
		%>
			<a href="food_detail.jsp?fno=<%=vo.getFno()%>">
			<img src="<%=vo.getPoster() %>" class="img-thumbnail" alt="...">
			
			</a>
		<%		
			}
		%>
	</div>
</div>
</body>
</html>