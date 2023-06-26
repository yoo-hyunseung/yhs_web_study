package com.sist.dao;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static FoodDAO dao;
	
	// 미리 생성된 객체 읽기 => Connection관련 (DBCP) => 연결에 소모되는 시간을 절약
	// DBCP는 웹에서만 사용이 가능 ==> 자바 애플리케이션에서는 사용할 수 없다.
	public void getConnection() {
		try {
			Context init = new InitialContext();
			Context c = (Context)init.lookup("java://comp/env");
			DataSource ds = (DataSource)c.lookup("jdbc/oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	// 반환
	public void disConnection() {
		try {
			if(ps!=null)ps.close();
			if(conn!=null)conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 싱글톤
	public static FoodDAO newInstance() {
		if(dao ==null)dao=new FoodDAO();
		return dao;
	}
	// 기능처리s
	public List<FoodBean> foodAllData(){
		List<FoodBean> list = new ArrayList<>();
		try {
			getConnection();
			String sql = "select poster,name,tel from food_house";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FoodBean vo = new FoodBean();
				vo.setName(rs.getString(2));
				vo.setTel(rs.getString(3));
				String poster = rs.getString(4);
				poster = poster.substring(0,poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
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
}
