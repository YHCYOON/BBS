package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BbsDAO {

	private Connection conn;	// 데이터베이스 접근 객체
	private ResultSet rs;
	
	// Mysql 에 접속
	public BbsDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");		// Mysql에 접속할 수 있도록 매개체 역할을 해주는 라이브러리
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 현재 시간을 가져오는 함수
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";	// 데이터베이스 오류
	}
	
	// 게시글 번호를 지정하는 함수
	public int getNext() {
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) +1;
			}
			return 1;	// 현재가 첫번째 게시글인 경우
		}catch (Exception e){
			e.printStackTrace();
		}
		return -1;	// 데이터베이스 오류
	}
	
	// 글을 작성하는 함수
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return -1;	// 데이터베이스 오류
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
