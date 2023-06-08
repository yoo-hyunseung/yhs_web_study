package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

@WebServlet("/EmpInsertServlet")
public class EmpInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 입력폼 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		EmpDAO dao = new EmpDAO();
		List<Integer> mList = dao.empGetMgrData();
		List<Integer> dList = dao.empGetDeptnoData();
		List<Integer> sList = dao.empGetSalData();
		List<String> jList = dao.empGetJobData();
		
		out.write("<!DOCTYPE html>\n");
	      out.write("<html>\n");
	      out.write("<head>\n");
	      out.write("<meta charset=\"UTF-8\">\n");
	      out.write("<title>Insert title here</title>\n");
	      out.write("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n");
	      out.write("<style>");
	      out.write(".container {margin-top:50px;}");
	      out.write(".row {margin: 0px auto; width:600px;}");
	      out.write("h1{text-align:center;}");
	      out.write("</style>");
	      out.write("</head>\n");
	      out.write("<body>\n");
	      out.write("<div class=container>");
	      out.write("<h1>사원 등록");
	      out.write("</h1>");
	      out.write("<div class=row>");
	      
	      out.write("<form method=post action=EmpInsertServlet>");
	      out.write("<table class=table>\n");
	      out.write("<tr>\n");
	      out.write("<th width=20% class\"text-right success\">이름<th>\n");
	      out.write("<td width=80%><input type=text name=ename class=\"input-sm\" size=15>\n");
	      out.write("</td>\n");
	      out.write("</tr>\n");
	      out.write("<tr>\n");
	      out.write("<th width=20% class\"text-right success\">직위<th>\n");
	      out.write("<td width=80%>");
	      out.write("<select name=job class=\"input-sm\">");
	      
	      for(String s : jList) {
	    	  out.write("<option>"+s+"</option>");
	      }
	      out.write("</select>");
	      out.write("</td>\n");
	      out.write("</tr>\n");
	      out.write("<tr>\n");
	      out.write("<th width=20% class\"text-right success\">사수번호<th>\n");

	      out.write("<td width=80%>");
	      out.write("<select name=mgr class=\"input-sm\">");
	      for(int i : mList) {
	    	  if(i==0)continue;
	    	  out.write("<option>"+i+"</option>");
	      }
	      out.write("</select>");
	      out.write("</td>\n");
	      out.write("</tr>\n");
	      out.write("<tr>\n");
	      out.write("<th width=20% class\"text-right success\">급여<th>\n");
	      out.write("<td width=80%>");
	      out.write("<select name=sal class=\"input-sm\">");
	      for(int i : sList) {
	    	  if(i==0)continue;
	    	  out.write("<option>"+i+"</option>");
	      }
	      out.write("</select>");
	      out.write("</td>\n");
	      out.write("</tr>\n");
	      out.write("<tr>\n");
	      out.write("<th width=20% class\"text-right success\">성과급<th>\n");
	      out.write("<td width=80%><input type=number name=comm class=\"input-sm\" size=15 max=500 min=100 step=50>\n");
	      out.write("</td>\n");
	      out.write("</tr>\n");
	      out.write("<tr>\n");
	      out.write("<th width=20% class\"text-right success\">부서번호<th>\n");
	      out.write("<td width=80%>");
	      out.write("<select name=deptno class=\"input-sm\">");
	      
	      for(int i : dList) {
	    	  out.write("<option>"+i+"</option>");
	      }
	      out.write("</select>");
	      out.write("</td>\n");
	      out.write("</tr>\n");
	      out.write("<tr>\n");
	      out.write("<td colspan=2 class=text-center>\n");
	      out.write("<button class=\"btn btn-sm btn-primary\">등록</button>&nbsp;\n");
	      out.write("<input type=button value=취소 class=\"btn btn-sm btn-primary\" onclick=\"javascript:history.back()\">\n");
	      out.write("</td>\n");
	      out.write("</tr>\n");
	      out.write("</table>\n");
	      out.write("</form>");
	      
	      out.write("</div>\n");
	      out.write("</div>\n");
	      out.write("</body>\n");
	      out.write("</html>\n");
	}
// 브라우저에서 값을 넘길때 %EC%E~~~로 넘어간다. 1바이트씩(encoding)
// 입력된 데이터를 처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 한글변환(encoding => decoding) 
		request.setCharacterEncoding("utf-8");
		
		// 2.값을 받는다.
		String ename = request.getParameter("ename");
		String job = request.getParameter("job");
		String mgr = request.getParameter("mgr");
		String sal = request.getParameter("sal");
		String comm = request.getParameter("comm");
		String deptno = request.getParameter("deptno");
		
		EmpVO vo = new EmpVO();
		vo.setEname(ename);
		vo.setJob(job);
		vo.setMgr(Integer.parseInt(mgr));
		vo.setSal(Integer.parseInt(sal));
		vo.setComm(Integer.parseInt(comm));
		vo.setDeptno(Integer.parseInt(deptno));
		
		//오라클로 전송 => 데이터추가
		EmpDAO dao = new EmpDAO();
		dao.empInsert(vo);
		
		// 화면이동
		response.sendRedirect("MainServlet");
	}

}
