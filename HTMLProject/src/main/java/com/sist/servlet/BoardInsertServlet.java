package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;

/**
 * Servlet implementation class BoardInsertServlet
 */
@WebServlet("/BoardInsertServlet")
public class BoardInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 입력을 전송  -> HTML(HTML을 브라우저로 보낸다.)
		response.setContentType("text/html;charset=UTF-8");
		//메모리에 HTML을 저장 => 브라우저에 읽어서출력
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=html/table.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>글쓰기</h1>");
		out.println("<form method=post action=BoardInsertServlet>");
		// 입력된 데이터를 한번에 -> action에 등록된 클래스로 전송
		// 메소드 -> method=post -> doPost()
		out.println("<table class=table_content width=700>");
		out.println("<tr>");
		out.println("<th width=15%>이름</th>");
		out.println("<td width=85%><input type=text name=name size=15 required></td>");// required -> not null
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>제목</th>");
		out.println("<td width=85%><input type=text name=subject size=50 required></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>내용</th>");
		out.println("<td width=85%><textarea rows=10 cols=50 name=content required></textarea></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>비밀번호</th>");
		out.println("<td width=85%><input type=password name=pwd size=10 required></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td colspan=2 align=center>");
		out.println("<input type=submit value=글쓰기>");
		out.println("<input type=button value=취소 onclick=\"javascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/*
	 * http://localhost:8080/HTMLProject/BoardInsertServlet?
	 * name=%ED%99%8D%EA%B8%B8%EB%8F%99&
	 * subject=%EC%B2%98%EC%9D%8C+%EB%A7%8C%EB%93%9C%EB%8A%94+%EA%B2%8C%EC%8B%9C%ED%8C%90&
	 * content=%EC%B2%98%EC%9D%8C+%EB%A7%8C%EB%93%9C%EB%8A%94+%EA%B2%8C%EC%8B%9C%ED%8C%90&
	 * pwd=1234
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8"); //한글변환
			// 디코딩 =- > byte[] 을 한글로 변환
			// 자바 -> 2byte -> 브라우저는 1byte로 받는다. -> 2byte
		} catch (Exception e) {
			// TODO: handle exception
		}
		//%ED%99%8D%EA%B8%B8%EB%8F%99&
		// 인코딩
		// 홍길동 -> 디코딩
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		
//		System.out.println("이름 : "+name);
//		System.out.println("제목 : "+subject);
//		System.out.println("내용 : "+content);
//		System.out.println("비밀번호 : "+pwd);
		BoardVO vo = new BoardVO();
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		
		// 값 전송
		BoardDAO dao = BoardDAO.newInstance();
		dao.boardInsert(vo);
		
		//insert 후 화면이동 
		response.sendRedirect("BoardListServlet");
		
		
		
	}

}
