package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

@WebServlet("/SeoulHotelServlet")
public class SeoulHotelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		SeoulDAO dao = SeoulDAO.newInstance();
		List<SeoulVO> list = dao.seoul_Hotel_ListData(2);
		// 카테고리 정보를 오라클로부터 받는다ㅏ.
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".continer{margin-top:50px}");
		out.println(".row{");
		out.println("margin:0px auto;");
		out.println("width:1200px;}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h1>서울 호텔</h1>");
		out.println("<div class=row>");
		for(SeoulVO vo : list) {

			out.println("<div class=\"col-md-3\">"); // 한줄에 4개  출력 12가 되면다음줄
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=#>");
			out.println("<img src=\""+vo.getPoster()+"\" alt=\"Lights\" style=\"width:256px; height:190px;\">");
			out.println("<div class=\"caption\">");
			out.println("<p style=\"font-size:9px\">"+vo.getTitle()+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>"); // row
		out.println("</div>"); // row
		out.println("</body>"); // row
		out.println("</html>"); // row
	}

}

