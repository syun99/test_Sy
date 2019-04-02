package dao.impl;//db의 데이터에 접근하기 위한 객체. sql문을 수행한다.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.face.EmpDao; //서로다른 패키지에 있는 클래스, 인터페이스를 상속하기 때문에
import dto.Emp;

public class EmpDaoImpl implements EmpDao {

	// OJDBC 드라이버 (jdbc : java database connectivity, 서로다른 데이터베이스(oracle, mysql)에 접근하도록 해주는 하나의 인터페이스
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// DB 연결정보
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	//jdbc:oracle:thin:@부분은 동일하게 사용하고 localhost는 위치를 말한다. 
	//다른PC이거나 할 경우는 IP를 입력해 주고 1521은 해당포트이며 xe는 오라클의 SID이다. 자신에 맞게 입력해준다.
	
	private static final String USERNAME = "scott";
	private static final String PASSWORD = "tiger";

	// OJDBC 객체
	private static Connection conn; // DB연결 객체
	//어떤 SQL 문장을 실행시키기 전에 우선 Connection 객체가 있어야 한다. 
	//Connection 객체는 특정 데이터 원본과 연결된 커넥션을 나타내고, 
	//특정한 SQL 문장을 정의하고 실행시킬 수 있는 Statement 객체를 생성할 때도 Connection 객체를 사용한다.

	private static Statement st; // SQL 수행 객체
	private static PreparedStatement ps; /// SQL 수행 객체(Statement와 차이 볼 것임) ->보통얘를더많이씀
	//Connection객체에 의해 프로그램에 리턴되는 객체가 있는데, 그 객체에 의해 구현되는 일종의 메소드 집합을 정의한다. 

	private static ResultSet rs;
	//SQL 문 중에서 Select 문을 사용한 질의의 경우 성공 시 결과물로 ResultSet을 반환한다. (행단위로 데이터 가지고 있음)
	//ResultSet은 SQL 질의에 의해 생성된 테이블을 담고 있다. 또한 ResultSet 객체는 '커서(cursor)' 라고 불리는 것을 가지고 있다.
	//커서는 초기에 첫 번째 행의 직전을 가리키도록 되어 있는데, ResultSet 객체의 next() 메소드를 사용하면 다음 위치로 커서를 옮길 수 있다.


	public EmpDaoImpl() {

		try {
			// --- 드라이버 로드 ---
			Class.forName(DRIVER); //driver를 메모리에 로드하고 객체를 생성한다. drivermanager에 등록됌.
			// ---DB 연결 ---
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); 
			//driverManager로 위에서 로드한 드라이버를 Connection하여 사용할 수 있다.
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	@Override
	public List<Emp> getList() {// 인터페이스 상속으로 인해 반드시 구현해야 하는 메소드
		//<Emp>는 getList내에서 사용하는 데이터타입을 emp클래스에 있는 데이터타입으로 일반화시킨다.(다른데이터 타입사용 막음, 생성도불가, 강제형변환필요x)
		//**제네릭은 메소드, 클래스 생성시 사용될 수 있음!
		// ---SQL 작성 ---
		String sql = "";
		sql += "SELECT * FROM emp ORDER BY empno";
		// --------------

		// --쿼리 결과 List(반환할 값)
		List<Emp> empList = new ArrayList<>(); 
		//제네릭사용으로 List<Emp>타입 변수만 생성가능

		try {
			// ---PreparedStatement 처리 ---
			ps = conn.prepareStatement(sql); //conn에 의해 반환되는 객체의 메소드를 구현하기 위한 preparedStatement
			//밑의 execute같은 ps의 메소드 사용하기 위한 과정임
			rs = ps.executeQuery(); // 쿼리 수행(PreparedStatement가 제공하는 쿼리수행하는 메소드임)

			// ---ResultSet 처리---
			while (rs.next()) {//resultSet의 next()메소드는 값이 존재하면 true, 없으면 false를 반환한다.
				Emp emp = new Emp(); //매 반복마다 새로운 Emp객체를 생성하여 한 행의 값을 저장한다.

				// ResultSet에서 Emp객체에 데이터 담기
				emp.setEmpno(rs.getInt("empno")); //rs에 존재하는 empno 컬럼의 데이터값을 가져와 Emp테이블의 Empno컬럼값을 변경한다. 
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setMgr(rs.getInt("mgr"));
				emp.setHiredate(rs.getDate("hiredate"));
				emp.setSal(rs.getInt("sal"));
				emp.setComm(rs.getInt("comm"));
				emp.setDeptno(rs.getInt("deptno"));

				// DTO를 List에 담기
				empList.add(emp); // empList는 ArrayList라서 차곡차곡 데이터가 쌓인다~
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			try {
				if(rs!= null) rs.close();
				if(ps!= null) ps.close();
				
				//**주의  : Connection은 close하면 안됌! ! 
				
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		// 결과반환
		return empList;

	}

}
