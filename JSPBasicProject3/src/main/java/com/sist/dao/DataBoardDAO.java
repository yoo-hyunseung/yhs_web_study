package com.sist.dao;
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

import com.sist.vo.DataBoardVO;

import java.sql.*;

public class DataBoardDAO {
	private Connection conn;
	// 송수신 (SQL -> 결과 값(데이터값))
	private PreparedStatement ps;
	//URL
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	// singleton
	private static DataBoardDAO dao;
	// static => 저장공간이 한계
	// 드라이버 등록
	public DataBoardDAO() {
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
	public static DataBoardDAO newInstance() {
		if(dao==null) {
			dao = new DataBoardDAO();
		}
		return dao;
	}
	// 기능
	// 1. 목록 -> 페이징 (인라인뷰)
	// 2. 블록별 -> 1 , 2 ,3,4,5,
	public List<DataBoardVO> databoardListData(int page){
		List<DataBoardVO> list = new ArrayList<>();
		try {
			getConnection();
			String sql = "select no,subject,name,to_char(regdate,'yyyy-mm-dd'),hit,num "
					+ "from (select no,subject,name,regdate,hit,rownum as num "
					+ "from (select /*+ INDEX_DESC(jspDataBoard jd_no_pk)*/ no,subject,name,regdate,hit "
					+ "from jspDataBoard)) "
					+ "where num between ? and ?";
			// Top-N 중간에서 자를수 없다.rownum
			ps=conn.prepareStatement(sql);
			// send sql  -> prepare미리 보내고 이후에 ? 값을 채운다.
			int rowSize=10;
			int start = (rowSize*page)-(rowSize-1);
			int end = rowSize*page;
			/*
			 *  rownum => 1번
			 *  1page -> 1 ~ 10
			 *  2page -> 11 ~ 20
			 *  ...
			 *  
			 */
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				DataBoardVO vo = new DataBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	// 총페이지
	public int databoardRowCount() {
		int count = 0;
		try {
			getConnection();
			String sql = "Select count(*) from jspDataBoard";
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return count;
	}
	public void databoardInsert(DataBoardVO vo) {
		try {
			getConnection();
			String sql = "insert into jspDataBoard values("
					+ "jd_no_seq.nextVal,?,?,?,?,sysdate,0,?,?)";
			ps = conn.prepareStatement(sql);
			// ? 값을 채운다.
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.setString(5, vo.getFilename());
			ps.setInt(6, vo.getFilesize());
			// 실행요청
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
	}
	// 내용보기
	public DataBoardVO databoardDetailData(int no) {
		DataBoardVO vo = new DataBoardVO();
		try {
			getConnection();
			String sql = "update jspDataBoard set "
					+ "hit=hit+1 "
					+ "where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
			sql ="select no,name,subject,content,to_char(regdate,'yyyy-mm-dd'),hit,filename,filesize "
					+ "from jspDataBoard "
					+ "where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
			vo.setFilename(rs.getString(7));
			vo.setFilesize(rs.getInt(8));
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
