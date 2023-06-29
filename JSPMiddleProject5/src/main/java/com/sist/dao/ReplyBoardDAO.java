package com.sist.dao;
// DBCP
import java.util.*;
import java.sql.*;
import javax.sql.*; 
import javax.naming.*; // context

public class ReplyBoardDAO {
	// 연결 객체
	private Connection conn;
	// SQL 전송 => 결과값 받기
	private PreparedStatement ps;
	// 싱글톤
	private static ReplyBoardDAO dao;
	
	public static ReplyBoardDAO newInstance() {
		if(dao==null)dao=new ReplyBoardDAO();
		return dao;
	}
	// 주소값 얻기
	public void getConnection() {
		try {
			Context init = new InitialContext();
			Context c = (Context)init.lookup("java:/comp/env");
			DataSource ds = (DataSource)c.lookup("jdbc/oracle");
			conn=ds.getConnection();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void disConnection() {
		try {
			if(ps!=null)ps.close();
			if(conn!=null)conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	// 기능 수행
	// 1. 목록출력
	public List<ReplyBoardVO> boardListData(int page){
		List<ReplyBoardVO> list = new ArrayList<>();
		try {
			getConnection();
			String sql = "select no,subject,name,to_char(regdate,'yyyy-mm-dd'),hit,group_tab,num"
					+ " from (select no,subject,name,regdate,hit,group_tab,rownum as num "
					+ "from (select no,subject,name,regdate,hit,group_tab "
					+ "from replyboard order by group_id desc, group_step asc)) "
					+ "where num between ? and ?";
			ps = conn.prepareStatement(sql);
			int rowSize = 10;
			int start = (rowSize*page) - (rowSize-1);
			int end = rowSize*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyBoardVO vo = new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setGroup_tab(rs.getInt(6));
				list.add(vo);
			}
			rs.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}
	// 1-1 총페이지
	public int boardRowCount() {
		int count=0;
		try {
			getConnection();
			String sql = "select count(*) from replyBoard";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return count;
	}
	// 2. 상세보기
	public ReplyBoardVO boardDetailData(int no) {
		ReplyBoardVO vo = new ReplyBoardVO();
		try {
			getConnection();
			String sql = "update replyboard set "
					+ "hit = hit+1 "
					+ "where no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			// 조회수 증가
			
			sql = "select no,name,subject,content,to_char(regdate,'yyyy-mm-dd'), hit from "
					+ "replyBoard where no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}
	// 3. 추가
	public void boardInsert(ReplyBoardVO vo) {
		try {
			getConnection();
			String sql = "insert into replyboard(no,name,subject,content,pwd,group_id) values("
					+ "rb_no_seq.nextVal,?,?,?,?,"
					+ "(select NVL(max(group_id)+1,1) from replyboard))";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			
			// 실행
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	// 4. 수정
	public ReplyBoardVO boardUpdateData(int no) {
		ReplyBoardVO vo = new ReplyBoardVO();
		try {
			getConnection();
			String sql = "select no,name,subject,content,regdate, hit from "
					+ "replyBoard where no=?";
			
			
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}
	// 4-1 수정
	public boolean boardUpdate(ReplyBoardVO vo) {
		boolean bCheck = false;
		try {
			getConnection();
			String sql = "select pwd from replyboard "
					+ "where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			if(db_pwd.equals(vo.getPwd())) {
				bCheck =true;
				// 수정
				sql = "update replyBoard set "
						+ "name=?,subject=?,content=? "
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
			disConnection();
		}
		return bCheck;
	}
	// 5. 삭제
	public boolean boardDelete(int no , String pwd) {
		boolean bCheck=false;
		try {
			getConnection();
			//pwd check
			String sql = "select pwd from replyBoard where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			if(db_pwd.equals(pwd)) {
				// 삭제되는거
				bCheck = true;
				// 삭제가능여부 확인
				sql = "select root,dept from replyboard "
						+ "where no=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				rs = ps.executeQuery();
				rs.next();
				int root = rs.getInt(1);
				int depth = rs.getInt(2);
				rs.close();
				if(depth==0) {
					// depth -> 밑에 답글의 갯수
					sql = "delete from replyboard where no=?";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, no);
					ps.executeUpdate();
				}else {
					String msg = "관리자가 삭제한 게시물입니다.";
					sql = "update replyboard set "
							+ "subject=?,content=? "
							+ "where no=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, msg);
					ps.setString(2, msg);
					ps.setInt(3, no);
					ps.executeUpdate();
				}
				sql = "update replyboard set "
						+ "dept = dept-1 "
						+ "where no=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, root);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return bCheck;
	}
	// 6. 답변하기
	public void replyInsert(int root, ReplyBoardVO vo) {
		// root => pno
		// 상위 게시물의 정보 => group_id, group_step, group_tab
		// group_step증가
		// insert
		// depth 증가
		try {
			getConnection();
			String sql = "select group_id,group_step,group_tab "
					+ "from replyboard "
					+ "where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, root);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int gi = rs.getInt(1);
			int gs = rs.getInt(2);
			int gt = rs.getInt(3);
			rs.close();
			sql = "UPDATE replyboard set "
					+ "group_step=group_step+1 "
					+ "where group_id = ? and group_step=?";
			// 답변형의 핵심 SQL
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gi);
			ps.setInt(2, gt);
			ps.executeQuery();
			
			// 추가
			sql = "insert into replyboard values(rb_no_seq.nextval,?,"
					+ "?,?,?,sysdate,0,?,?,?,?,0)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.setInt(5, gi);
			ps.setInt(6, gs+1);
			ps.setInt(7 ,gt+1);
			ps.setInt(8, root);
			ps.executeUpdate();
			
			// depth 증가
			sql = "update replyBoard set "
					+ "dept = dept+1 "
					+ "where no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, root);
			ps.executeUpdate();
			
			
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	// 7. 검색하기
	public int boardFindCount(String fs, String ss) {
		int count = 0;
		try {
			getConnection();
			String sql = "select count(*) from replyBoard "
					+ "where "+fs+" like '%'||?||'%'"; // column/table -> 문자열 결합
			ps = conn.prepareStatement(sql);
			ps.setString(1, ss); // ''
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return count;
	}
	public List<ReplyBoardVO> boardFindData(String fs, String ss) {
		List<ReplyBoardVO> list = new ArrayList<>();
		try {
			getConnection();
			String sql = "select no,subject,name,regdate,hit from replyBoard "
					+ "where "+fs+" like '%'||?||'%'"; // column/table -> 문자열 결합
			ps = conn.prepareStatement(sql);
			ps.setString(1, ss); // ''
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyBoardVO vo = new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
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
