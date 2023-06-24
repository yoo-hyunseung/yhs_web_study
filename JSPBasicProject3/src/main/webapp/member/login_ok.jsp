<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	MemberDAO dao = MemberDAO.newInstance();
	
	MemberVO vo = dao.isLogin(id, pwd);
	// NOID NOPWD OK
	if(vo.getMsg().equals("OK")){
		// 세션에 저장 map 방식 -> key가 중복이 되면 안된다.
		session.setAttribute("id", vo.getId());
		session.setAttribute("name", vo.getName());
		session.setAttribute("sex", vo.getSex());
		session.setMaxInactiveInterval(3600);//  1 시간 유지
	}
	
			
%>
<%=vo.getMsg() %>
