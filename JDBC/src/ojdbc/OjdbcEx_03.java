package ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OjdbcEx_03 {

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

			// -------------

			// ---SQL수행 --- ** Statement와 PreparedStatement차이 확인!
			// Statement는 ?가 없는 쿼리에 보통 사용되고 PreparedStatement는 ?가 있는 쿼리 없는쿼리 둘다 사용가능
			// => 앞으로 PreparedStatement를 계속 사용하자!
			// ? : 변하는 값

			/*
			 * Statement객체는 생성할 때 SQL 안 넣고 수행(execute)할 때 SQL 적용함 st =
			 * conn.createStatement(); rs = st.executeQuery(sql);
			 */

			// PreparedStatement 객체는 생성할 때 SQL 넣고 수행(execute)할 때 SQL 안녛음!
			ps = conn.prepareStatement(sql);

			// SQL의 ?에 데이터 넣기
			ps.setString(1, "SALESMAN"); // 첫번째 '?' 에 SALESMAN이라는 문자이 위 SQL쿼리의 '?' 자리에 들어감.

			// SQL 수행 및 결과 받기
			rs = ps.executeQuery();
			// -------------

			// ---결과 처리---
			
			while (rs.next()) { // 데이터가 없으면 false
				System.out.print(rs.getString("empno"));
				System.out.print(", " + rs.getString("ename"));
				System.out.print(", " + rs.getString("job"));
				System.out.print(", " + rs.getString("mgr"));
				System.out.print(", " + rs.getString("hiredate"));
				System.out.print(", " + rs.getString("sal"));
				System.out.print(", " + rs.getString("comm"));
				System.out.println(", " + rs.getString("deptno"));
			}
			// -------------

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
