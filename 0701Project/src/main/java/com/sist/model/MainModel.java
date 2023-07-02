package com.sist.model;
import com.sist.controller.*;
import java.util.*;
import com.sist.vo.*;
import com.sist.dao.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MainModel {
	@RequestMapping("jsp/main.do")
	public String main_page(HttpServletRequest request,HttpServletResponse response) {
		// 구현 ~ random 출력 레시피
		RecipeDAO rdao = RecipeDAO.newInstance();
		List<RecipeVO> rlist = rdao.randomRecipeList();
		request.setAttribute("rlist", rlist);
		
		
//		공유주방 random 출력 
		shareDAO sdao = shareDAO.newInstance();
		List<shareVO> slist =sdao.randomShareList();
		request.setAttribute("slist", slist);
		
		request.setAttribute("main_jsp", "../jsp/home.jsp");
		return "../jsp/main.jsp";
//		return "redirect:../boardList.jsp";
	}
	
}
