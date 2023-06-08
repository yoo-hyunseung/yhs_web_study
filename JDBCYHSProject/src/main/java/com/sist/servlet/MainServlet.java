package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 	get = doGet();
 * Post = doPost(); ==> 405
 * ---- +
 *      service()
 *      
 *      @GetMapping
 *      @PostMapping
 *      ------------ @RequestMapping
 * 
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//전송타입(response) => 
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		//화면 출력
		PrintWriter out = response.getWriter();
		String mode = request.getParameter("mode");// 화면변경
		String servlet = "";
		if(mode==null) {
			mode ="1";
		}
		switch(mode) {
		case "1":
			servlet = "EmpListServlet";
			break;
		case "2":
			servlet = "EmpDetailServlet";
			break;
		case "3":
			servlet = "EmpInsertServlet";
			break;
		case "4":
			servlet = "EmpUpdateServlet";
			break;
		}
		out.write("<!DOCTYPE html>\n");
	      out.write("<html>\n");
	      out.write("<head>\n");
	      out.write("<meta charset=\"UTF-8\">\n");
	      out.write("<title>Insert title here</title>\n");
	      out.write("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n");
	      out.write("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js\"></script>\n");
	      out.write("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>\n");
	      out.write("</head>\n");
	      out.write("<body>\n");
	      out.write("<nav class=\"navbar navbar-inverse navbar-fixed-top\">\n");
	      out.write("  <div class=\"container-fluid\">\n");
	      out.write("    <div class=\"navbar-header\">\n");
	      out.write("      <a class=\"navbar-brand\" href=\"MainServlet\">WebSiteName</a>\n");
	      out.write("    </div>\n");
	      out.write("    <ul class=\"nav navbar-nav\">\n");
	      out.write("      <li class=\"active\"><a href=\"MainServlet\">home</a></li>\n");
	      out.write("      <li class=\"dropdown\"><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">사용자<span class=\"caret\"></span></a>\n");
	      out.write("        <ul class=\"dropdown-menu\">\n");
	      out.write("          <li><a href=\"MainServlet\">사원목록</a></li>\n");
	      out.write("          <li><a href=\"MainServlet?mode=3\">사원등록</a></li>\n");
	      out.write("        </ul>\n");
	      out.write("      <li class=\"dropdown\"><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">관리자<span class=\"caret\"></span></a>\n");
	      out.write("        <ul class=\"dropdown-menu\">\n");
	      out.write("          <li><a href=\"MainServlet?mode=5&type=1\">그룹별 통계</a></li>\n");
	      out.write("          <li><a href=\"MainServlet?mode=5&type=2\">그룹별 순위</a></li>\n");
	      out.write("      </li>\n");
	      out.write("    </ul>\n");
	      out.write("  </div>\n");
	      out.write("</nav>\n");
	      // include => <jsp:include page="">
	      // 파일을 첨부
	      RequestDispatcher rd = request.getRequestDispatcher(servlet);
	      rd.include(request, response);
	      out.write("</body>\n");
	      out.write("</html>");
	}

}
