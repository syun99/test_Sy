package ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OjdbcEx_01 {
	public static void main(String[] args) {
	
		//JDBC 사용방법
		// 1. JDBC 드라이버 로드 (라이브러리다운이선행되어야함)
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ojdbc에 있는 메소드를 사용하기 위한 과정
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		
		//OJDBC사용에 필요한 변수들
		Connection conn = null; //DB연결 객체
		Statement st = null; //SQL구문 수행 객체
		ResultSet rs = null; //조회결과 반환 객체
		
		// 2. DB연결(Connection)
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
			//conn객체가 DB에 접속해서 sql문을 불러오고 결과값을 받아옴
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		// 3. SQL 쿼리 수행
		try {
			st = conn.createStatement(); //SQL 수행 객체
			rs = st.executeQuery("SELECT * FROM emp ORDER BY empno");
			//sql수행 및 결과 반환(ResultSet)
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		
		// 4. 결과 처리 - 불러온 값 출력하기
		try {
			while(rs.next()) { //데이터 없으면 false
				System.out.print(rs.getString("empno")); //getString은 숫자 문자상관없이 가져온다
				System.out.print(", "+rs.getString("ename"));
				System.out.print(", "+rs.getString("job"));
				System.out.print(", "+rs.getString("mgr"));
				System.out.print(", "+rs.getString("hiredate"));
				System.out.print(", "+rs.getString("sal"));
				System.out.print(", "+rs.getString("comm"));
				System.out.println(", "+rs.getString("deptno"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		// 5. 연결 종료
		try {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
/*	  + execute 메소드 종류
 * 		- ResultSet executeQuery(String sql);
 * 			SELECT 쿼리의 결과를 ResultSet으로 반환
 * 			ResultSet은 조회된 모든 행을 담고 있다
 * 			반환객체.next()를 호출할 때마다 다음 행을 가리킨다
 * 
 * 		- int executeUpdate(String sql);
 * 			INSERT, UPDATE, DELETE를 수행할 때 사용
 * 			반환값(int형)은 영향받은 행의 수
 * 			
 * 		- boolean execute(String sql);
			DDL(CREATE, ALTER, DROP)을 수행할 때 사용
			반환값
			true - ResultSet 객체를 반환하는 쿼리일 경우
			false - ResultSet 객체를 반환하지 않는 쿼리일 경우
		
		
*/	
	}
	
	

}
