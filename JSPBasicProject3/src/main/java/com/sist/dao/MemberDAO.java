package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {

	private Connection conn;
	// 송수신 (SQL -> 결과 값(데이터값))
	private PreparedStatement ps;
	//URL
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	// singleton
	private static MemberDAO dao;
	// static => 저장공간이 한계
	// 드라이버 등록
	public MemberDAO() {
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
	public static MemberDAO newInstance() {
		if(dao==null) {
			dao = new MemberDAO();
		}
		return dao;
	}
	// 로그인 처리
	public MemberVO isLogin(String id, String pwd) {
		MemberVO vo = new MemberVO();
		try {
			getConnection();
			// id 존재여부 확인
			String sql = "select count(*) from jspMember "
					+ "where id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			if(count == 0) { // id가 없는 경우
				vo.setMsg("NOID");
			}else {
				sql = "select id,name,sex,pwd "
						+ "from jspMember "
						+ "where id=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, id);
				rs = ps.executeQuery();
				rs.next();
				String db_id = rs.getString(1);
				String name = rs.getString(2);
				String sex=rs.getString(3);
				String db_pwd = rs.getString(4);
				rs.close();
				if(db_pwd.equals(pwd)) {
					vo.setId(db_id);
					vo.setName(name);
					vo.setSex(sex);
					vo.setMsg("OK");
				}else {
					vo.setMsg("NOPWD");
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}
}
