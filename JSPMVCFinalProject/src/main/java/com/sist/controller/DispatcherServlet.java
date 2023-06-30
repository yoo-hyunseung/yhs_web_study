package com.sist.controller;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.*;
import java.util.*;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	private List<String> clsList = new ArrayList<>();
	private static final long serialVersionUID = 1L;
//  초기화 -> XML에 등록된 클래스 읽기 (메뉴)
	
    /* MVC 동작 과정
       -----------
       1. 요청 (JSP) -> DispatcherServlet을 찾는다.
          list.do                |
          insert.do              |
          ---------          서버에서 받을 수 있는 부분 URI , URL
                            URI => Model을 찾는다.
       2. DispatcherServlet(Controller)
          => Front-Controller : 요청 -> Model 연결 -> request를 전송
                                       ---------
                                       요청 처리 기능을 가지고 있다.
                                       
	   3. MVC 목적 : 보안 (JSP => 배포 : 소스를 통으로)
	                역할 분담(Front-End(JSP) , Back-End(자바,DAO))
	                자바와 HTML을 분리하는 이유
	                --- 
	                확장성, 재사용, 변경이 쉽다.(JSP는 한번사용하면 버린다.)
	                MVC, MVVP ....
	   4. 동작 순서          .do                     request
	      JSP(링크,버튼클릭) ------- DispatcherServlet ----> Model(DAO <=>오라클)
	                                                      결과값을 request에 담아둔다.
	                                                      request.setAttribute()
	              JSP  <----------DispatcherServlet <--------
	                      request                   request를 넘겨준다.
	               |       
	               |
	               request.getAttribute() => ${}
	   5. DispatcherServlet은 최대한 고정시켜놔야한다.
	      -------------------------------------
	   6. 등록 (Model클래스) => XML로 세팅 (메뉴판)
	   
	   7. 메소드 찾기 => 어노테이션 @ (메소드를 자동 호출이 가능하)
	   ----------------------------------------
	   어려운 난이도 : 맥 / 윈도우 경로명 호환
	          
     * 
     *      
     *      */
	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			URL url = this.getClass().getClassLoader().getResource(".");
			File file = new File(url.toURI());
			System.out.println(file.getPath());
			///Users/yuhyeonseung/Desktop/sist/webstudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JSPMVCFinalProject/WEB-INF/classes
			String path = file.getPath();
			path = path.replace("\\", File.separator);
			// window => \\ , Mac => /
			path = path.substring(0,path.lastIndexOf(File.separator));
			System.out.println(path);
			path = path+File.separator+"application.xml";
			
			// xml parsing
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			// 파서기  xml -> DocumentBuilder , html -> Jsoup)
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			// parser 데이터만 가져오는거  document로 저장
			Document doc =db.parse(new File(path));
			
			// 필요한 데이터를 읽기
			// root태그 -> beans
			Element beans = doc.getDocumentElement();
			System.out.println(beans.getTagName());
			
			// 같은 태그를 묶어서 사용
			NodeList list = beans.getElementsByTagName("bean");
			for(int i= 0; i<list.getLength();i++) {
				// bean tag를 1개씩 가지고 온다.
				Element bean = (Element)list.item(i);
				String id = bean.getAttribute("id");
				String cls = bean.getAttribute("class");
				System.out.println(id+":"+cls );
				clsList.add(cls);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} 
	}
//	웹에서 사용자 요청
// 	servlet : 화면 출력은 하지 않는다.
//	화면 JSP(VIEW)
/*
	Controller : Servlet
		Spring : DispatcherServlet
		struts : ActionServlet
		struts2: FilterDispatcher
		               ---------- 배달부 (request)
	Model : 요청처리 -> java
	View : 화면 출력 : JSP
	                 ---- HTML 
	*/
	
	// 요청에 따라
	
	// uri 에서는 요청값이 -> aa.do?id=aa&pwd=22 => 값은 짤린다.
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = request.getRequestURI();  // model return
		System.out.println("path : "+path);
		path = path.substring(request.getContextPath().length()+1);
//		http://localhost
//		/JSPMVCFinalProject/   food/category.do -> path
//		-------------------- contextPath()
//		food/category.do -> path
//		경로명 안에 파일을 가져온다.
		try {
			for(String cls : clsList) {
				// Class 정보읽기
				Class clsName = Class.forName(cls);
	
				// 메모리 할당
				Object obj = clsName.getDeclaredConstructor().newInstance();
//				A a = new A()
				// 메소드를 읽어온다.
				Method[] methods = clsName.getDeclaredMethods();
				for(Method m : methods) {
					RequestMapping rm = m.getAnnotation(RequestMapping.class);
					if(rm.value().equals(path)) {
						String jsp = (String)m.invoke(obj, request,response);
						if(jsp==null) { // void(ajax)
							return;
						}
						else if(jsp.startsWith("redirect:")) {
							// sendRedirect
							response.sendRedirect(jsp.substring(jsp.indexOf(":")+1));
							
							
						} else {
							// Re
							RequestDispatcher rd = request.getRequestDispatcher(jsp);
							rd.forward(request, response);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
			
	}

}
