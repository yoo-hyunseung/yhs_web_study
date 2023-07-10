package com.sist.temp;

/*
 * CREATE TABLE reserve_day(
	rno NUMBER,
	rday NUMBER CONSTRAINT rd_rday_nn NOT NULL,
	time varchar2(200) CONSTRAINT rd_time_nn NOT NULL ,
	CONSTRAINT rd_rno_pk PRIMARY KEY(rno)
);
 */
import java.util.*;
import java.sql.*;
public class ReserveMain {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	public ReserveMain() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL,"hr","1235");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void disConnection() {
		try {
			if(ps!=null)ps.close();
			if(conn!=null)conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void reserveDayInsert(int day, String time) {
		try {
			getConnection();
			String sql = "insert into reserve_day values("
					+ "(select nvl(max(rno)+1,1) from reserve_day), ?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, day);
			ps.setString(2, time);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	public List<Integer> foodGetFnoData(){
		List<Integer> list = new ArrayList<>();
		try {
			getConnection();
			String sql = "select fno from food_house";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1));
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
	public void foodReserveDayUpdate(int fno,String rday) {
		try {
			getConnection();
			String sql = "update food_house set "
					+ "reserve_day=? "
					+ "where fno=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, rday);
			ps.setInt(2, fno);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	public String reserveTimeData() {
		String result = "";
		int [] com = new int[(int)(Math.random()*7)+5];
		
		int rand = 0;
		boolean bCheck = false;
		for(int i =0 ; i < com.length;i++) {
			bCheck=true;
			while(bCheck) {
				rand = (int)(Math.random()*22)+1;
				bCheck=false;
				for(int j = 0; j < i;j++) {
					if(com[j]==rand) {
						bCheck = true;
						break;
					}
				}
			}
			com[i] = rand;
		}
		Arrays.sort(com);
		System.out.println(Arrays.toString(com));
		for(int i =0 ; i <com.length;i++) {
			result +=com[i]+",";
		}
		result = result.substring(0,result.lastIndexOf(","));
		return result ;
	}
	public String reserveDayData() {
		String result = "";
		int [] com = new int [(int)(Math.random()*10)+10];
		
		int rand = 0;
		boolean bCheck = false;
		for(int i =0 ; i < com.length;i++) {
			bCheck=true;
			while(bCheck) {
				rand = (int)(Math.random()*31)+1;
				bCheck = false;
				for(int j = 0; j < i;j++) {
					if(com[j]==rand) {
						bCheck = true;
						break;
					}
				}
			}
			com[i] = rand;
		}
		Arrays.sort(com);
		System.out.println(Arrays.toString(com));
		for(int i =0 ; i <com.length;i++) {
			result +=com[i]+",";
		}
		result = result.substring(0,result.lastIndexOf(","));
		return result ;
	}
	
	public static void main(String[] args) {
		ReserveMain rm = new ReserveMain();
		System.out.println(rm.reserveTimeData());
		for(int i =1; i <= 31 ; i++) {
			String s = rm.reserveTimeData(); // 예약가능 시간 모아둔거
			rm.reserveDayInsert(i, s);
		}
		System.out.println("저장완료");
		
//		List<Integer> list = rm.foodGetFnoData();
//		for(int fno:list) {
//			String s = rm.reserveDayData(); // 예약가능 날짜 모아둔거
//			rm.foodReserveDayUpdate(fno, s);
//		}
//		System.out.println("저장완료11");
	}
	
	
}
