package com.sist.view;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

/// 속성 class
@WebServlet("/FoodDetailServlet")
public class FoodDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 브라우저 전송  (response)
		response.setContentType("text/html;charset=utf-8");
		// 전 페이지(FoodListServlet)에서 전송된 값을 받는다. -> fno
		String fno = request.getParameter("fno");
		//request는 사용자가 전송한 데이터를 받을 때 사용한다.
		// 요청 정보
		// 웹 -> 객체(request, response, session) + cookie
		FoodDAO dao = FoodDAO.newInstance();
		FoodVO vo = dao.foodDetailData(Integer.parseInt(fno));
		PrintWriter out = response.getWriter();
		String addr= vo.getAddress();
		String addr1 = addr.substring(0,addr.lastIndexOf("지번"));
		String addr2 = addr.substring(0,addr.lastIndexOf("지")+3);
		// html에 출력 -> 오라클에서 받은 결과값 출력
		out.println("<html>");
		out.println("<head>");
		out.println("<script type=\"text/javascript\" src=\"//dapi.kakao.com/v2/maps/sdk.js?appkey=9965c727d3306713c47391be682e4be9&libraries=services\"></script>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".continer{margin-top:50px}");
		out.println(".row{");
		out.println("margin:0px auto;");// 가운데 정렬
		out.println("width:1024px;}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		// 이미지 5개 
		String poster = vo.getPoster();
		poster = poster.replaceAll("#", "&");
		StringTokenizer st = new StringTokenizer(poster,"^");
		out.println("<table class=table>");
		out.println("<tr>");
		while(st.hasMoreTokens()) {
			out.println("<td>");
			out.println("<img src="+st.nextToken()+" style=\"width:100%\">");
			out.println("</td>");
		}
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
		out.println("<div class=row>");
		// lg , sm , md, xs
		out.println("<div class=col-sm-8>");
		// 상세보기
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<h3>"+vo.getName()+"&nbsp;<span style=\"color:orange\""+
					vo.getScore()+"></span>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<div style=\"heigth:20px\"></div>"); // 공백
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<th width=20%>주소</th>");
		out.println("<td width=80%>"+addr1+"<br><sub>지번:"+addr2+"</sub></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=20%>전화</th>");
		out.println("<td width=80%>"+vo.getPhone()+"</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=20%>음식종류</th>");
		out.println("<td width=80%>"+vo.getType()+"</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=20%>가격대</th>");
		out.println("<td width=80%>"+vo.getPrice()+"</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=20%>주차</th>");
		out.println("<td width=80%>"+vo.getParking()+"</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=20%>영업시간</th>");
		out.println("<td width=80%>"+vo.getTime()+"</td>");
		out.println("</tr>");
		out.println("<tr>");
		if(vo.getMenu().equals("no")) {
			out.println("<th width=20%>메뉴</th>");
			out.println("<td width=80%>");
			st = new StringTokenizer(vo.getMenu(),"원");
			out.println("<ul>");
			while(st.hasMoreTokens()) {
				out.println("<li>");
				out.println(st.nextToken()+"원");
				out.println("</li>");
			}
			out.println("</ul>");
			out.println("</td>");
			out.println("</tr>");
		}
		
		out.println("<th width=20%>메뉴</th>");
		out.println("<td width=80%>"+vo.getMenu()+"</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<tr colspan=4>");
		out.println("<td class=text-right>");
		out.println("<a href=# class=\"btn btn-xs btn-danger\">예약하기</a>");
		out.println("<a href=# class=\"btn btn-xs btn-success\">찜하기</a>");
		out.println("<a href=# class=\"btn btn-xs btn-warning\">좋아요</a>");
		out.println("<a href=\"MainServlet?mode=2&cno="+vo.getCno()+"\" class=\"btn btn-xs btn-info\">목록</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</div>");
		out.println("<div class=col-sm-4>");
		// 지도출력
//		RequestDispatcher rd = request.getRequestDispatcher("Map.jsp");
//		rd.include(request, response);
		out.println("</div>");
		out.println("</div>");
		out.println("</div>"); // row
		out.println("</div>"); // container
		out.println("</body>"); // row
		out.println("</html>"); // row
		
	}

}
