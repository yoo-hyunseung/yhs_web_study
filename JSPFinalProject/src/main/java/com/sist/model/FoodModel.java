package com.sist.model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.controller.RequestMapping;

import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
public class FoodModel {
  @RequestMapping("food/location_find.do")
  public String food_find(HttpServletRequest request,HttpServletResponse response)
  {
	  // 검색어를 받는다 
	  try
	  {
		  request.setCharacterEncoding("UTF-8");
	  }catch(Exception ex){}
	  String fd=request.getParameter("fd");
	  if(fd==null)
	  {
		  fd="마포";
	  }
	  String page=request.getParameter("page");
	  // JSP안에서는 page는 내장 객체 
	  if(page==null)
		  page="1";
	  int curpage=Integer.parseInt(page);
	  // => DAO를 연결해서 값을 전송 
	  FoodDAO dao=FoodDAO.newInstance();
	  List<FoodVO> list=dao.foodLocationFindData(fd, curpage);
	  int totalpage=dao.foodLoactionTotalPage(fd);
	  
	  final int BLOCK=10;
	  int startPage=((curpage-1)/BLOCK*BLOCK)+1;
	  int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
	  if(endPage>totalpage)
		  endPage=totalpage;
	  
	  // location_find.jsp로 전송할 데이터 
	  request.setAttribute("curpage", curpage);
	  request.setAttribute("totalpage", totalpage);
	  request.setAttribute("startPage", startPage);
	  request.setAttribute("endPage", endPage);
	  request.setAttribute("list", list);
	  request.setAttribute("fd", fd);
	  request.setAttribute("main_jsp", "../food/location_find.jsp");
	  return "../main/main.jsp";
  }
  /*
   *  어노테이션 : 찾기 -> if문이 추가된다 
   *  --------  밑에 있는 class , method 변수
   * @Controller -> 메모리 할당 관련
   * class A {
   * 	@Autowired
   * 	B b; -> 자동으로 b의 주소값 대입
   * 	@RequestMapping("food/list.do")
   * 	public void disp(@Resorce int a){}
   * }
   */
  @RequestMapping("food/food_category_list.do")
  public String food_list(HttpServletRequest request,HttpServletResponse response) {
	  
	  String cno = request.getParameter("cno");
	  FoodDAO dao = FoodDAO.newInstance();
	  CategoryVO cvo = dao.foodCategoryInfoData(Integer.parseInt(cno));
	  List<FoodVO> list = dao.foodCategoryListData(Integer.parseInt(cno));
	  
	  //HttpServletRequest request,HttpServletResponse response -> dispatcherServlet에서 넘어온거
	  // 매개 변수로 전송 -> 전송된 request에 값을 채운다 setAttribute
	  request.setAttribute("cvo", cvo);
	  request.setAttribute("list", list);
	  // 전송 -> return 에 있는 jsp 가 받는다.
	  // 
	  
	  
	  request.setAttribute("main_jsp", "../food/food_category_list.jsp");
	  
	  return "../main/main.jsp";
  }
  
  @RequestMapping("food/food_detail_before.do")
  public String food_detail_before(HttpServletRequest request,HttpServletResponse response) {
	  //cookie 생성
	  
	  String fno = request.getParameter("fno");
	  Cookie cookie = new Cookie("food"+fno,fno);
//	  쿠키의 단점은 항상 문자열만 저장한다.(요청한 사용자의 브라우저로 전송)
//			저장위치 설정
	  cookie.setPath("/");
	  cookie.setMaxAge(60*60*24); // 하루저장 ms 초단위
//	  저장기간 설정
	  
//	  전송
	  response.addCookie(cookie);
	  return "redirect:../food/food_detail.do?fno"+fno;
  }
/*
  회원가입 / 로그인 -> main.do
  d예약/ 장바구니 / 결제 --> mypage.do
  */
  @RequestMapping("food/food_detail.do")
  public String food_detail(HttpServletRequest request,HttpServletResponse response) {
	  String fno = request.getParameter("fno");
	  
	  // dao 연결
	  FoodDAO dao = FoodDAO.newInstance();
	  // 결과를 request에 담는다.
	  FoodVO vo = dao.foodDetailData(Integer.parseInt(fno));
	  request.setAttribute("vo", vo);
	  String address = vo.getAddress();
	  String addr1 = address.substring(0,address.indexOf("지번"));
	  String addr2 = address.substring(address.indexOf("지번")+3);
	  request.setAttribute("addr1", addr1.trim());
	  request.setAttribute("addr2", addr2.trim());
			  
	  //인근 명소, 레시피
	  
	  request.setAttribute("main_jsp", "../food/food_detail.jsp");
	  
	  return "../main/main.jsp";
  }
  
}






