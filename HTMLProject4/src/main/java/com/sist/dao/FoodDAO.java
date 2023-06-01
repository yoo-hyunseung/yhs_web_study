package com.sist.dao;
/*
 *  => 카테고리 출력 => 카테고리별 맛집 => 맛집에 대한 상세보기 => 지도 출력,검색(Ajax)
 *  
 */

import java.sql.*;
import java.util.*;

public class FoodDAO {
	// 연결하는 객체
	private Connection conn;
	
	// 송수신
	private PreparedStatement ps;
	// 오라클 URL 주소
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
	// singleton
	private static FoodDAO dao;
	
	// 1.드라이버 등록	-> 한번 수행 => 시작과 동시에 한번 수행 => 멤버변수 초기화 : 생성자
	public FoodDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 예외 처리 -> ClassNotFoundException => 체크예외처리 => 반드시 예외 처리해야 함
		//	java.io , java.sql, java.net 필수
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 2. 오라클 연결
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,"hr","1235");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 3. 오라클 연결 해제
	public void disConnection() {
		try {
			if(ps!=null)ps.close(); // 통신이 열려있으면 닫는다.
			if(conn!=null)conn.close(); // exit; 오라클 닫기
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 4. 싱글턴 설정 => static은 메모리 공간에 하나다.
	// 메모리 누수를 줄인다.
	// DAO를 new 를 사용해서 생성 => 사용하지 않는 DAO가 오라클은 연결하고 있다.
	// 싱글톤은 데이터베이스에서 필수 조건
	// 프로그래머 , 디벨로퍼 (코더)
	// java web html/css
	public static FoodDAO newInstance() {
		if(dao==null) dao = new FoodDAO();
		return dao;
	}
	// 5. 기능
	// 5-1. 카테고리 출력
	public List<CategoryVO> food_category_list(){
		// 카테고리 1개 정보 (번호, 이미지, 제목, 부제목)
		List<CategoryVO> list = new ArrayList<>();
		try {
			getConnection();
			String sql ="select cno,title,subject,poster "
					+ "from food_category "
					+ "order by cno asc";
			// index 자동 생성(primary , unique는 자동 생성0
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				CategoryVO vo = new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setPoster(rs.getString(4));
				list.add(vo);
			}
			rs.close();
			// list => 받아서 브라우저로 전송실행
//			                ------ servlet jsp
//			                 Spring => servlet => DispatcherServlet 배달 서플릿
			//  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	//5-1-1. 카테고리 정보
	public CategoryVO food_category_info(int cno) {
		CategoryVO vo = new CategoryVO();
		try {
			getConnection();
			String sql = "select title,subject "
					+ "from food_category "
					+ "where cno="+cno;
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setTitle(rs.getString(1));
			vo.setSubject(rs.getString(2));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return vo;
	}
	// 5-2. 카테고리별 맛집 출력
	public List<FoodVO> food_category_data(int cno){
		List<FoodVO> list = new ArrayList<>();
		try {
			getConnection();
			String sql = "select fno,name,poster,address,phone,type,score "
					+ "from food_house "
					+ "where cno="+cno;
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FoodVO vo = new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				String poster =rs.getString(3);
				poster = poster.substring(0,poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				/*
				 * https://mp-seoul-image-production-s3.mangoplate.com/653082_1629603975501431.jpg?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80^https://mp-seoul-image-production-s3.mangoplate.com/804901_1680423954319935.jpg?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80^https://mp-seoul-image-production-s3.mangoplate.com/804901_1680423955121730.jpg?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80^https://mp-seoul-image-production-s3.mangoplate.com/804901_1680423955476424.jpg?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80^https://mp-seoul-image-production-s3.mangoplate.com/415973/1842582_1680273240849_106705?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80
				 */
				String address = rs.getString(4);
				address = address.substring(0,address.lastIndexOf("지"));
				vo.setAddress(address.trim());
				vo.setPhone(rs.getString(5));
				vo.setType(rs.getString(6));
				vo.setScore(rs.getDouble(7));
				list.add(vo);
				// adress -> 서울특별시 중랑구 공릉로 32 공감대아파트 상가 1F 지번 서울시 중랑구 묵동 171-4 공감대아파트 상가 1F
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
		
	}
	// 5-3. 맛집 상세보기 & 지도출력
	public FoodVO foodDetailData(int fno) {
		FoodVO vo = new FoodVO();
		try {
			getConnection();
			String sql = "SELECT fno,cno,name,poster,phone,type,address,time,parking,menu,price,score "
					+ "from food_house "
					+ "where fno=?";
			ps=conn.prepareStatement(sql);
			// ?에 값을 채운다 = >JSP/프로젝트
			// 2차 프로젝트 => MyBatis (비밀번호 암호화), 실시간 처리 =>Betch
			// 3차 MySQL JPA
			// 기반 => MSA기반 => CI/CD (젠킨스)
			ps.setInt(1, fno);
			// 실행요청 => 결과값 받기
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			// 커서 위치 변경 = > 데이터가 출력한 위치로 변경
			vo.setFno(rs.getInt(1));
			vo.setCno(rs.getInt(2));
			vo.setName(rs.getString(3));
			vo.setPoster(rs.getString(4));
			vo.setPhone(rs.getString(5));
			vo.setType(rs.getString(6));
			vo.setAddress(rs.getString(7));
			vo.setTime(rs.getString(8));
			vo.setParking(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setPrice(rs.getString(11));
			vo.setScore(rs.getDouble(12));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return vo;
	}
	// 5-4. 맛집 검색
//	List<FoodVO>
	public List<FoodVO> foodFindData(String addr, int page){
		List<FoodVO> list = new ArrayList<>();
		try {
			getConnection();
//			String sql = "SELECT fno,name,poster,score "
//					+ "from food_location "
//					+ "where address like '%'||?||'%'";
			// mysql => like concat('%',?,'%');
			String sql = "select fno,name,poster,score,num "
					+ "from (select fno,name,poster,score,rownum as num "
					+ "from (select fno,name,poster,score "
					+ "from food_location "
					+ "where address like '%'||?||'%')) "
					+ "where num between ? and ?";
			ps = conn.prepareStatement(sql);
			int rowSize = 12;
			int start = (rowSize*page)-(rowSize-1);
			int end = rowSize*page;
			ps.setString(1, addr);
			ps.setInt(2, start);
			ps.setInt(3, end);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FoodVO vo = new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				String poster = rs.getString(3);
				if(poster.contains("^")) {
					poster = poster.substring(0,poster.indexOf("^"));
				}
//				System.out.println(poster.indexOf("^"));
				poster = poster.replaceAll("#", "&");
				vo.setPoster(poster);
				vo.setScore(rs.getDouble(4));
				list.add(vo);
			}
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	// total page ==> 어디서 가저오냐
	public int foodRowCount(String addr) {
		int count=0;
		try {
			getConnection();
			String sql = "select count(*) from food_location "
					+ "where address like '%'||?||'%'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, addr);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return count;
	}

	// 5-5. 댓글 (CURD) => 로그인
}
