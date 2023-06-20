package com.sist.dao;
/*
 *   데이터 베이스 연동
 *   JDBC =========> DBCP =========> ORM(Mybatis,Hibernate) => JPA
 *                    1차              2차                       3차
 *                   ----
 *                   1. 미리 연결을 한다./Connection갯수를 지정
 *                   2. 연결된 Connection을 얻어온다.
 *                   3. 반환 -> 재사용
 *                   4. 웹프로그램의 일반
 *                    
 *   JDBC는 요청시마다 연결하고 닫기 반복
 *                  --- 연결에 소모되는 시간이 많이 걸린다.
 *                  --- Connection의 갯수를 관리 할 수 없다.
 *                      --------------- 서버 다운될 가능성이 있다.
 *                      
 * 
 */
import java.util.*;
import java.sql.*;
// 서버가 아니라 클라이언트 프로그램(실제 서버 : 오라클)
public class FoodDAO {
	// 연결 객체 => Socket
	private Connection conn;
	// 송수신 (SQL -> 결과 값(데이터값))
	private PreparedStatement ps;
	//URL
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	// singleton
	private static FoodDAO dao;
	// static => 저장공간이 한계
	// 드라이버 등록
	public FoodDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 오라클 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL,"hr","1235");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 오라클 연결해제
	public void disConnection() {
		try {
			if(ps!=null)ps.close();
			if(conn!=null)conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 싱글턴 처리
	public static FoodDAO newInstance() {
		if(dao==null) {
			dao = new FoodDAO();
		}
		return dao;
	}
	// 기능수행
	public List<FoodVO> foodListData(){
		List<FoodVO> list = new ArrayList<>();
		
		try {
			getConnection();
			String sql = "select fno,poster,name,rownum "
					+ "from food_location "
					+ "where rownum<=20";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				FoodVO vo = new FoodVO();
				vo.setFno(rs.getInt(1));
				String poster  = rs.getString(2);
				poster = poster.substring(0,poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(3));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}
	
	// 상세보기 데이터 읽기
	public FoodVO foodDetailData(int fno) {
		FoodVO vo = new FoodVO();
		try {
			getConnection();
			String sql = "select fno,poster,name,score,tel,type,time,parking,price,menu,ADDRESS "
					+ "from food_location "
					+ "where fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
//			String poster  = rs.getString(2);
//			poster = poster.substring(0,poster.indexOf("^"));
//			poster = poster.replace("#", "&");
			vo.setPoster(rs.getString(2).replace("#","&"));
			vo.setName(rs.getString(3));
			vo.setScore(rs.getDouble(4));
			vo.setTel(rs.getString(5));
			vo.setType(rs.getString(6));
			vo.setTime(rs.getString(7));
			vo.setParking(rs.getString(8));
			vo.setPrice(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setAddress(rs.getString(11));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return vo;
	}
}

