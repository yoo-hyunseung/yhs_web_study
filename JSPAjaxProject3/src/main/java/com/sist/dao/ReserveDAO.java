package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.common.*;
public class ReserveDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static ReserveDAO dao;
	
	// singleton 
	
	public static ReserveDAO newInstance() {
		if(dao==null)dao = new ReserveDAO();
		return dao;
	}
	public List<FoodVO> FoodReserveData(String type){
		List<FoodVO> list = new ArrayList<>();
		try {
			conn = db.getConnection();
			String sql = "select poster,name,phone,reserve_day,fno "
					+ "from food_house "
					+ "where type like '%'||?||'%'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, type);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FoodVO vo = new FoodVO();
				String poster = rs.getString(1);
				poster = poster.substring(0,poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(2));
				vo.setPhone(rs.getString(3));
				vo.setReserve_day(rs.getString(4));
				vo.setFno(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	
	// 예약 가능 날짜 맛집마다 가능한 날짜를 가지고 온다
	public String foodReserveDay(int fno) {
		String result ="";
		try {
			conn = db.getConnection();
			String sql = "select reserve_day from food_house "
					+ "where fno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = rs.getString(1);
			// 2,3,5,6,8,12,15,16,18,20,21,22,24,25,26,28,30,31
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
	public String reserve_day_time(int tno) {
		String result = "";
		try {
			conn = db.getConnection();
			String sql = "select time from reserve_day "
					+ "where rno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, tno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = rs.getString(1);
			// 2,3,5,6,8,12,15,16,18,20,21,22,24,25,26,28,30,31
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
	public String reserve_get_time(int tno) {
		String result = "";
		try {
			conn = db.getConnection();
			String sql = "select time from reserve_time "
					+ "where tno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, tno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = rs.getString(1);
			// 2,3,5,6,8,12,15,16,18,20,21,22,24,25,26,28,30,31
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
}
