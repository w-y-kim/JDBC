
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
		} catch (ClassNotFoundException e) {
			// 컴파일 타입 예외
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
