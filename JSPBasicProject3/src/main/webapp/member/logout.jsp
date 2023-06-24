<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 세션을 해제 -> 저장된 모든 정보를 지운다. --> 로그아웃 
	session.invalidate();
	// 한개씩 지우는 메소드 -> removeAttribute("key"); -> 장바구니
	response.sendRedirect("../databoard/list.jsp");
%>