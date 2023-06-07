package com.sist.view;

import java.io.*;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 화면 분할 -> 모든 이동(MainServlet) => 레이아웃(include)
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//전송타입(response) => 
		response.setContentType("text/html;charset=utf-8");
		//화면 출력
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		// 사용자가 보낸준 값.
		String mode = request.getParameter("mode");
		String servlet = "";
		if(mode==null) {
			mode = "1";
		}
		switch (mode) {
		case "1": 
			servlet = "FoodCategory";
			break;
		case "2":
			servlet = "FoodListServlet";
			break;
		case "3":
			servlet = "FoodDetailServlet";
			break;
		case "4":
			servlet = "FoodSearchServlet";
			break;
		case "5":
			servlet = "SeoulServlet";
			break;
		}
		out.write("\n");
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
	      out.write("      <li class=\"dropdown\"><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">서울맛집<span class=\"caret\"></span></a>\n");
	      out.write("        <ul class=\"dropdown-menu\">\n");
	      out.write("          <li><a href=\"MainServlet\">맛집목록</a></li>\n");
	      out.write("          <li><a href=\"MainServlet?mode=4\">맛집검색</a></li>\n");

	      out.write("        </ul>\n");
	      out.write("      <li class=\"dropdown\"><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">서울맛집<span class=\"caret\"></span></a>\n");
	      out.write("        <ul class=\"dropdown-menu\">\n");
	      out.write("          <li><a href=\"MainServlet?mode=5&type=1\">명소</a></li>\n");
	      out.write("          <li><a href=\"MainServlet?mode=5&type=2\">자연</a></li>\n");
	      out.write("          <li><a href=\"MainServlet?mode=5&type=3\">쇼핑</a></li>\n");
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
