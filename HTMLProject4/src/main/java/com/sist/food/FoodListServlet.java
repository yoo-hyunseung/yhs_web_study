package com.sist.food;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

@WebServlet("/FoodListServlet")
public class FoodListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//html =>브라우저에 알림
		response.setContentType("text/html;charset=utf-8");
		
		//클라이언트가 전송값을 받는다
		String cno = request.getParameter("cno");
		
		// dao conn
		System.out.println(cno);
		FoodDAO dao = FoodDAO.newInstance();
		List<FoodVO> list = dao.food_category_data(Integer.parseInt(cno));
		CategoryVO cvo = dao.food_category_info(Integer.parseInt(cno));
		
		// 화면에 출력
		PrintWriter out = response.getWriter();
		
		// html에 출력 -> 오라클에서 받은 결과값 출력
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".continer{margin-top:50px}");
		out.println(".row{");
		out.println("margin:0px auto;");// 가운데 정렬
		out.println("width:800px;}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<div class=jumbotron>");
		out.println("<center>");
		out.println("<h3>"+cvo.getTitle()+"</h3>");
		out.println("<h4>"+cvo.getSubject()+"</h4>");
		out.println("</center>");
		out.println("</div>");
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		for(FoodVO vo : list) {
			out.println("<table class=table>");
			out.println("<tr>");
			out.println("<td width=30% align=center rowspan=4>");
			out.println("<a href=FoodDetailServlet?fno="+vo.getFno()+"><img src="+vo.getPoster()+" class=img-rounded style=\"width:240px;height:200px\">");
			out.println("</td>");
			out.println("<td width=70%><h3>");
			out.println("<a href=FoodDetailServlet?fno="+vo.getFno()+">"+vo.getName()+"&nbsp;<span style=\"color:orange\">"+vo.getScore()+"</span>");
			out.println("<h3></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=70% >");
			out.println(vo.getAddress());
			out.println("</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=70% >");
			out.println(vo.getPhone());
			out.println("</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=70% >");
			out.println(vo.getType());
			out.println("</td>");
			out.println("</tr>");
		}
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}
