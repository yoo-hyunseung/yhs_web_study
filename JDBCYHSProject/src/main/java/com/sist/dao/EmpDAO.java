package com.sist.dao;

import java.sql.*;
import java.util.*;

import com.sist.dbconn.CreateDataBase;


public class EmpDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	
	// 기능 설정
	/*
	 * INSERT : 추가
	 *  INSERT INTO 테이블명(컬럼명 ..) VALUES ( 값 ...)
	 *   => default / null 값 허용 필요한 데이터만 출력
	 *  INSERT INTO 테이블명 VALUES(값...)
	 *              ------- 테이블이 가지고 있는 모든 데이터 추가
	 *  => 8개를 추가 (매개변수8개 사용하지않고 -> 클래스 모아서 첨부
	 *  -> 기본 매개변수는 3개 이상을 초과 하지 않게 만든다.
	 *                -------- 배열 / 클래스 
	 * 
	 */
	public void empInsert(EmpVO vo) {
		try {
			conn = db.getConnection();
			// join select;
			// subquery -> select , insert, update, delete
			String sql = "INSERT INTO myEmp VALUES("
					+ "(SELECT NVL(MAX(empno)+1,7000) from myEmp),"
					+ "?,?,?,SYSDATE,?,?,?)";
			ps=conn.prepareStatement(sql);
			// ? 값을 채운다.
//			setString , setInt, setDouble , setDate
//			---------                       -------  자동으로 '' 가 붙는다.
//			table, 컬럼명에는 사용하지 않는다.
//			myBatis -> $, # 
			ps.setString(1, vo.getEname());
			ps.setString(2, vo.getJob());
			ps.setInt(3, vo.getMgr());
			ps.setInt(4, vo.getSal());
			ps.setInt(5, vo.getComm());
			ps.setInt(6, vo.getDeptno());
			ps.executeUpdate(); // commit 수행
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}
	// mgr / sal / deptno
	public List<Integer> empGetMgrData(){
		List<Integer> list = new ArrayList<>();
		// 제네릭스는 반드시 클래스형만 사용 (제네릭스: 면접의 단골)
		try {
			conn=db.getConnection();
			String sql = "select DISTINCT mgr from myEmp";
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1));
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
	public List<Integer> empGetSalData(){
		List<Integer> list = new ArrayList<>();
		// 제네릭스는 반드시 클래스형만 사용 (제네릭스: 면접의 단골)
		try {
			conn=db.getConnection();
			String sql = "select DISTINCT sal from myEmp";
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1));
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
	public List<Integer> empGetDeptnoData(){
		List<Integer> list = new ArrayList<>();
		// 제네릭스는 반드시 클래스형만 사용 (제네릭스: 면접의 단골)
		try {
			conn=db.getConnection();
			String sql = "select DISTINCT deptno from myEmp";
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1));
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
	public List<String> empGetJobData(){
		List<String> list = new ArrayList<>();
		// 제네릭스는 반드시 클래스형만 사용 (제네릭스: 면접의 단골)
		try {
			conn=db.getConnection();
			String sql = "select DISTINCT job from myEmp";
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1));
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
	public List<EmpVO> empListData(){
		List<EmpVO> list = new ArrayList<>();
		try {
			conn=db.getConnection();
			// 첫번째 문제 => 오라클 조인
//			String sql = "SELECT empno,ename,job,TO_CHAR(hiredate,'yyyy-mm-dd'),to_char(sal,'L999,999'),nvl(comm,0),dname,loc,grade "
//					+ "from myemp me join mydept md "
//					+ "on me.deptno=md.deptno "
//					+ "join mygrade ms "
//					+ "on me.sal between ms.losal and ms.hisal "
//					+ "order by empno desc";
			String sql ="SELECT empno,ename,job,TO_CHAR(hiredate,'yyyy-mm-dd'),to_char(sal,'L999,999'),nvl(comm,0),dname,loc,grade "
					+ "from myemp me, mydept md, mygrade mg "
					+ "where me.deptno=md.deptno "
					+ "and me.sal between mg.losal and mg.hisal "
					+ "order by empno desc";
			ps= conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				EmpVO vo = new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setDbsal(rs.getString(5));
				vo.setComm(rs.getInt(6));
				vo.getDvo().setDname(rs.getString(7));
				vo.getDvo().setLoc(rs.getString(8));
				vo.getSvo().setGrade(rs.getInt(9));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	// 상세보기 int empno 사용자 전송 -> ?empno=7788;
	public EmpVO empDetailData(int empno) {
		EmpVO vo = new EmpVO();
		try {
			conn=db.getConnection();
			// 2번째 문제 -> join -> 스칼라 서브쿼리로
//			String sql ="select empno,ename,job,nvl(mgr,0),to_char(hiredate,'yyyy-mm-dd'),to_char(sal,'L999,999'),nvl(comm,0),dname,loc,grade "
//					+ "from myemp me,mydept md,mygrade mg "
//					+ "where me.deptno=md.deptno and me.sal between mg.losal and mg.hisal "
//					+ "and empno=?";
			String sql = "select empno,ename,job,nvl(mgr,0),to_char(hiredate,'yyyy-mm-dd'),to_char(sal,'L999,999'),nvl(comm,0),"
					+"(SELECT dname FROM mydept WHERE me.deptno = deptno) AS dname,"
					+"(SELECT loc FROM mydept WHERE me.deptno = deptno) AS loc,"
					+"(SELECT grade FROM mygrade WHERE me.sal BETWEEN losal AND hisal) AS grade "
					+"FROM myemp me WHERE empno=?";
			ps=conn.prepareStatement(sql);
			
			// ? 값을 채운다
			ps.setInt(1, empno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setEmpno(rs.getInt(1));
			vo.setEname(rs.getString(2));
			vo.setJob(rs.getString(3));
			vo.setMgr(rs.getInt(4));
			vo.setDbday(rs.getString(5));
			vo.setDbsal(rs.getString(6));
			vo.setComm(rs.getInt(7));
			vo.getDvo().setDname(rs.getString(8));
			vo.getDvo().setLoc(rs.getString(9));
			vo.getSvo().setGrade(rs.getInt(10));
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	
	// 수정 데이터 읽기
	public EmpVO empUpdateData(int empno) {
		EmpVO vo = new EmpVO();
		try {
			conn = db.getConnection();
			String sql = "select empno,ename,job,mgr,sal,comm,deptno "
					+ "from myemp "
					+ "where empno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empno);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setEmpno(rs.getInt(1));
			vo.setEname(rs.getString(2));
			vo.setJob(rs.getString(3));
			vo.setMgr(rs.getInt(4));
			vo.setSal(rs.getInt(5));
			vo.setComm(rs.getInt(6));
			vo.setDeptno(rs.getInt(7));
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
}
