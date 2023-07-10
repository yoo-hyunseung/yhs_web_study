package com.sist.model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;

public class DiaryModel {
	@RequestMapping("diary3/diary.do")
	public String diaryData(HttpServletRequest request,HttpServletResponse response) {
		
		String fno = request.getParameter("fno");
		String strYear = request.getParameter("year");
		String strMonth = request.getParameter("month");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		String today = sdf.format(date);
		StringTokenizer st = new StringTokenizer(today,"-");
		String sy = st.nextToken();
		String sm = st.nextToken();
		String sd = st.nextToken();
		if(strYear == null){
			strYear = sy;
		}
		if(strMonth == null){
			strMonth = sm;
		}
		int year = Integer.parseInt(strYear);
		int month = Integer.parseInt(strMonth);
		int day = Integer.parseInt(sd);
		
		String [] strWeek ={"일","월","화","수","목","금","튜"};
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH,month-1);
		cal.set(Calendar.DATE,1); // 1일자
		int week = cal.get(Calendar.DAY_OF_WEEK); // 요일 구하기
		int lastday = cal.getActualMaximum(Calendar.DATE);// 각 달의 마지막 일
		
		week = week-1;
		
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("week", week);
		request.setAttribute("lastday", lastday);
		request.setAttribute("strWeek", strWeek);
		
		// 오라클 데이터를 읽어 온다.
		int[] rday = new int[32];
		ReserveDAO dao = ReserveDAO.newInstance();
		String r = dao.foodReserveDay(Integer.parseInt(fno));
		st = new StringTokenizer(r,",");
		
		while(st.hasMoreTokens()) {
			int a = Integer.parseInt(st.nextToken());
			if(a>=day) { // day 는 오늘 날짜 , 오늘 날짜보다는 커야 예약가능 하다.
				rday[a] = 1;
			}
		}
		request.setAttribute("rday", rday);
		return "diary.jsp";
	}
	
	@RequestMapping("diary3/foodlist.do")
	public String food_List(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}
		String type = request.getParameter("type");
		if(type==null) {
			type="한식";
		}
		
		ReserveDAO dao = ReserveDAO.newInstance();
		List<FoodVO> list = dao.FoodReserveData(type);
		request.setAttribute("list", list);
		
		return "foodlist.jsp";
	}
	
	@RequestMapping("diary3/reservemain.do")
	public String reserve_main(HttpServletRequest request,HttpServletResponse response) {
		return "reservemain.jsp";
	}
	@RequestMapping("diary3/time.do")
	public String reserve_Time(HttpServletRequest request,HttpServletResponse response) {
		String day = request.getParameter("day");
		ReserveDAO dao = ReserveDAO.newInstance();
		String times= dao.reserve_day_time(Integer.parseInt(day));
		StringTokenizer st = new StringTokenizer(times,",");
		List<String> list = new ArrayList<>();
		while(st.hasMoreTokens()) {
			String time = dao.reserve_get_time(Integer.parseInt(st.nextToken()));
			list.add(time);
		}
		request.setAttribute("list", list);
		return "time.jsp";
	}
	@RequestMapping("diary3/inwon.do")
	public String reserve_inwon(HttpServletRequest request,HttpServletResponse response) {
		return "inwon.jsp";
	}
}
