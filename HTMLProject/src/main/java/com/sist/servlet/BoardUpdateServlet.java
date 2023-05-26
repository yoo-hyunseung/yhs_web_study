package com.sist.servlet;

import java.io.*;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;


@WebServlet("/BoardUpdateServlet")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// html 형식으로 받아옴
		response.setContentType("text/html;charset=utf-8");
		String no =request.getParameter("no");
		BoardDAO dao = BoardDAO.newInstance();
		BoardVO vo = dao.BoardDetailData(Integer.parseInt(no));
		String name = vo.getName();
		String subject = vo.getSubject();
		System.out.println(subject);
		String content = vo.getContent();
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=html/table.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1 align=center>Update<h1>");
		out.println("<form method=post action=BoardUpdateServlet>");
		out.println("<table class=table_content align=center border=1 width=700>");
		out.println("<tr>");
		out.println("<th align=center width=20%>번호</th>");
		out.println("<td align=center width=30%>"+no+"</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th align=center width=20%>이름</th>");
//		out.println("<td align=center width=30%>"+name+"</td>");
		out.println("<td align=center width=30%><input type=text name=name value="+name+"></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th align=center width=20%>제목</th>");
//		out.println("<td align=left width=70% >"+subject+"</td>");
//		out.println("<td align=center width=30%><textarea rows=1 cols=7>"+subject+"</textarea></td>");
		out.println("<td align=center width=30%><input type=text value=\""+subject+"\" size=40 name=subject></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td align=left colspan=4 align=left valign=top height=200>");
		out.println("<textarea rows=10 cols=70>"+content+"</textarea>");
//		out.println("<input type=text value="+content+">");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=20%>password</th>");
		out.println("<td width=30%>");
		out.println("<input type=password name=pwd size=15 required>");
		out.println("<input type=hidden name=content value=\""+content+"\">");
		out.println("<input type=hidden name=no value="+no+">");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td colspan=4 align=right>");
		out.println("<input type=submit value=수정>");
		out.println("<input type=button value=취소 onclick=\"javascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8"); //한글변환
			// 디코딩 =- > byte[] 을 한글로 변환
			// 자바 -> 2byte -> 브라우저는 1byte로 받는다. -> 2byte
		} catch (Exception e) {
			// TODO: handle exception
		}
		response.setContentType("text/html;charset=utf-8");
		String no = request.getParameter("no");
		System.out.println(no);
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
//		BoardVO vo = new BoardVO();
		String[] con = {name,subject,content};
		BoardDAO dao = BoardDAO.newInstance();
		PrintWriter out = response.getWriter();
		boolean bCheck = dao.boardUpdate(Integer.parseInt(no), pwd, con);
		if(bCheck) {
			response.sendRedirect("BoardListServlet");
		}
		else {
			
			// 삭제창으로..
			out.println("<script>");
			out.println("alert(\"비밀번호가 틀립니다.\")");
			out.println("history.back();");
			out.println("</script>");
		}
	}

}
