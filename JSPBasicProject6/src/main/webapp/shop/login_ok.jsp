<%@page import="com.sist.dao.GoodsDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	
	// DAO 연동
	GoodsDAO dao = GoodsDAO.newInstance();
	String result = dao.isLogin(id, pwd);
	
	if(result.equals("NOID")){
%>	
		<script>
		alert("아이디가 존재하지 않습니다.!!!!!!!!!!!!!");
		history.back();
		</script>
<%
	} else if(result.equals("NOPWD")){
%>	
		<script>
		alert("비밀번호가 틀립니다....!!!!!!!!!!!!!");
		history.back();
		</script>
<%
		
	} else{
		session.setAttribute("id", id);
		session.setAttribute("name", result);
		response.sendRedirect("goods.jsp");
	}
	
%>