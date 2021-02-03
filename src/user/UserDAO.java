package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;		// 데이터베이스 접근 객체
	private PreparedStatement pstmt;		// SQL구문을 실행시키는 기능을 가진 객체
	private ResultSet rs;	// 실행 결과를 저장하는 객체
	
	// Mysql 에 접속
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
//			String dbURL = "jdbc:mysql://localhost/yhcyoon";
//			String dbID = "yhcyoon";
//			String dbPassword = "gmlcks5631!";
			Class.forName("com.mysql.jdbc.Driver");		// Mysql에 접속할 수 있도록 매개체 역할을 해주는 라이브러리를 불러옴
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);	// Connection 객체 생성
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 로그인 메서드
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";	// 해당 userID의 userPassword를 가져옴
		try {
			pstmt = conn.prepareStatement(SQL);	// SQL문을 PreparedStatement 객체에 저장
			pstmt.setString(1, userID);	// ? 에 매개변수 userID 를 setString
			rs = pstmt.executeQuery();	// ResultSet 객체에 쿼리 실행결과 저장
			if (rs.next())	// rs 가 존재하면
				if(rs.getString(1).equals(userPassword)) {	// rs의 값이 매개변수 userPassword와 같다면
					return 1; // 로그인 성공
				} 
				else {
					return 0; // 비밀번호 불일치
			}	
			return -1;	// rs가 존재하지 않으면 아이디가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;	// 데이터베이스 오류
}
	
	// 회원가입 메서드
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";	
		try {
			pstmt = conn.prepareStatement(SQL);	// SQL문을 PreparedStatement 객체에 저장
			pstmt.setString(1, user.getUserID());		// user 자바빈의 getter 메서드를 이용해 pstmt 에 setString
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();		// 실행시 0이상의 값을 리턴
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;	// 데이터베이스 오류
	}
	
}
