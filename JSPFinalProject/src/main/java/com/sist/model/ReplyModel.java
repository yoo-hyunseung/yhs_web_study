package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.*;

public class ReplyModel {
	@RequestMapping("reply/reply_insert.do")
	public String reply_insert(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			String cno = request.getParameter("cno");
			String type = request.getParameter("type");
			String msg = request.getParameter("msg");
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("id");
			String name = (String)session.getAttribute("name");
			
			ReplyVO vo = 
			
		}
	}
}
