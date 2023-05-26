package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.*;
@WebServlet("/BoardDetailServlet")
public class BoardDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전송하는 방식
		response.setContentType("text/html;charset=UTF-8");
		
		// client가 보낸 값을 받는다.
		// => BoardDetailServlet?no=1
		// a.jsp?page=1 a.jsp 가 값을 받아서 처리한다.
		String no = request.getParameter("no");
		/*
		 *  ? 뒤에  ?no=1&page=10&name=mmm 
		 *         --------------------- &로 구분
		 *         ajax -> {"no":1}
		 *         react/vue -> params:{"no":1}
		 * 
		 */
		// 오라클에서 값을 받아온다.
		BoardDAO dao = BoardDAO.newInstance();
		BoardVO vo = dao.BoardDetailData(Integer.parseInt(no));
		
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=html/table.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>content<h1>");
		out.println("<table width=700 class=table_content border=1>");
		out.println("<tr>");
		out.println("<th width=20%>번호</th>");
		out.println("<td width=30% align=center>"+vo.getNo()+"</td>");
		out.println("<th width=20%>작성일</th>");
		out.println("<td width=30% align=center>"+vo.getDbday()+"</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=20%>이름</th>");
		out.println("<td width=30% align=center>"+vo.getName()+"</td>");
		out.println("<th width=20%>조회수</th>");
		out.println("<td width=30% align=center>"+vo.getHit()+"</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=20%>제목</th>");
		out.println("<td colspan=3 name=subject>"+vo.getSubject()+"</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td colspan=4 height=200 align=left valign=top>"); //왼쪽상단정렬
		out.println("<pre style\"white-space:pre-wrap\" name=content>"+vo.getContent()+"</pre>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td colspan=4 align=right>");
		out.println("<a href=BoardUpdateServlet?no="+vo.getNo()+">수정</a>&nbsp;");
		out.println("<a href=BoardDeleteServlet?no="+vo.getNo()+">삭제</a>&nbsp;");
		out.println("<a href=BoardListServlet>목록</a>&nbsp;");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
//		out.println("");
		
	}

}
