import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbcTest {

	// 프로토콜 이름을 멤버변수로 설정하자
	// DB가 바뀔 때마다 여기 4가지 멤버변수들은 바뀔 수 밖에 없음
	// 때문에 실제 개발 시 하드코딩 하지는 않고 XML이나 속성파일[Property FILE]에서 동적으로 읽어씀

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";// DB이름이 XE인 경우
	private String user = "hr";
	private String password = "hr";

	public jdbcTest() {
		// TODO 1.JDBC 드라이버 로딩
		try {
			Class.forName(driver);// 클래스를 로딩한다. static으로 만들어진 객체가 생성되서 등록뭔소리
			System.out.println("드라이버 로딩 성공!");

			// TODO 2.sql.Connection을 가져와야한다.
			Connection con = DriverManager.getConnection(url, user, password);

			/*
			 * 하나의 드라이버 로딩 커넥션 받아서 커넥션으로부터 질의문을 던져온다 (by Connection 객체로부터
			 * Statement 객체를 만들어서, Connection의 createStatement()메소드 사용)
			 */
			System.out.println("DB연결성공");
		} catch (ClassNotFoundException | SQLException e) {
			// 둘 다 컴파일 타입 예외
			e.printStackTrace();
		} // 클래스 로더를 통해 클래스변수(?)를 읽어들이는 역할

	}

	/**
	 * 조회하고 조회된 결과를 반환하는 메소드
	 * 
	 * @param id
	 *            테이블이 만들어져야하고 테이블과 1:1 관계에 있는 VO를 만들어야 한다.
	 */
	public Member selectMember(String id) {
		Member m = null;
		Connection con = null;// 로컬 초기
		try {
			// TODO 4.DB연결
			con = DriverManager.getConnection(url, user, password);// 컨넥셕을 가져옴
			
			// TODO 5.statement 객체 생성
			Statement stmt = con.createStatement();
			String sql = "select * from member where id = " + " ' " + id + " ' "; // id만
																					// 동적으로

			// TODO 6.sql 실행
			/*
			 * dml = inset , delete, update 는 executeUpdate() sql 반환값은 int , 예를
			 * 들어 insert 했을 때 생성된 레코드의 수
			 * select는 executeQuery()를 사용, 결과집합[ResultSet]이라는 객체의 형태로 
			 */
			
			ResultSet rs = stmt.executeQuery(sql);
			
			
			
			
			
			// con.close();//다 쓰면 무조건 항상 절대! close를 해줘야함 , 자원반납안하면 나중에 error가
			// 발생(예외도 아니고!) ,에러터지면 여기 실행안됨
		} catch (SQLException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();// 할당이 되어야지만 닫아야지 없는데 닫으면 null 에러
			} catch (SQLException e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}

		return m;
	}

	/**
	 * 여기서 컴파일에 에러가 나면 오라클 설치나 경로 등에 문제가 있는 것
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new jdbcTest();
	}

}
