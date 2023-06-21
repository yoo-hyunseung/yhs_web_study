<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.temp.*"%>
    
    <%
    
    	request.setCharacterEncoding("utf-8");
    	
    
    %>
    <jsp:useBean id="bean" class="com.sist.temp.SawonBean">
    <jsp:setProperty name="bean" property="*"/>
    </jsp:useBean>
    <%--
    	public void aaa(SawonBean bean){
    	218page 빈을 이용한 JSP파일 작성
    	-----------------------------------------
    	   jsp 액션 태그
    	   => 화면 이동 , 화면 연결, 값 전송 , 객체 생성 , 멤버변수에 값 주입, 멤버변수 값을 읽기
      	         |        |        |               <jsp:setProperty>  <jsp:getProperty>
      	                                  <jsp:useBean>
      	         
      	                      *** <jsp:param>
      	            *** <jsp:include>
    	      <jsp:forward>
    	      
    	-----------------------------------------
    	 <jsp:useBean> : 객체 메모리 할당 => 객체 생성
    	 속성
    	  id : 객체명
    	  class : 클래스명
    	  scope : 사용범위
    	   = page (default) => 한개 JSP에서 사용(다른페이지이동 => 메모리 해제)
    	     *** 생략이 가능
    	   = request => 사용자 요청이 있는 경우에 사용
    	   = session => 프로그램이 유지하고 있는 동안(접속 ~ 종료)
    	   = application => 프로그램 종료시 까지
    	   <jsp:useBean id="a" class="A">
    	                       -------- 패키지명.클래스명 => Class.forName()
    	      => 자바 변경
    	        A a = new A();
    	-----------------------------------------
    	 <jsp:setProperty> : 변수에 값을 설정(쓰기)
    	   setter
    	   name : 객체명
    	   property : 변수 
    	   value : 값을 설정
    	   ---
    	   class A{
    	   	private int no;
    	   	private String name;
    	   	
    	   	setNO() , getNo, setName(),...
    	   }
    	   // 1. 메모리할당 
    	    <jsp:useBean id="a" class="A"
    	    = 초기화
    	     <jsp:setProperty name="a" property="name" value="홍길동">
    	     A a = new A()
    	     
    	     a.setName("홍길")
    	     
    	     <jsp:setProperty name="a" property="no" value="10">
    	     a.setNo(10)
    	     
    	     <jsp:setProperty name="a" property="*"/>
    	     a.setName(request.getParameter("name"))
    	     a.setNo(Integer.parseInt(request.getParameter("no"))_
    	-----------------------------------------
    	 <jsp:getProperty> : 변수 값 읽기
    	   getter
    	   <jsp:getProperty name="a" propery="name"> => a.getName()
    	-----------------------------------------
    	}
    	
     --%>
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
name : <%=bean.getName() %><br>
name : <%=bean.getSex() %><br>
name : <%=bean.getDept() %><br>
name : <%=bean.getJob() %><br>
name : <%=bean.getPay() %><br>

</body>
</html>