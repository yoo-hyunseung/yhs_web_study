package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.controller.RequestMapping;
import com.sist.dao.RecipeDAO;
import com.sist.vo.*;
import java.util.*;

public class ListModel {
	
	@RequestMapping("list/recipeList.do")//url 뒤에 붙는페이지 설계
	public String recipeList_page(HttpServletRequest request, HttpServletResponse response) {
		RecipeDAO dao = RecipeDAO.newInstance();
		String curpage = request.getParameter("page");
		String type = request.getParameter("type");
		
		

		if(curpage==null) {
			curpage="1";
		}
		if(type==null) {
			type ="all";
		}
		int totalpage = dao.recipeTotalPage(type);
		List<RecipeVO> rlist = dao.recipeCategoryDataList(type,Integer.parseInt(curpage));
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("rlist", rlist);
		request.setAttribute("type", type);
		request.setAttribute("main_jsp", "../list/recipeList.jsp");
		return "../jsp/main.jsp";
	}
	
	@RequestMapping("list/recipeDetail.do")//url 뒤에 붙는페이지 설계
	public String recipeDetail_page(HttpServletRequest request, HttpServletResponse response) {
		RecipeDAO dao = RecipeDAO.newInstance();
		String rdno = request.getParameter("rdno");
		RecipeVO rvo = dao.recipeDetailData(Integer.parseInt(rdno));
		request.setAttribute("rvo", rvo);
		List<String> step_pos = new ArrayList<>();
		List<String> step_text = new ArrayList<>();
 		StringTokenizer st = new StringTokenizer(rvo.getStep_pos(),"^");
 		StringTokenizer st1 = new StringTokenizer(rvo.getStep_text(),"^");
		while(st.hasMoreTokens()) {
			step_pos.add(st.nextToken());
		}
		while(st1.hasMoreTokens()) {
			step_text.add(st1.nextToken());
		}
		if(step_pos.size() == step_text.size()) {
			request.setAttribute("step_pos", step_pos);
			request.setAttribute("step_text", step_text);
		}else if(step_pos.size() > step_text.size()){
			int minus = step_pos.size() - step_text.size();
			for(int i = 0 ; i < minus;i++) {
				step_text.add(" ");
			}
			request.setAttribute("step_pos", step_pos);
			request.setAttribute("step_text", step_text);
		} else {
			int minus = step_text.size() - step_pos.size();
			for(int i = 0 ; i < minus;i++) {
				step_pos.add(" ");
			}
			request.setAttribute("step_pos", step_pos);
			request.setAttribute("step_text", step_text);
		}
		request.setAttribute("main_jsp", "../list/recipeDetail.jsp");
		return "../jsp/main.jsp";
	}

}
