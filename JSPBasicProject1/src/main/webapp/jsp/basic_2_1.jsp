<%-- 
	JSP => HTML + JAVA
	       ----------- 구분해서 사용 (스크립트)
	=> 스크립트 : 자바를 코딩하는 영역(위치)
	   선언문 : <%! %> => 메소드 선언, 멤버변수 선언
	   표현식 : <%= %> => out.println() => 자바 변수출력
	   스크립트릿 :  <% %> => 일반 자바 (메소드 영역)
	                       메소드 호출, 지역변수 설정, 제어문 ... 
	         
	   a.jsp              
	   <%!
	      int a=10;
	      public void display(){}
	   %>
	   <%
	      String name="홍길동"
	   %>
	   
	   <div>
	     <%=name%>
	   </div>
	   => 자바 변경
	   public class a_jsp extends HttpServlet{
	   		---------------------------------- ==> <%! %>
	   		int a =10;
	   		public void display(){}
	   		----------------------------------
	   		public void _jspInit(){
	   			
	   		}
	   		public void _jspDestroy(){}
	   		public void _Service(){   ==> <% %>
	   			String name = "홍길동"
	   			out.write("<div>");
	   			out.println(name); // <%=name%>
	   			out.write("</div>");
	   		}
	   }
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<h1>구구단</h1>
	<div class="row">
		<table class="table">
			<tr>
			<c:forEach var="i" begin="2" end="9">
			<th class="text-center">${i}단</th>
			</c:forEach>
			</tr>
			<c:forEach var="i" begin="1" end="9">
				<tr>
					<c:forEach var="j" begin="2" end="9">
						<td class="text-center">${j }*${i } = ${j*i }</td> 
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
</body>
</html>