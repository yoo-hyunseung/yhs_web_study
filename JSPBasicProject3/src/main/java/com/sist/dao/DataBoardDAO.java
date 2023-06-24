package com.sist.dao;
import java.util.*;
import com.sist.vo.*;
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
	public DataBoardVO databoardDetailData(int no, int type) {
		DataBoardVO vo = new DataBoardVO();
		try {
			getConnection();
			if(type == 0) {
				String sql = "update jspDataBoard set "
						+ "hit=hit+1 "
						+ "where no=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}
			String sql ="select no,name,subject,content,to_char(regdate,'yyyy-mm-dd'),hit,filename,filesize "
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
	// 인기순위 10개
	public List<DataBoardVO> databoardTop10(){
		List<DataBoardVO> list = new ArrayList<>();
		try {
			getConnection();
			String sql ="select no,name,subject,rownum "
					+ "from (select no,name,subject "
					+ "from jspDataBoard order by hit desc) "
					+ "where rownum <=10";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				DataBoardVO vo = new DataBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSubject(rs.getString(3));
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
	// 게시글 삭제 file을 지우는거
	public DataBoardVO databoardFileInfo(int no) {
		DataBoardVO vo = new DataBoardVO();
		// 파일명을 알아야 한다 =
		try {
			getConnection();
			String sql = "select filename,filesize "
					+ "from jspDataBoard "
					+ "where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setFilename(rs.getString(1));
			vo.setFilesize(rs.getInt(2));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}
	// 게시글에 댓글 -> 게시글 삭제 순서
	public boolean databoardDelete(int no , String pwd) {
		boolean bCheck = false;
		try {
			getConnection();
			String sql = "select pwd from jspdataboard"
					+ " where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			if(db_pwd.equals(pwd)) {
				bCheck = true;
				sql = "delete from jspreply "
						+ "where bno=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
				// 참조하는거부터 지우고 (댓글) -> 게시글 삭제 순서
				sql = "delete from jspdataboard where no=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
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
	// 수정하기
	public boolean databoardUpdate(DataBoardVO vo) {
		boolean bCheck = false;
		try {
			getConnection();
			// 비밀번호 검색 
			String sql = "select pwd from jspDataBoard "
					+ "where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			if(db_pwd.equals(vo.getPwd())) {
				bCheck = true;
				sql ="update jspdataboard set "
						+ "name=? ,subject=?,content=? "
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
	// 댓글
	// 1. 댓글 추가
	public void replyInsert(ReplyVO vo) {
		try {
			getConnection();
			// id와 name은 session에 저장되어잉ㅆ어어 안보냄
			String sql = "insert into jspreply values("
					+ "jr_no_seq.nextVal,?,?,?,?,sysdate)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getBno());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getName());
			ps.setString(4, vo.getMsg());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	// 2. 댓글 읽기
	public List<ReplyVO> replyListData(int bno){ // 게시물 번호를 받아서 댓글 리스트를 출력
		List<ReplyVO> list = new ArrayList<>();
		try {
			getConnection();
			String sql = "select /*+ INDEX_DESC(jspreply jr_no_pk)*/no,bno,id,name,msg,to_char(redate,'yyyy-mm-dd hh24:mi:ss') "
					+ "from jspreply "
					+ "where bno=?";
			// 특별한경우 order by (x) index연습을 많이해아라
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bno);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyVO vo = new ReplyVO();
				vo.setNo(rs.getInt(1));
				vo.setBno(rs.getInt(2));
				vo.setId(rs.getString(3));
				vo.setName(rs.getString(4));
				vo.setMsg(rs.getString(5));
				vo.setDbday(rs.getString(6));
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
	// 3. 댓글 수정 => 댓글 수정 jquery
	public void replyUpdate(int no , String msg) {
		try {
			getConnection();
			String sql = "update jspreply set "
					+ "msg=? "
					+ "where no=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, msg);
			ps.setInt(2, no);
			
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	// 4. 댓글 삭제
	public void replyDelete(int no) {
		try {
			getConnection();
			String sql = "delete from jspreply "
					+ "where no =?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
}
