package com.sist.dao;

import java.util.*;

import com.sist.common.CreateDataBase;
import com.sist.vo.*;
import java.sql.*;


public class SeoulDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static SeoulDAO dao;
	private String[] tab = {"","seoul_location","seoul_nature","seoul_shop"};
	
	public static SeoulDAO newInstance() {
		if(dao==null) dao=new SeoulDAO();
		return dao;
	}
	
	// 기능
	// 1. 목록출력
	public List<SeoulVO> seoulListData(int page,int type){
		List<SeoulVO> list = new ArrayList<>();
		try {
			conn = db.getConnection();
			String sql = "select no,title,poster,num "
					+ "from (select no,title,poster,rownum as num "
					+ "from (select no,title,poster "
					+ "from "+tab[type]+" order by no asc)) "
					+ "where num between ? and ?";
			ps = conn.prepareStatement(sql);
			int rowSize =20;
			int start = (rowSize*page) - (rowSize-1);
			int end = rowSize*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
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
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
			
		}
		return list;
	}
	public int seoulTotalPage(int type) {
		int total=0;
		try {
			conn=db.getConnection();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return total;
	}
}
