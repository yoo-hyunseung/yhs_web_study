package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.controller.RequestMapping;
import com.sist.dao.shareDAO;
import com.sist.vo.*;
import java.util.*;
public class ShareModel {
	
	@RequestMapping("share/shareList.do")
	public String share_main(HttpServletRequest request, HttpServletResponse response) {
		String curpage = request.getParameter("page");
		if(curpage==null) {
			curpage="1";
		}
		shareDAO sdao = shareDAO.newInstance();
		List<shareVO> slist = sdao.shareListData(Integer.parseInt(curpage));
		int totalpage = sdao.shareTotalPage();
		request.setAttribute("slist", slist);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("main_jsp", "../share/shareList.jsp");
		return "../jsp/main.jsp";
	}
	
	@RequestMapping("share/shareDetail.do")
	public String share_Detail(HttpServletRequest request, HttpServletResponse response) {
		String skdno = request.getParameter("skdno");
		shareDAO sdao = shareDAO.newInstance();
		shareVO svo = sdao.shareDetailData(Integer.parseInt(skdno));
		
		request.setAttribute("svo", svo);
		request.setAttribute("main_jsp", "../share/shareDetail.jsp");
		return "../jsp/main.jsp";
	}
	
}
