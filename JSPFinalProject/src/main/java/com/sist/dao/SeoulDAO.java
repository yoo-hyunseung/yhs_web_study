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
	public List<SeoulVO> seoulListData(int page,int type)
	   {
		   List<SeoulVO> list=new ArrayList<SeoulVO>();
		   try
		   {
			   conn=db.getConnection();
			   String sql="SELECT no,title,poster,num "
					     +"FROM (SELECT no,title,poster,rownum as num "
					     +"FROM (SELECT no,title,poster "
					     +"FROM "+tab[type]+" ORDER BY no ASC)) "
					     +"WHERE num BETWEEN ? AND ?";
			   ps=conn.prepareStatement(sql);
			   int rowSize=20;
			   int start=(rowSize*page)-(rowSize-1);
			   int end=rowSize*page;
			   ps.setInt(1, start);
			   ps.setInt(2, end);
			   ResultSet rs=ps.executeQuery();
			   while(rs.next())
			   {
				   SeoulVO vo=new SeoulVO();
				   vo.setNo(rs.getInt(1));
				   vo.setTitle(rs.getString(2));
				   vo.setPoster(rs.getString(3));
				   
				   list.add(vo);
			   }
			   rs.close();
		   }catch(Exception ex)
		   {
			   ex.printStackTrace();
		   }
		   finally
		   {
			   db.disConnection(conn, ps);
		   }
		   return list;
	   }
	   // 총페이지 구하기 
	   public int seoulTotalPage(int type)
	   {
		   int total=0;
		   try
		   {
			   conn=db.getConnection();
			   String sql="SELECT CEIL(COUNT(*)/20.0) FROM "+tab[type];
			   ps=conn.prepareStatement(sql);
			   ResultSet rs=ps.executeQuery();
			   rs.next();
			   total=rs.getInt(1);
			   rs.close();
		   }catch(Exception ex)
		   {
			   ex.printStackTrace();
		   }
		   finally
		   {
			   db.disConnection(conn, ps);
		   }
		   return total;
	   }
	   
	public SeoulVO seoulDetailData(int no, int type) {
		SeoulVO vo = new SeoulVO();
		try {
			conn=db.getConnection();
			String sql ="update "+tab[type]+" set "
					+ "hit = hit+1 "
					+ "where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
			sql = "select no,title,poster,address,msg "
					+ "from "+tab[type]+" where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			vo.setPoster(rs.getString(3));
			vo.setAddress(rs.getString(4));
			vo.setMsg(rs.getString(5));
			
			
			rs.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
}
