import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class jdbcTest2 {

	// 프로토콜 이름을 멤버변수로 설정하자
	// DB가 바뀔 때마다 여기 4가지 멤버변수들은 바뀔 수 밖에 없음
	// 때문에 실제 개발 시 하드코딩 하지는 않고 XML이나 속성파일[Property FILE]에서 동적으로 읽어씀

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";// DB이름이 XE인 경우
	private String user = "hr";
	private String password = "hr";

	/**
	 * 생성자에서 Driver 로딩만 해주면 된다. 아래 코드는 Driver와 Connection의 관계를 설명하기 위해 메소드처럼 적은
	 * 것이다.
	 */
	public jdbcTest2() {
//		Connection con = null;//

		
			// TODO 1단계_JDBC 드라이버 로딩
			// 드라이버 로딩 : arg의 경로의 라이브러리 클래스인 OracleDriver 클래스파일을 로딩
			// 클래스 로더를 통해 OrcaleDriver의 클래스변수(static)를 읽어들이는 역할
			try {
				Class.forName(driver);
				System.out.println("드라이버 로딩 성공!");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		 
	}

	/**
	 * SELECT 메소드 DB에 접근해 sql을
	 * 
	 * @param id_파라미터는
	 *            찾을 데이터의 id를 직접 지정하는 경우 사용
	 * @return Member객체_DB(XE)의 Member Table의 VO를 틀로해서 생성한 인스턴스(힙메모리에 로딩)
	 */
	public Member selectMember(String id) {
		Member m = null;
		Connection con = null;// 로컬 초기
		try {
			
			// TODO 1단계_DB연결
			con = DriverManager.getConnection(url, user, password);// 컨넥셕을 가져옴
			System.out.println("DB접속 성공");
			
			// TODO 2단계_statement 객체 생성
			// statement는 Connection정보를 바탕으로 Query의 
			Statement stmt = con.createStatement();
			String sql = "select * from member where id = " + "'" + id + "'"; // 동적제어

			// TODO 3단계_.sql 실행[select]해서 그 결과를 ResultSet에 담기 
			// 데이터조작어[DML]인 inset , delete, update 는 executeUpdate() sql 반환값은 int 
			// 데이터질의어[DQL]인 select는 executeQuery()를 사용
			// 그리고 결과집합[ResultSet]이라는 참조변수에 담음
			ResultSet rs = stmt.executeQuery(sql);

			// TODO 4단계_DB에서(ResultSet이라는 객체를 통해) 레코드불러오기 
			while (rs.next()) {
				// rs.getString(1);
				// String id2 = rs.getString(1);//로컬중복
				String name = rs.getString("name");
				String password = rs.getString("password");
				int age = rs.getInt("age");
				m = new Member(id, name, password, age);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();// 할당이 되어야지만 닫아야지 없는데 닫으면 null 에러
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return m;
	}

	public ArrayList<Member> selectAllMember() {
		ArrayList<Member> allMembers = new ArrayList<>();

		Connection con = null;// 로컬 초기

		try {
			con = DriverManager.getConnection(url, user, password);// 컨넥셕을 가져옴

			Statement stmt = con.createStatement();
			String sql = "select * from member"; // id만
			ResultSet rs = stmt.executeQuery(sql);
			// 결과 받아옴

			while (rs.next()) {

				String id = rs.getString("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				int age = rs.getInt("age");

				Member m = new Member(id, name, password, age);
				allMembers.add(m);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();// 할당이 되어야지만 닫아야지 없는데 닫으면 null 에러
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return allMembers;
	}

	
	public void insertMember(Member m){
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url,user,password);
			String sql = "insert into member values(?,?,?,?)"; //물음표로 남겨둔다 
			PreparedStatement pstmt = con.prepareStatement(sql); // Statement와 다르게 미리 sql을 작성해서 바로 넣음 
			
			//m객체에서 값 불러와 pstmt에 저장 
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getPassword());
			pstmt.setString(3, m.getName());
			pstmt.setInt(4, m.getAge());
			
			int row = pstmt.executeUpdate();//반환값은 int이다 
			System.out.println(row +"개 레코드 삽입");
			
		} catch (SQLException e) {
		} finally {
			try {
				if(con != null) con.close();
			} catch (Exception e2) {
			}
		}
		
	}
	
	public void deleteMember(String id){
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url,user,password);
			String sql = "delete member where id =?"; //물음표로 남겨둔다 
			PreparedStatement pstmt = con.prepareStatement(sql); // Statement와 다르게 미리 sql을 작성해서 바로 넣음 
			pstmt.setString(1, id);
			
			int row = pstmt.executeUpdate();
			System.out.println(row+"개 삭제완료");
			
		} catch (SQLException e) {
		} finally {
			try {
				if(con != null) con.close();
			} catch (Exception e2) {
			}
		}
		
	}
	public void updateMember(Member m){
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url,user,password);
			String sql = "Update member set PASSWORD=?, Name=?, AGE=? WHERE ID=?"; //물음표로 남겨둔다 
			PreparedStatement pstmt = con.prepareStatement(sql); // Statement와 다르게 미리 sql을 작성해서 바로 넣음 
			
			//m객체에서 값 불러와 pstmt에 저장 
			pstmt.setString(1, m.getPassword());
			pstmt.setString(2, m.getName());
			pstmt.setInt(3, m.getAge());
			pstmt.setString(4, m.getId());
			
			int row = pstmt.executeUpdate();//반환값은 int이다 
			System.out.println(row +"개 레코드 갱신");
			
		} catch (SQLException e) {
		} finally {
			try {
				if(con != null) con.close();
			} catch (Exception e2) {
			}
		}
		
	}
	
	public static void main(String[] args) {
		jdbcTest2 test = new jdbcTest2();
		
		//SELECT 시 (find)
//		Member m = test.selectMember("2");
//		System.out.println(m);

		//SELECT 시 (showAll)
//		ArrayList<Member> alm = test.selectAllMember();
//		for (Member e : alm) {
//			System.out.println(e);
//		}

		//INSERT 시 (등록)
//		Member m = new Member("abcd","김자바","1234",33); 
//		test.insertMember(m);

		//DELETE 시 (삭제)
//		test.deleteMember("1");

		//UPDATE 시 (수정)
		Member m = new Member("abcd","박자바","1234",33); 
		test.updateMember(m);
	}

}
