<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	//response.sendRedirect("response_2.jsp"); // request유지 (x)
	RequestDispatcher rd = request.getRequestDispatcher("response_2.jsp");
	rd.forward(request, response); // request를 유지하면서 값을 전달 
	System.out.println(request);
%>