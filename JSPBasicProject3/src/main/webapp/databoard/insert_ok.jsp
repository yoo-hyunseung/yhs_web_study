<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.sist.dao.*,com.sist.vo.*,com.oreilly.servlet.*,java.io.*"%>
    <%@page import="com.oreilly.servlet.multipart.*"%>
    
<% 
	// _ok.jsp 기능만 있는 경우 
	// 데이터베이스 처리 -> list.jsp
	// 1. 한글처리
	request.setCharacterEncoding("utf-8");
	// 1-1 fileupload클래스 생성
	String path ="/Users/yuhyeonseung/Downloads";
	int size=1024*1024*100; // 최대업로드 크기
	String enctype="UTF-8";
	MultipartRequest mr = new MultipartRequest(request,path,size,enctype,
			new DefaultFileRenamePolicy());
	// 2. 요청데이터 받기
	String name = mr.getParameter("name");
	String subject = mr.getParameter("subject");
	String content = mr.getParameter("content");
	String pwd = mr.getParameter("pwd");
	// 3. VO에 묶는다.
	DataBoardVO vo = new DataBoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	//String filename=mr.getOriginalFileName("upload");
	String filename=mr.getFilesystemName("upload");
	if(filename==null){ // 업로드가 안된상태
		vo.setFilename("");
		vo.setFilesize(0);
	}
	else{ // 업로드가 된상태
		File file = new File(path+"/"+filename);
		vo.setFilename(filename);
		vo.setFilesize((int)file.length());
	}
	// 4. DAO로 이동
	DataBoardDAO dao = DataBoardDAO.newInstance();
	dao.databoardInsert(vo);
	// 5. 화면 이동 (list.jsp)
	response.sendRedirect("list.jsp");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>