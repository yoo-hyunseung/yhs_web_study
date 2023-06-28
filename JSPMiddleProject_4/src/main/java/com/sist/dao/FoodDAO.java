package com.sist.dao;
import java.sql.*;
import java.util.*;
// 연결하는 장소
/*
 * 연결 
 * 	1. 드라이버 등록 오라클 연결해주는 라이브러리 ojdbc8.jar 
 * OracleDriver -> 메모리 할당
 * 
 * 	2. 오라클 연결
 * 		Connection
 * 	3. SQL 문장 전송
 * 		PreparedStatement
 * 	4. SQL문장 실행 요청
 * 		= executeUpdate() = insert update delete 실행시
 * 		  --------------- COMMIT 자동으로 수행(AUTO COMMIT)
 * 		= executeQuery() = select 실행시
 * 		  --------------- 결과 값을 가지고 온다.
 * 						  ------
 * 						  ResultSet
 * 	5. 닫기
 * 		= 생성 반대 순서로 닫는다
 * 		rs.close() ps.close() conn.close()
 * 
 * 			ResultSet
 * 			String sql = "SELECT id,name,sex,age ";
 * 			--------------------------------------------
 * 	           id        name         sex       age 
 * 			--------------------------------------------
 *            aaa        홍길동         남자        20    | first()  => next()
 *                                                        위치변경      위치변경후 데이터 읽기
 *       getString(1),getString(2),getString(3),getInt(4)
 *       getString("컬럼명")
 *       getString("id")                                               
 *          --------------------------------------------
              bbb        심청이         여자        27
         getString(1),getString(2),getString(3),getInt(4)
            --------------------------------------------
              ccc        박문수         남자        23    | last() => previous()
         getString(1),getString(2),getString(3),getInt(4)
            --------------------------------------------
             | 커서 위치 
 *          
 *          -------------------------------오라클 연결(Servlet -> JSP)
 */
public class FoodDAO {
	// 기능 insert -> 데이터 수집 (파일)
	//
	private Connection conn; //오라클 연결하는 객체 (데이터베이스 연결)
	private PreparedStatement ps; // sql문장 전송  / 결과값 받기
	private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	// MySql => jdbc:mysql://localhost/mydb
	
	private static FoodDAO dao; // 싱글턴 패턴
	// DAO객체를 한개만 사용이 가능하게 만든다.
	
