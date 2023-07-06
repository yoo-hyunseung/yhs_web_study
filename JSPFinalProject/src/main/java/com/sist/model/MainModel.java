package com.sist.model;
import java.util.*;
import com.sist.common.*;
import com.sist.vo.*;
import com.sist.dao.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.controller.RequestMapping;
public class MainModel {
   @RequestMapping("main/main.do")
   public String main_page(HttpServletRequest request,HttpServletResponse response)
   {
	   // home.jsp -> category 전송
	   FoodDAO dao = FoodDAO.newInstance();
	   List<CategoryVO> list = dao.foodCategoryListData();
	   request.setAttribute("list", list);
	   request.setAttribute("main_jsp", "../main/home.jsp");
	   
	   // cookie 전송
	   Cookie[] cookies = request.getCookies();
	   List<FoodVO> clist = new ArrayList<>();
	   if(cookies!=null) {
		   for(int i =cookies.length-1 ; i>=0;i--) {
			   if(cookies[i].getName().startsWith("food_")) {
				   String fno = cookies[i].getValue();
				   FoodVO vo = dao.foodDetailData(Integer.parseInt(fno));
				   clist.add(vo);
			   }
		   }
	   }
	   request.setAttribute("clist", clist);
	   CommonModel.commonRequestData(request);
	   return "../main/main.jsp";
   }
}