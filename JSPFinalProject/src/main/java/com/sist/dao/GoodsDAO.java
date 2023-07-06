package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;
public class GoodsDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private String [] tab = {" ","goods_all","goods_best","goods_new","goods_special"};
	
	private static GoodsDAO dao;
	
	// singleton
	public static GoodsDAO newInstance() {
		if(dao==null)dao = new GoodsDAO();
		return dao;
	}
	
	// 목록
	public List<GoodsVO> goodsListData(int page,int type){
		List<GoodsVO> list = new ArrayList<>();
		try {
			conn = db.getConnection();
			String sql = "select no,goods_poster,goods_price,goods_name,num "
					+ "from (select no,goods_poster,goods_price,goods_name,rownum as num "
					+ "from (select no,goods_poster,goods_price,goods_name "
					+ "from "+tab[type]+" order by no asc)) "
					+ "where num between ? and ?";
			ps = conn.prepareStatement(sql);
			int rowSize=12;
			int start = (rowSize*page)-(rowSize-1);
			int end= (rowSize*page);
			
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs =ps.executeQuery();
			while(rs.next()) {
				GoodsVO vo = new GoodsVO();
				vo.setNo(rs.getInt(1));
				vo.setGoods_poster(rs.getString(2));
				vo.setGoods_price(rs.getString(3));
				vo.setGoods_name(rs.getString(4));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	public int goodsTotalPage(int type) {
		int total = 0;
		try {
			conn = db.getConnection();
			String sql = "select ceil(count(*)/12.0) from "+tab[type];
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return total;
	}
	
	// Detail Data goods
	// type 에 해당되는 번호를 가져와
	public GoodsVO goodsDetailData(int no, int type) {
		GoodsVO vo = new GoodsVO();
		/*
		 private int no,goods_discount,hit;
		 private String account,goods_name,goods_sub,
		                goods_price,goods_delivery,
		                goods_poster; 
		 
		 */
		try {
			conn=db.getConnection();
			String sql = "update "+tab[type]+" set "
					+ "hit=hit+1 where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate(); //-> auto commit -> conn.commit() 포함
			
			sql ="select no,goods_poster,goods_name,goods_sub,"
					+ "goods_price,goods_first_price,goods_discount,account,"
					+ "goods_delivery "
					+ "from "+tab[type]+" "
							+ "where no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs= ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setGoods_poster(rs.getString(2));
			vo.setGoods_name(rs.getString(3));
			vo.setGoods_sub(rs.getString(4));
			vo.setGoods_price(rs.getString(5));
			vo.setGoods_first_price(rs.getString(6));
			vo.setGoods_discount(rs.getInt(7));
			vo.setAccount(rs.getInt(8));
			
			vo.setGoods_delivery(rs.getString(9));
			rs.close();
					
			String temp = vo.getGoods_price();
			// 숫자를 제외하고 나머지를 공백으로
			temp = temp.replaceAll("[^0-9]", "");
			vo.setPrice(Integer.parseInt(temp));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
}
