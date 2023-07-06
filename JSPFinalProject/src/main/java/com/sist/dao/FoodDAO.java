package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;
public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static FoodDAO dao;
	
	// singleton
	public static FoodDAO newInstance() {
		if(dao==null)dao = new FoodDAO();
		return dao;
	}
	
	// ㄱ기능 => 카테고리 생성
	public List<CategoryVO> foodCategoryListData(){
		List<CategoryVO> list = new ArrayList<>();
		try {
			conn = db.getConnection();
			String sql = "select /*+ INDEX_ASC(food_category fc_cno_pk)*/ cno,title,subject,poster "
					+ "from food_category";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CategoryVO vo = new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setPoster(rs.getString(4));
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
	public List<FoodVO> foodLocationFindData(String fd,int page)
	   {
		   List<FoodVO> list=new ArrayList<FoodVO>();
		   try
		   {
			   conn=db.getConnection();
			   String sql="SELECT fno,poster,name,num "
					     +"FROM (SELECT fno,poster,name,rownum as num "
					     +"FROM (SELECT /*+ INDEX_ASC(food_location fl_fno_pk)*/fno,poster,name "
					     +"FROM food_location WHERE address LIKE '%'||?||'%')) "
					     +"WHERE num BETWEEN ? AND ?";
			   int rowSize=12;
			   int start=(rowSize*page)-(rowSize-1);
			   int end=rowSize*page;
			   
			   ps=conn.prepareStatement(sql);
			   ps.setString(1, fd);
			   ps.setInt(2, start);
			   ps.setInt(3, end);
			   ResultSet rs=ps.executeQuery();
			   while(rs.next())
			   {
				   try
				   {
				     FoodVO vo=new FoodVO();
				     vo.setFno(rs.getInt(1));
				     String poster=rs.getString(2);
				     poster=poster.substring(0,poster.indexOf("^"));
				     poster=poster.replace("#", "&");
				     vo.setPoster(poster);
				     vo.setName(rs.getString(3));
				     list.add(vo);
				   }catch(Exception ex) {}
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
	   // 지역별 맛집 => 총페이지 
	   public int foodLoactionTotalPage(String fd)
	   {
		   int total=0;
		   try
		   {
			   conn=db.getConnection();
			   String sql="SELECT CEIL(COUNT(*)/12.0) "
					     +"FROM food_location "
					     +"WHERE address LIKE '%'||?||'%'";
			   ps=conn.prepareStatement(sql);
			   ps.setString(1, fd);
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
	   // 카테고리별 맛집
	   // 1. 카테고리 정보 
	   public CategoryVO foodCategoryInfoData(int cno) {
		   CategoryVO vo = new CategoryVO();
		   try {
			   	conn=db.getConnection();
			   	String sql = "select title,subject from food_category where cno =?";
			   	
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
				db.disConnection(conn, ps);
			}
		   return vo;
	   }
	   // 2. 실제 맛집목록
	   public List<FoodVO> foodCategoryListData(int cno){
		   List<FoodVO> list = new ArrayList<>();
		   try {
			   conn = db.getConnection();
			   String sql = "select fno,cno,poster,name,score,address "
			   		+ "from food_house where cno=?";
			   ps = conn.prepareStatement(sql);
			   ps.setInt(1, cno);
			   ResultSet rs = ps.executeQuery();
			   while(rs.next()) {
				   FoodVO vo = new FoodVO();
				   vo.setFno(rs.getInt("fno"));
				   vo.setCno(rs.getInt("cno")); // 컬럼명도 가능하다...-> Mybatis
				   String poster = rs.getString("poster");
				   poster=poster.substring(0,poster.indexOf("^"));
				   poster = poster.replace("#", "&");
				   vo.setPoster(poster);
				   vo.setName(rs.getString("name"));
				   vo.setScore(rs.getDouble("score"));
				   String addr = rs.getString("address");
				   addr=addr.substring(0,addr.indexOf("지번"));
				   vo.setAddress(addr.trim());
				   
				   list.add(vo);
			   }
			   /*
			    * mvc
			    * === 
			    *  1. dao연결 -> 오라클 연결ㅎ만 연결담당
			    *  2. dao 브라우저 jsp => 연결 -> 결과값 -> model
					-----------------------------------------
			    *  3. 브라우저로 값을 전송 / 요청 Controller (front Controller)
			    *     DispatcherServlet
			    *  4. 전송 받은 데이터 출ㄹ력 :View(JSP)
			    *     ----------------- request, session
			    * ---------------------------------------------------
			    *  1 동작 
			    *    1. 사용자가 요청 : <a> , <form> < ajax>  
			    *      => 지정된 URL 을 사용 (  *.do) 
			    *    2. DispatcherServlet이 요청 내용을 받는다.
			    *       => service()0 , URI -> do 추출
			    *    2-2 요청에 해당하는 메소드를 찾는다.
			    *      -> 어노테이션 (@RequestMapping)
			    *         -------  (index) 찾기
			    *         = method 찾기 @Target(METHOD)
			    *         = class   : Model 만 찾는다.
			    *         = 생ㅇ성자 찾기
			    *         = 매개변수찾기
			    *    2-3 메소드를 찾아서 -> 요청 데이터를 model로 전송 
			    *        => m.invoke(obj, request, response)
			    *        ------------------------------------
			    *          해당 요청 메소드를 호출 
			    *           invoke
			    *           => 메소드명은 자유롭게 만들수 있다.
			    *           DI 의존성 주입
			    *    3. Model 
			    *       => 요청 처리 , 결과값 받기
			    *       => DAO 오라클,json, 데이터 읽기
			    *       => request에 값을 담아주는 역할
			    *    4. dispatcherservlet
			    *       => request에 담긴 데이터를 jSp에 전송
			    *       => RequestDispatcher rd = request.getRequestDispatcher(jsp)
			    *       rd.forward(request,response)
			    *       ----------------------------
			    *        해당 jsp를 찾아서 request 전송역할
			    *    5. JSP 
			    *       => request에 데이터를 출력
			    *       -> EL / JSTL
			    *       
			    *       
			    *    
			    */
			   
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				db.disConnection(conn, ps);
			}
		   return list;
	   }
	   // 맛집 상세보기
	   public FoodVO foodDetailData(int fno) {
		   FoodVO vo = new FoodVO();
		   try {
				conn=db.getConnection();
				String sql = "update food_house set "
						+ "hit = hit+1 "
						+ "where fno=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, fno);
				ps.executeUpdate();
				
				sql ="select fno,cno,name,score,address,phone,type,time,parking,price,menu,poster "
						+ "from food_house where fno=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, fno);
				ResultSet rs =ps.executeQuery();
				rs.next();
				vo.setFno(rs.getInt(1));
				vo.setCno(rs.getInt(2));
				vo.setName(rs.getString(3));
				vo.setScore(rs.getDouble(4));
				vo.setAddress(rs.getString(5));
				vo.setPhone(rs.getString(6));
				vo.setType(rs.getString(7));
				vo.setTime(rs.getString(8));
				vo.setParking(rs.getString(9));
				vo.setPrice(rs.getString(10));
				vo.setMenu(rs.getString(11));
				vo.setPoster(rs.getString(12));
				
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				db.disConnection(conn, ps);
			}
		   return vo;
	   }
	   // 맛집 => 인근 명소 => 레시피 (핵심 가격비교)
	   public List<FoodVO> foodTop7()
	   {
		   List<FoodVO> list=new ArrayList<FoodVO>();
		   try
		   {
			   conn=db.getConnection();
			   String sql="SELECT fno,name,hit,rownum "
					     +"FROM (SELECT fno,name,hit  "
					     +"FROM food_house ORDER BY hit DESC) "
					     +"WHERE rownum<=7";
			   ps=conn.prepareStatement(sql);
			   ResultSet rs=ps.executeQuery();
			   while(rs.next())
			   {
				   FoodVO vo=new FoodVO();
				   vo.setFno(rs.getInt(1));
				   vo.setName(rs.getString(2));
				   vo.setHit(rs.getInt(3));
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
}