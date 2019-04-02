package ex;

import java.util.List;

import dao.face.EmpDao;
import dao.impl.EmpDaoImpl;
import dto.Emp;

public class EmpEx { //Controller역할
	
	//EmpDao 객체 생성
	private static EmpDao empDao = new EmpDaoImpl();
	
	public static void main(String args[]) {
		
		//DAO를 통한 Emp테이블 전체 조회
		List<Emp> empList = empDao.getList(); 
		//List<Emp>는 Emp만을 데이터타입으로 가지는 List객체임
		//getList()의 리턴값을 저장함
		
		//조회 결과 empList가 존재하면 전체 출력
		if(empList != null) {
			for(Emp e : empList) { //empList의 값을 e에 담아서 순차적으로 출력
				System.out.println(e);
			}
		}
		
//		System.out.println(empList); 이렇게하면 한줄에 모든 데이터값이 다 출력됌.
	}

}
