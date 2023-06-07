package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/FoodCookieDelServlet")
public class FoodCookieDelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		String cno = request.getParameter("cno");
		String fno = request.getParameter("fno");
		// 네트워크 -> 요청(client (브라우저 )) / 응답 (Server / Servlet)
		//           => 요청처리에 필요한 데이터를 전송
		// 주소
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i =0 ; i < cookies.length;i++) {
				if(cookies[i].getName().equals("food_"+fno)) {
					cookies[i].setPath("/");
					cookies[i].setMaxAge(0); // 삭제
					response.addCookie(cookies[i]); // 전송
					break; // 찾앗음 중지
				}
			}
		}	
		response.sendRedirect("MainServlet?mode=2&cno="+cno);
	}

}
