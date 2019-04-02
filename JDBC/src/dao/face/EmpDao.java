package dao.face; //구현된 메소드의 목록을 한눈에 보기위해 dao를 인터페이스와 구현클래스로 나누었다.

import java.util.List;

import dto.Emp;

public interface EmpDao {
	
	//전체 조회
	public List<Emp> getList();
	
	
	

}
