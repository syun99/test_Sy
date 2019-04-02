package ojdbc;  

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.Emp;

public class OjdbcEx_04 { //3번에서 결과를 List를 사용하여 출력해보기(DTO라는 클래스형태로)

	// OJDBC 드라이버
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// DB 연결정보
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "scott";
	private static final String PASSWORD = "tiger";

	// OJDBC 객체
	private static Connection conn; // DB연결 객체
	private static Statement st; // SQL 수행 객체
	private static PreparedStatement ps; /// SQL 수행 객체(Statement와 차이 볼 것임)
	private static ResultSet rs;

	public static void main(String[] args) {

		// --- 드라이버 로드 ---

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		// ------------------

		// --- SQL 작성 ---
		String sql = "";
		sql += "SELECT * FROM emp";
		sql += " WHERE job = ?";
		sql += " ORDER BY empno";
		// ---------------

		try {
			// ---DB 연결 ---
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			// PreparedStatement 객체는 생성할 때 SQL 넣고 수행(execute)할 때 SQL 안녛음!
			ps = conn.prepareStatement(sql);

			// SQL의 ?에 데이터 넣기
			ps.setString(1, "SALESMAN"); // 첫번째 '?' 에 SALESMAN이라는 문자이 위 SQL쿼리의 '?' 자리에 들어감.

			// SQL 수행 및 결과 받기
			rs = ps.executeQuery();
			// -------------

			// ---결과 처리---
			
			List<Emp> list = new ArrayList<>();
			
			while (rs.next()) { // 데이터가 없으면 false
				
				Emp emp = new Emp(); //반복시마다 새로운 emp생성
				
				//ResultSet에서 Emp객체에 데이터 담기
				emp.setEmpno(rs.getInt("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setMgr(rs.getInt("mgr"));
				emp.setHiredate(rs.getDate("hiredate"));
				emp.setSal(rs.getInt("sal"));
				emp.setComm(rs.getInt("comm"));
				emp.setDeptno(rs.getInt("deptno"));
				
				//DTO를 List에 담기
				list.add(emp);
			}
			
			// 결과 출력
			for(Emp e : list) {
				System.out.println(e);
			}
			// --DTO를 사용하여 데이터를 묶음으로(객체로써) 처리하는 것이 가능해졌다!
			// --List라는 Object 하나만 넘기면 처리가 가능해졌다 : )

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

}
