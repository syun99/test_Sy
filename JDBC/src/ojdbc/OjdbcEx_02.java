package ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OjdbcEx_02 {

	// OJDBC 드라이버
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// DB 연결 정보
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "scott";
	private static final String PASSWORD = "tiger";

	// OJDBC 객체
	private static Connection conn = null; // DB연결객체
	private static Statement st = null; // SQL수행객체
	private static ResultSet rs = null; // 조회 결과 객체

	public static void main(String[] args) {

		// --- 드라이버 로드 ---
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		// ------------------

		// --- SQL 작성 --- , 오라클에서 작성하는 구문과 동일하게 작성해야 하기 때문에 첫줄 다음부터는 앞에 띄어쓰기를 넣어주자
		// (띄어쓰기 안넣어주면 모든 구문을 붙여쓰는 것과 같이 인식되니까!)
		String sql = "";
		sql += "CREATE TABLE userTest(";
		sql += " idx NUMBER ";
		sql += " CONSTRAINT PK_USER_TEST PRIMARY KEY,";
		sql += "	name VARCHAR2(50) NOT NULL,";
		sql += "	phone VARCHAR2(30) NOT NULL )"; // 다 이어지는 구문이라 중간에 띄어쓰기 해줘야 함

		String sql2 = "";
		sql2 += "CREATE SEQUENCE seq_usertest";
		sql2 += " INCREMENT BY 1";
		sql2 += " START WITH 1";

		// ---------------

		// ---DB 연결 ---

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			// ---SQL 수행 ---
			st = conn.createStatement();

			st.execute(sql);
			st.execute(sql2);
			// --------------

			// --- 결과처리 ---
			// TABLE 생성 확인
			rs = st.executeQuery("SELECT count(*) FROM tabs" // db에서 테이블이 생성되면 tabs라는 테이블에 테이블명이 추가됌. 이를 이용해 확인
					+ " WHERE table_name=upper('usertest')"); // 내가 만든 테이블 있나 갯수확인

			rs.next(); // 조회결과 첫 행 찾기. rs는 참조값을 갖고있기때문에이거안하면 주소값만 반환함.

			if (rs.getInt(1) > 0) { // 위의 sql쿼리 결과 값 중 '1'을 가진 것 확인 (무조건 1이 나올 것임)
									// rs.getInt("COUNT(*)"); getInt는 매개변수 int, String을받기때문에 이렇게 조회해도 가능

				System.out.println("테이블 생성 완료");

			}
			// SEQUENCE 생성 확인
			rs = st.executeQuery(
					"SELECT count(*) FROM user_sequences" 
				  + " WHERE sequence_name = upper('seq_usertest')");

			rs.next();

			if (rs.getInt(1) > 0) { // 테이블 생성 확인 방법과 같은방식
				System.out.println("시퀀스 생성 완료");
			}
			// -------------

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// ---자원 해제 ---
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
				// --------------
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

} //JAVA Application으로 Run ! 
	//==> 실행 후 DBMS에서 테이블이랑 시퀀스 COUNT결과가 1이 되었는지 확인