	// 드라이버 설치 -> 소프트웨어(메모리할당요청) Class.forName()
	// 클래스의 정보를 전송
	// 드라이버 설치는 한번만 수행
	public FoodDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 데이터베이스(오라클) 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url,"hr","1235");
			// -> 오라클 전송 : conn hr/happy
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 데이터베이스(오라클) 연결 종료
	public void disConnection() {
		try {
			if(ps!=null)ps.close();
			if(conn!= null)conn.close();
			// 오라클 전송 : exit
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// DAO객체를 1개만 생성해서 사용 => 메모리 누수현상을 방지(싱글톤 패턴)
	// 싱글톤 / 팩토리 -> 면접(스프링 : 패턴 8개)
	public static FoodDAO newInstance() {
		// newInstance() , getInstance() -> 싱글톤 패턴
//		Calendar cal = Calendar.getInstance(); -- 싱글톤 패턴
		if(dao==null) {
			dao=new FoodDAO();
		}
		return dao;
	}
	// ================== 기본 셋팅 (모든 dao)
	// 기능
	// 1. 데이터 수집(INSERT) return 이 없다.
	/*
	 * Statement => SQL -> 생성과 동시에 데이터 추가
	 *                "'"+name......
	 * PreparedStatement => 미리 SQL문장을 만들고 나중에 값을 채운다.
	 * CallableStatement => PL/SQL Procedure 호출
	 * */
	public void foodCategoryInsert(CategoryVO vo) {
		try {
			// 1. 연결 
			getConnection();
			
			// 2. sql 문장
			String sql ="INSERT INTO food_category VALUES("
					+ "fc_cno_seq.nextVal, ?,?,?,?)"; // '편하게 실행전 나중에 값을 채움
			/*            물음표의 순서       1 2 3 4 -> index ps.setString(index,)
			 * "'"+vo.getTitle()+"'"
			 */
			
			// 3. sql문장 전송
			ps = conn.prepareStatement(sql);
			// 3-1. ?에 값을 채운다
			ps.setString(1, vo.getTitle()); //"'"+vo.getTitle()+"'" 와 같은 뜻 
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getPoster());
			ps.setString(4, vo.getLink());
			// 단점 -> 번호가 잘못되면 오류 발생, 데이터형이 다르면 오류발생
			// IN, OUT ~
			
			// 4. sql문장 실행 명령 => sql문장을 작성하고 -> 
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); // 에러확인 
		}finally {
			disConnection(); // 오라클 연결 해제
		}
	}
	//1-1. 실제 맛집 정보 저장
	/*
	 * 	fno NUMBER, -- pk , 자동증가번호설정
		cno NUMBER, -- fk
		name varchar2(100) CONSTRAINT fh_name_nn NOT NULL,
		score number(2,1),
		address varchar2(300) CONSTRAINT fh_addr_nn NOT NULL,
		phone varchar2(20) CONSTRAINT fh_phone_nn NOT NULL,
		type varchar2(30) CONSTRAINT fh_type_nn NOT NULL,
		price varchar2(30),
		parking varchar2(30),
		menu clob,
		good NUMBER,
		soso NUMBER,
		bad NUMBER,
		poster varchar2(4000) CONSTRAINT fh_poster_nn NOT NULL,
	 */
	public void foodDataInsert(FoodVO vo) {
		try {
			// 1. 오라클 연결
			getConnection();
			
			// 2. sql  문장
			String sql="INSERT INTO food_house VALUES("
				     +"fh_fno_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			// 3. 오라클 전송
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getCno());
			ps.setString(2, vo.getName());
			ps.setDouble(3, vo.getScore());
			ps.setString(4, vo.getAddress());
			ps.setString(5, vo.getPhone());
			ps.setString(6, vo.getType());
			ps.setString(7, vo.getPrice());
			ps.setString(8, vo.getParking());
			ps.setString(9, vo.getTime());
			ps.setString(10, vo.getMenu());
			ps.setInt(11, vo.getGood());
			ps.setInt(12, vo.getSoso());
			ps.setInt(13, vo.getBad());
			ps.setString(14, vo.getPoster());
			
			
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection(); // 오라클 닫기
		}
	}
	// 2. SELECT -> 전체 데이터 읽기 => 30개 (1개당 -> CategoryVO)
	// Collection , 배열 => 브라우저 30개 전송
	// 브라우저 <===> 오라클 (X) 
	// 브라우저 <==> 자바 <==> 오라클 => Spring Cloud
	// vo, list<vo>
	// Collection , 메소드 제작, 
	public List<CategoryVO> foodCategoryData(){
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		try {
			// 1.오라클 연결
			getConnection();
			
			// 2. sql 문장
			String sql = "SELECT cno,title,subject,poster,link "
					+ "FROM food_category";
			
			// 3.오라클 전송
			ps = conn.prepareStatement(sql);
			
			// 4.결과값 받기
			ResultSet rs = ps.executeQuery();
			
			// rs의 값을 list에 저장
			while(rs.next()) {
				CategoryVO vo = new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				String poster = rs.getString(4);
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setLink("https://www.mangoplate.com"+rs.getString(5));
				list.add(vo);
			}
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			//오라클 닫기
			disConnection();
		}
		return list;
	}
	// 카테고리에 해당되는 데이터를 읽는다.
	public CategoryVO foodCategoryInfoData(int cno) {
		CategoryVO vo = new CategoryVO();
		try {
			getConnection();
			String sql = "select title,subject from food_category "
					+ "where cno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setTitle(rs.getString(1));
			vo.setSubject(rs.getString(2));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}
	// 카테고리에 해당되는 맛집 읽기
	public List<FoodVO> foodCategoryListData(int cno){
		List<FoodVO> list = new ArrayList<>();
		try {
			getConnection();
			String sql = "select fno,name,score,poster,address,phone, type from "
					+ "food_house where cno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cno);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FoodVO vo = new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setScore(rs.getDouble(3));
//				vo.setPoster(rs.getString(4));
//				vo.setAddress(rs.getString(5));
				vo.setPhone(rs.getString(6));
				vo.setType(rs.getString(7));
				String poster = rs.getString(4);
				poster = poster.substring(0,poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				String address = rs.getString(5);
				address = address.substring(0,address.indexOf("지번"));
				vo.setAddress(address);
				list.add(vo);
			}
			rs.close();
//					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}
	// 3. 상세보기 -> WHERE
	public FoodVO foodDetailData(int fno) {
		FoodVO vo = new FoodVO();
		try {
			getConnection();
			String sql = "select fno,cno,name,score,poster,address,"
					+ "type,parking,time,menu,phone,price "
					+ "from food_house"
					+ " where fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setCno(rs.getInt(2));
			vo.setName(rs.getString(3));
			vo.setScore(rs.getDouble(4));
			vo.setPoster(rs.getString(5));
			vo.setAddress(rs.getString(6));
			vo.setType(rs.getString(7));
			vo.setParking(rs.getString(8));
			vo.setTime(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setPhone(rs.getString(11));
			vo.setPrice(rs.getString(12));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}
	
	
}
