package com.sist.dao;
import java.util.*;
import java.sql.*;

import com.sist.vo.*;
import com.sist.common.*;
public class FreeBoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static FreeBoardDAO dao;
	
	// 싱글톤 패턴
	public static FreeBoardDAO newInstance() {
		if(dao == null) dao=new FreeBoardDAO();
		return dao;
	}
	// 기능
	// 1. 목록 출력 -> inline view 페이징
	public List<FreeBoardVO> freeboardListData(int page){
		List<FreeBoardVO> list = new ArrayList<>();
		try {
			conn = db.getConnection();
			String sql = "select no, subject,name,to_char(regdate,'yyyy-mm-dd'), "
					+ "hit,num from (select no,subject,name,regdate,hit, rownum as num "
					+ "from (select /* index_desc(project_freeboard pf_no_pk)*/ no,subject,name,regdate,hit "
					+ "from project_freeboard)) where no between ? and ?";
			ps = conn.prepareStatement(sql);
			int rowSize = 10;
			int start = (rowSize*page)-(rowSize-1);
			int end = rowSize*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FreeBoardVO vo = new FreeBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setRownum(rs.getInt(6));
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
	// 1-1 총페이지
	public int freeboardTotalPage() {
		int total = 0;
		try {
			conn =db.getConnection();
			String sql = "select ceil(count(*)/10.0) from project_freeboard";
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
	// 2. Top 7 rownum (HIT desc Top-N
	// 3. 글쓰기 create
	public void freeboardInsert(FreeBoardVO vo) {
		try {
			conn = db.getConnection();
			String sql = "insert into project_freeboard values(pf_no_seq.nextVal,"
					+ "?,?,?,?,sysdate,0)";
			ps= conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.executeUpdate();
					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		
	}
	// 4. 상세보기  read
	public FreeBoardVO freeboarodDetailData(int no) {
		FreeBoardVO vo = new FreeBoardVO();
		try {
			conn = db.getConnection();
			String  sql = "update project_freeboard set "
					+ "hit = hit + 1 "
					+ "where no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
			// 실제 데이터 읽기
			sql = "select no,subject,name,content,to_char(regdate,'yyyy-mm-dd'),hit "
					+ "from project_freeboard where no =?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setSubject(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	// 상세보기
	// jsp -> dispatcherServlet -> Model ->dispatcherServlet -> jsp 
	// model -> model / dao / vo 3가지
	
	// 5. 수정하기 update ->  Ajax 
	public FreeBoardVO freeboarodUpdateData(int no) {
		FreeBoardVO vo = new FreeBoardVO();
		try {
			conn = db.getConnection();
			String sql = "select no,subject,name,content "
					+ "from project_freeboard where no =?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setSubject(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setContent(rs.getString(4));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	// 5-1 수정하기
	public boolean freeboardUpdate(FreeBoardVO vo) {
		boolean bCheck = false;
		try {
			conn = db.getConnection();
			String sql = "select pwd from project_freeboard where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(vo.getPwd())) {
				bCheck=true;
				sql = "update project_freeboard set "
						+ "name = ?, subject=?,content=? "
						+ "where no=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getNo());
				
				ps.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return bCheck;
	}
	// 6. 삭제하기 delete -> Ajax
	public String freeboardDelete(int no, String pwd) {
		String res="NO";
		try {
			conn = db.getConnection();
			String sql = "select pwd from project_freeboard "
					+ "where no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);

			rs.close();
			if(db_pwd.equals(pwd)) {
				res="YES";
				sql = "delete from project_freeboard where no =?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return res;
	}
	
	

	
}
