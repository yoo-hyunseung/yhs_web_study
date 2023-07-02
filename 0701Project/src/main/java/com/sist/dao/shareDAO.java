package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sist.common.CreateDataBase;
import com.sist.vo.RecipeVO;
import com.sist.vo.shareVO;

public class shareDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static shareDAO dao;
	public static shareDAO newInstance() {
		if(dao==null)dao=new shareDAO();return dao;
	}
	/* 페이지 나누기
	 * 1. listData -> inlineView
	 *   -> 페이지 번호랑, 페이징 -> totalpage  count; 
	 */
	
	// main random 출력
	public List<shareVO> randomShareList(){
		List<shareVO> slist = new ArrayList<>();
		try {
			conn=db.getConnection();
			// random 4개 출력 main
			String sql = "select poster,title,max_mem,price,skdno from "
					+ "(select poster,title,max_mem,price,skdno from share_kitchen_detail order by DBMS_RANDOM.RANDOM) "
					+ "where rownum<=4";
			ps =conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				shareVO vo = new shareVO();
				String poster = rs.getString(1);
				poster = poster.replaceAll("#", "&");
				vo.setPoster(poster);
				vo.setTitle(rs.getString(2));
				vo.setMax_mem(rs.getString(3));
				vo.setPrice(rs.getString(4));
				vo.setSkdno(rs.getInt(5));
				slist.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return slist;
	}
	public List<shareVO> shareListData(int page){
		List<shareVO> slist = new ArrayList<>();
		try {
			conn = db.getConnection();
			String sql = "select poster,title,max_mem,price,skdno num "
					+ "from (select poster,title,max_mem,price,skdno,rownum as num "
					+ "from (select /*+ INDEX_ASC(share_kitchen_detail skd_skdno_pk)*/poster,title,max_mem,price,skdno "
					+ "from share_kitchen_detail))"
					+ "where num between ? and ?";
			ps=conn.prepareStatement(sql);
			int rowSize = 12;
			int start = (rowSize*page)-(rowSize-1);
			int end = rowSize*page;
			ps.setInt(1,start);
			ps.setInt(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				shareVO vo = new shareVO();
				String poster = rs.getString(1);
				poster = poster.replaceAll("#", "&");
				vo.setPoster(poster);
				vo.setTitle(rs.getString(2));
				vo.setMax_mem(rs.getString(3));
				vo.setPrice(rs.getString(4));
				vo.setSkdno(rs.getInt(5));
				slist.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.disConnection(conn,ps);
		}
		return slist;
	}
	public int shareTotalPage() {
		int totalpage =0;
		try {
			conn = db.getConnection();
			String sql = "select CEIL(count(*)/12.0) from share_kitchen_detail";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalpage = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn,ps);
		}
		return totalpage;
	}
	public shareVO shareDetailData(int skdno) {
		shareVO svo = new shareVO();
		try {
			/*
			 * skdno      number         NOT NULL,
			  title      varchar2(500)  NOT NULL,
			  sub_title  varchar2(400)  NOT NULL,
			  intro      clob           NOT NULL,
			  run       varchar2(1000) NOT NULL,
			  holi       varchar2(200)  ,
			  info       clob           NOT NULL,
			  caution    clob           NOT NULL,
			  address    varchar2(1000)  NOT NULL,
			  hs_tag     varchar2(800)  NOT NULL,
			  max_mem    varchar2(300),
			  poster     varchar2(400),
			  price      varchar2(100),
			  refund     clob           NOT NULL,
			 */
			conn=db.getConnection();
			String sql = "select * from share_kitchen_detail where skdno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, skdno);
			ResultSet rs =ps.executeQuery();
			rs.next();
			svo.setSkdno(rs.getInt(1));
			svo.setTitle(rs.getString(2));
			svo.setSub_title(rs.getString(3));
			svo.setIntro(rs.getString(4));
			svo.setRun(rs.getString(5));
			svo.setHoli(rs.getString(6));
//			svo.setInfo(rs.getString(7));
			String info = rs.getString(7);
			info = info.substring(info.indexOf("1")+1);
			svo.setInfo(info);
			svo.setCaution(rs.getString(8));
			svo.setAddress(rs.getString(9));
			svo.setHs_tag(rs.getString(10));
			svo.setMax_mem(rs.getString(11));
			String poster = rs.getString(12);
			poster = poster.replaceAll("#", "&");
			svo.setPoster(poster);
			svo.setPrice(rs.getString(13));
			svo.setRefund(rs.getString(14));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return svo;
	}
}
