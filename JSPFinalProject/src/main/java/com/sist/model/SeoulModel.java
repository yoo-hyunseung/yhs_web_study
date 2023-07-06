package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.common.CommonModel;
import com.sist.controller.RequestMapping;
import com.sist.vo.*;
import com.sist.dao.SeoulDAO;
import java.util.*;
import java.sql.*;
public class SeoulModel {
	@RequestMapping("seoul/seoul_list.do")
	public String seoulList(HttpServletRequest request,HttpServletResponse response) {
		
		String page = request.getParameter("page");
		if(page==null) page="1";
		String type = request.getParameter("type");
		if(type==null) type="1";
		int curpage = Integer.parseInt(page);
		
		SeoulDAO dao = SeoulDAO.newInstance();
		List<SeoulVO> list = dao.seoulListData(curpage, Integer.parseInt(type));
		int totalpage = dao.seoulTotalPage(Integer.parseInt(type));
		
		final int BLOCK = 5;
		int startPage =((curpage-1)/BLOCK*BLOCK)+1;
		int endPage = ((curpage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalpage) {
			endPage = totalpage;
		}
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("list", list);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("type", type);
		String[] msg= {"","서울 명소","서울 자연","서울 쇼핑"};
		request.setAttribute("msg", msg[Integer.parseInt(type)]);
		request.setAttribute("main_jsp", "../seoul/seoul_list.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("seoul/seoul_detail.do")
	public String seoul_detail(HttpServletRequest request,HttpServletResponse response) {
		String no = request.getParameter("no");
		String type = request.getParameter("type");
		
		
		
		
		request.setAttribute("main_jsp", "../seoul/seoul_detail.jsp");
		
		return "../main/main.jsp";
	}
}
