package com.sist.dao;

import java.util.*;
import java.sql.*;
public class SeoulDAO {
	private Connection conn;
	
	// 송수신
	private PreparedStatement ps;
	// 오라클 URL 주소
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
	// singleton
	private static SeoulDAO dao;
	
	// 1.드라이버 등록	-> 한번 수행 => 시작과 동시에 한번 수행 => 멤버변수 초기화 : 생성자
	public SeoulDAO() {
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
	public static SeoulDAO newInstance() {
		if(dao==null) dao = new SeoulDAO();
		return dao;
	}
	// 5. 기능
	// 목록출력 => sql inline view -> 페이징 기법
	public List<SeoulVO> seoul_Location_ListData(int page){
		List<SeoulVO> list = new ArrayList<>();
		try {
			getConnection();
			String sql = "select no,title,poster,num "
					+ "from (select no,title,poster,rownum as num "
					+ "from (select no,title,poster "
					+ "from seoul_location order by no asc)) "
					+ "where num between ? and ?";
			// rownum 가상 컬럼 (오라클에서 지원)
			// 단점 => 중간에 데이터를 추출할 수 없다. => Top-N
			// sql 문장 보내기
			ps = conn.prepareStatement(sql);
			// ? 값을 채운다. 안채우면 => IN.OUT 입출력에러
			int rowSize =12; // 한페이지에 출력하는 갯수
//			int start =  (page-1)*(rowSize)+1;
			int start = (rowSize*page)-(rowSize-1);
			int end = page*rowSize;
			ps.setInt(1,start);
			ps.setInt(2, end);
			// 실행요청
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				SeoulVO vo = new SeoulVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			disConnection();
		}
		return list;
	}
	public List<SeoulVO> seoul_Hotel_ListData(int page){
		List<SeoulVO> list = new ArrayList<>();
		try {
			getConnection();
			// sql
			String sql = "select no,title,poster,num "
					+ "from (select no,title,poster, rownum as num "
					+ "from (select no, title, poster "
					+ "from seoul_hotel order by no)) "
					+ "where no between ? and ?";
			// 연결
			ps = conn.prepareStatement(sql);
			// ? value
			int rowSize = 12;
			int start = (rowSize*page)-(rowSize-1);
			int end = rowSize*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			// result
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				SeoulVO vo = new SeoulVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				list.add(vo);
			}
			rs.close();
		
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			disConnection();
		}
		return list;
	}
	
}
