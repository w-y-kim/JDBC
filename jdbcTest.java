import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbcTest {

	
	//프로토콜 이름을 멤버변수로 설정하자 
	//DB가 바뀔 때마다 여기 4가지 멤버변수들은 바뀔 수 밖에 없음
	//때문에 실제 개발 시 하드코딩 하지는 않고 XML이나 속성파일[Property FILE]에서 동적으로 읽어씀
	
	private String driver = "oracle.jdbc.driver.OracleDriver"; 
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";//DB이름이 XE인 경우
	private String user = "hr";
	private String password = "hr";
			
			
	public jdbcTest(){
		//TODO 1.JDBC 드라이버 로딩 
		try {
			Class.forName(driver);//클래스를 로딩한다. static으로 만들어진 객체가 생성되서 등록뭔소리
			System.out.println("드라이버 로딩 성공!");
			
			//TODO 2.sql.Connection을 가져와야한다. 
			Connection con = DriverManager.getConnection(url, user, password);
			/*상속관계에서 extends를 쓰지 
			 * 때로는 부모자식 관계 이외에도 공통적인 형질을 갖는 경우 
			 * 인터페이스를 쓰지, 그리고 인터페이스도 상속처럼 다형성이 적용된다. 
			 * class A implements Test {} 
			 * Test t = new A();
			 * 
			 * 여기서 인터페이스 타입으로 받은 이유는 나중에 DB가 바뀌어서 그 드라이버가 바뀌어도 그대로 쓰기 위함 
			 * DriverManager가 오라클에만 적용 가능한 클래스인지는 모르겠으나 여튼 그런 맥락 
			 * */
			/* connection을 멤버변수로 쓰고 안쓰고 차이는?
			 * 로컬변수로 만들어 쓰면 메소드마다 매번 만들어 사용하고 끝나기 전에 con.close()를 해줌
			 * 멤버로 쓰는게 효율적이지 않다는 사실에 주의!
			 * 왜냐하면 con은 사용하지 않으면 끊어야 하는데 계속 물고 있으니까 서버가 못버팀 
			 * 즉 DB의 연결과 해제는 꼭! 로컬로 만들어서 메소드별로 관리하자
			 */
			 
			
			
			System.out.println("DB연결성공");
		} catch (ClassNotFoundException | SQLException e) {
			// 둘 다 컴파일 타입 예외
			e.printStackTrace();
		} // 클래스 로더를 통해 클래스변수(?)를 읽어들이는 역할 
		
	} 
	
	/**여기서 컴파일에 에러가 나면 오라클 설치나 경로 등에 문제가 있는 것  
	 * @param args
	 */
	public static void main(String[] args) {
		new jdbcTest();
	}

}
