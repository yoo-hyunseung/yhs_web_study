<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<title>Insert title here</title>
<style type="text/css">
.row{
	margin: 0px auto;
	width: 800px;
}
</style>
</head>
<body>
<div class="wrapper row3">
  <main class="container clear"> 
  <div class="jumbotron">
  	<h3 class="text-center">${cvo.title }</h3>
  	<h4 class="text-center">${cvo.subject }</h4>
  </div>
  <div class="row">
  	<table class="table">
  		<tr>
  			<td>
  			<%-- request.getAttribute -> --%>
  				<c:forEach var="vo" items="${list }">
  					<table class="table">
  						<tr>
  							<td width="35%" class="text-center" rowspan="3">
  							<a href="../food/food_detail_before.do?fno=${vo.fno }">
  								<img alt="" src="${vo.poster }" style="width: 100%" class="img-rounded">
  							</a>
  							</td>
  							<td width="65%">
  								<a href="../food/food_detail_before.do?fno=${vo.fno }"><h3>${vo.name }&nbsp;<span style="color: orange;">${vo.score }</a></span></h3>
  							</td>
  						</tr>
  						<tr>
  							<td width="65%">
  							${vo.address }
  							</td>
  						</tr>
  						<tr>
  							<td width="65%" height="150"></td>
  						</tr>
  						
  					</table>
  				</c:forEach>
  			</td>
  		</tr>
  		<tr>
  			<td class="text-right">
  				<a href="../main/main.do" class="btn btn-sm btn-danger">뒤로가기</a>
  			</td>
  		</tr>
  	</table>
  </div>
  </main>
</div>  
</body>
</html>