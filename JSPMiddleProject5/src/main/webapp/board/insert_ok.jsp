<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<jsp:useBean id="dao" class="com.sist.dao.ReplyBoardDAO"/>

<%
	// 사용자가 보낸 데이터 받기
	request.setCharacterEncoding("UTF-8");
%>
<%-- 사용자가 보내준 데이터를 vo객체에 첨부 전체 값을 받음 --%>
<jsp:useBean id="vo" class="com.sist.dao.ReplyBoardVO">
	<jsp:setProperty name="vo" property="*"/>
</jsp:useBean>
<%
//데이터 베이스 연결 DAO 오라클에 데이터 첨부
dao.boardInsert(vo);
// 화면 이동 -> list.jsp
	
%>

<c:redirect url="list.jsp"/>
<%-- => response.sendRedirect --%>