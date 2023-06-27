<%@page import="java.util.*"%>
<%@page import="com.sist.dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	301page : Session VS cookie
	---------------------------
	Session / Cookie의 차이점 (GET/POST) -> MVC
	-----------------------
			Session    Cookie
	
	클래스명 (내장객체)    (필요시마다 생성)
			HttpSession  Cookie
			=>HttpSession session = request.getSession(); => interface
			=> Cookie cookie = new Cookie() => 일반 클래스 
			----------------------------------------------------------
	저장 값  자바에서 사용되는 모든 클래스 문자열만 저장이 가능
	        (Object) 
	저장 위치 서버안에 저장   클라이언트(브라우저)저장
	        -> 구분(sessionId)
	           getId() => webSocket
	보안     보안 (0)     보안(x)
	사용처   로그인(사용자일부정보) 최근방문 
	       장바구니
	       
-------------------------------------------------------------
	Cookie
	 1) Cookie 생성
	    Cookie cookie = new Cookie(String key, String value)
	 2) 저장 기간 설정
	    cookie.setMaxAge(초) -> 0 이면 삭제
	           -------------
	 3) 저장 path지정
	    cooke.setPath("/")
	          ------------
	 4) 저장된 쿠키를 클라이언트 브라우저로 전송
	 	request.addCookie(cookie)
	 5) Cookie[] cookies = request.getCookies()
	                       --------------------
	 6) key 읽기   : getName()
	 7) value 읽기 : getValue()
	 -------------------------
	 *** 쿠기/세션 => 상태를 지속적으로 유지 
	                ---- 값 변경(react/vue -> state)
	                데이터 관리 
	 
	 Session (HttpSession)
	 ---------------------- 내장객체
	 서플릿 : request.getSession()
	 JSP : 내장 객체Session)
	 ---------------------- 서버에 저장
	 -> 전역변수
	 -> 서버에 저장 보안이 뛰어나다
	 
	 1) 세션저장
	    session.setAttribute(String key, Object obj)
	 2) 세션 저장기간
	    session.setMaxInactveInterval(60*5) -> 30분 default
	 3) 세션 읽기\
	    Object session.getAttribute(String key)
	    *** 형변환이  필요하다
	 4) 세션 일부 삭지
	 	session.removeAttribute(String key)
	 5) 세션 전체 삭제
	    session.invalidate()
	 6) 생성여부 확인
	    session.isNew()
	 7) 세션에 등록된 sessionId(각클라이언트마다 1개 생성) => 모든정보\\
	 session.getId()
	 ------------------------------------------------------
	 저장 방식 -> Map방식 값언 
	           value는 중복가능
	           key는 중복불가능
	           web=> map방식(request, response, session, application)                      
	    
--%>
<%
	// 출력할 데이터 읽기()
	String strPage = request.getParameter("page");
// 첫 페이지 : default로 설정
	if(strPage==null){
		strPage="1";
	}
int curpage = Integer.parseInt(strPage);
FoodDAO dao = FoodDAO.newInstance();
List<FoodBean> list = dao.foodListData(curpage);
int totalpage = dao.foodTotalPage();
	// 1. 사용자 연결 요청
	// 2. DAO 연결 -> 데이터 읽기
	// -------------------------- 자바(Model)
	// 3. 읽은 데이터 출력 -> HTML
	// 4. ----------------------- HTML을 나눠서 작업(VIEW) MV구조
	Cookie[] cookies = request.getCookies();
	List<FoodBean> cList = new ArrayList<>();
	
	if(cookies!=null){ // 쿠키가 존재하면
		for(int i=cookies.length-1;i>=0;i--){
			if(cookies[i].getName().startsWith("food_")){
				String fno = cookies[i].getValue();
				FoodBean vo = dao.foodDetaildata(Integer.parseInt(fno));
				cList.add(vo);
			}
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container{
   margin-top: 50px;
}
.row {
  margin: 0px auto;
  width:960px;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<!-- 맛집 목록 -->
			<%
         for(FoodBean vo:list)
         {
      %>
             <div class="col-md-3">
			    <div class="thumbnail">
			      <a href="detail_before.jsp?fno=<%=vo.getFno()%>">
			        <img src="<%=vo.getPoster() %>"  style="width:100%">
			        <div class="caption">
			          <p><%=vo.getName() %></p>
			        </div>
			      </a>
			    </div>
			  </div>
      <%
         }
      %>
		</div>
		<div style="height: 20px"></div>
		<div class="row">
			<!--페이지 -->
			<div class="text-center">
				<a href="main.jsp?page=<%=curpage>1?curpage+1:curpage %>" class="btn btn-sm btn-danger">이전</a>
				<%=curpage %> page / <%=totalpage %>pages
				<a href="main.jsp?page=<%=curpage>totalpage?curpage:curpage++ %>" class="btn btn-sm btn-danger">다음</a>
			</div>
		</div>
		<div style="height: 20px"></div>
		<h3>최신 방문 맛집</h3>
		<a href="all_delete.jsp" class="btn btn-xs btn-warning">전체삭제</a>
		<a href="all_cookie.jsp" class="btn btn-xs btn-warning">더보기</a>
		<hr>
		<div class="row">
			<!-- 최신방문 -->
			<%
				int i = 0;
				if(cookies != null && cList.size()>0){
			
					for(FoodBean vo : cList){
						if(i>9)break;
			%>
						<img src="<%=vo.getPoster()%>" style="width: 100px; height: 100px;" title="<%=vo.getName()%>">
						<br>
						<a href="cookie_delete.jsp?fno=<%=vo.getFno() %>" class="btn btn-sm btn-danger">삭제</a>
			<%
						i++;
					}
				}
				else{
			%>
				<h3>쿠키가 없습니다.</h3>
			<%
				}
			%>
			
		</div>
	</div>
</body>
</html>