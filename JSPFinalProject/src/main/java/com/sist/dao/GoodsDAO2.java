package com.sist.dao;
import java.util.*;
import java.sql.*;
public class GoodsDAO2 {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public GoodsDAO2() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL,"hr","happy");
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
	public List<Integer> goodsGetNoData(String tab){
		List<Integer> list = new ArrayList<>();
		try {
			getConnection();
			String sql = "select no from "+tab+" order by no asc";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int no = rs.getInt(1);
				list.add(no);
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
	public void goodsAccountInsert(int account,int no ,String tab) {
		try {
			getConnection();
			String sql = "update "+tab+" "
					+ "set account=? "
					+ "where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, account);
			ps.setInt(2, no);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	public static void main(String[] args) {
		GoodsDAO2 dao = new GoodsDAO2();
		List<Integer> list = dao.goodsGetNoData("goods_best");
		for(int no : list) {
			int account = (int)(Math.random()*10)+11;
			dao.goodsAccountInsert(account, no, "goods_best");
		}
		System.out.println("완료1--");
		List<Integer> list1 = dao.goodsGetNoData("goods_new");
		for(int no : list1) {
			int account = (int)(Math.random()*10)+11;
			dao.goodsAccountInsert(account, no, "goods_new");
		}
		System.out.println("완료2--");
		List<Integer> list2 = dao.goodsGetNoData("goods_special");
		for(int no : list2) {
			int account = (int)(Math.random()*10)+11;
			dao.goodsAccountInsert(account, no, "goods_special");
		}
		System.out.println("완료3--");
		List<Integer> list3 = dao.goodsGetNoData("goods_all");
		for(int no : list2) {
			int account = (int)(Math.random()*10)+11;
			dao.goodsAccountInsert(account, no, "goods_all");
		}
		System.out.println("완료4--");
	}
}
