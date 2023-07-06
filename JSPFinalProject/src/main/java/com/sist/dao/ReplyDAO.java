package com.sist.dao;

import java.sql.CallableStatement;
import java.sql.*;
import com.sist.vo.*;

import oracle.jdbc.OracleTypes;

import java.util.*;
import com.sist.common.CreateDataBase;

public class ReplyDAO {
	private Connection conn;
	private CallableStatement cs;
	private CreateDataBase db = new CreateDataBase();
	
	private static ReplyDAO dao;
	
	public static ReplyDAO newInstance() {
		if(dao==null)dao=new ReplyDAO();
		return dao;
	}
	
	public List<ReplyVO> replyListData(int type,int cno){
		List<ReplyVO> list = new ArrayList<>();
		try {
			conn = db.getConnection();
			String sql = "{CALL replyListData(?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, cno);
			cs.setInt(2, type);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			
		//	실행
			cs.executeQuery();
			ResultSet rs = (ResultSet)cs.getObject(3);
			while(rs.next()) {
				ReplyVO vo = new ReplyVO();
				vo.setNo(rs.getInt(1));
				vo.setType(rs.getInt(2));
				vo.setCno(rs.getInt(3));
				vo.setId(rs.getString(4));
				vo.setName(rs.getString(5));
				vo.setMsg(rs.getString(6));
				vo.setDbday(rs.getString(7));
				list.add(vo);
				
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, cs);
		}
		return list;
	}
	// 댓글 추가 
	public void replyInsert(ReplyVO vo) {
		try {
			conn = db.getConnection();
			String sql = "{CALL replyInsert(?,?,?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, vo.getType());
			cs.setInt(2, vo.getType());
			cs.setString(3, vo.getId());
			cs.setString(4, vo.getMsg());
			cs.setString(5,vo.getName());
			
			cs.executeQuery();
					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, cs);
		}
	}
}
