<%@page import="com.sist.vo.DataBoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,java.io.*"%>
    
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"></jsp:useBean>

<%
	String no = request.getParameter("no");
	String pwd = request.getParameter("pwd");
	// 미리 파일 크기, 이름을 받아놔야 한다.
	DataBoardVO vo = dao.databoardFileInfo(Integer.parseInt(no));
	//dao연결 -> 삭제됨 파일명, 파일크기를 알 수 없다.
	boolean bCheck = dao.databoardDelete(Integer.parseInt(no), pwd);
	if(bCheck){
		
		if(vo.getFilesize()!=0){ // 파일 존재여부 -> 있으면 
			File file = new File("/Users/yuhyeonseung/Downloads/"+vo.getFilename());
			file.delete();// delete
		}
		response.sendRedirect("list.jsp");
		
		
	}
	else{
%>
	<script>
		alert("비밀번호가 다릅니다")
		history.back()
	</script>
<%
	}
%>
