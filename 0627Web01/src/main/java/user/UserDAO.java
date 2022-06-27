package user;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	//Connection 데이터 베이스 접근하게 해주는 하나의 객체
	private Connection conn;
	private PreparedStatement pstmet;
	
	//하나의 정보를 담을 수 있는 하나의 객체
	private ResultSet rs;
	
	// ctrl + shift + o 를 눌러 외부 라이브러리를 넣어줍니다.
	
	//생성자를 만들어줍니다.
	public UserDAO() { //실제로 mysql에 접속할 수 있도록 도와줌
		try {
			//localhost:3306은 우리 컴퓨터에 설치된 mysql 서버 자체를 의미하고
			//bbs는 우리가 만든 테이블 이름입니다.
			String dbURL = "jdbc:mysql://localhost:3306/BBS?serverTimezone=UTC";
			String dbID = "root"; //db 계정
			String dbPassword = "system123"; //db 비밀번호
			
			//driver는 mysql에 접속할 수 있도록 도와주는 하나의 라이브러리 매개체
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); //오류가 무엇인지 출력
		}
	}
	
	
	
	
	
	//로그인 기능 수행 함수. 로그인 화면에서 유저가 아이디 비번을 치고 밑의 로그인 버튼을 눌렀을 때 실행
	public int login(String userId, String userPassword) { //아이디와 비번을 받아옴
		//실제로 db에 입력할 SQL문
		String SQL = "SELECT userPassword FROM TABLE_USER WHERE userID= ?";
		//실행할 쿼리. 해당 아이디의 비밀번호를 가져온다.
		
		try {
			 pstmet = conn.prepareStatement(SQL); //문자열 쿼리를 pstmt에 대입
			 pstmet.setString(1, userId); //첫번째 물음표에 userID값 대입
			 //프로그래밍 언어에서 인덱스는 0부터 시작이지만 쿼리에서는 1부터 시작이다.
			
			 rs = pstmet.executeQuery(); // 쿼리 실행후 결과 받기
			 if(rs.next()) { //결과의 리스트를 받았는데 다음행의 데이터가 있으면
				if(rs.getString(1).equals(userPassword)) {
					//남은 것의 첫번째의 값이 login함수를 호출할 떄 전달받은 비밀번호와 같은지 검사
					return 1; //로그인 성공
				}else {
					return 0; //로그인 실패
				}
			}
			return -1; //아이디가 없음
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	}

}
