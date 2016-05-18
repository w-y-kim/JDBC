
/**member테이블과 1:1 대응하는 VO
 * @author user
 * 컬럼에 해당하는 것을 멤버변수로 만들어줌 
 * @method 겟셋,투스트링,생성자 
 */
public class Member {
//TODO 3. VO만들기
	private String id;

	private String name;
	private String password;
	private int age; 
	
	public Member(String id, String name, String password, int age) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", password=" + password + ", age=" + age + "]";
	}

	
	
}
