package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;		// �����ͺ��̽� ���� ��ü
	private PreparedStatement pstmt;		// SQL������ �����Ű�� ����� ���� ��ü
	private ResultSet rs;	// ���� ����� �����ϴ� ��ü
	
	// Mysql �� ����
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
//			String dbURL = "jdbc:mysql://localhost/yhcyoon";
//			String dbID = "yhcyoon";
//			String dbPassword = "gmlcks5631!";
			Class.forName("com.mysql.jdbc.Driver");		// Mysql�� ������ �� �ֵ��� �Ű�ü ������ ���ִ� ���̺귯���� �ҷ���
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);	// Connection ��ü ����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// �α��� �޼���
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";	// �ش� userID�� userPassword�� ������
		try {
			pstmt = conn.prepareStatement(SQL);	// SQL���� PreparedStatement ��ü�� ����
			pstmt.setString(1, userID);	// ? �� �Ű����� userID �� setString
			rs = pstmt.executeQuery();	// ResultSet ��ü�� ���� ������ ����
			if (rs.next())	// rs �� �����ϸ�
				if(rs.getString(1).equals(userPassword)) {	// rs�� ���� �Ű����� userPassword�� ���ٸ�
					return 1; // �α��� ����
				} 
				else {
					return 0; // ��й�ȣ ����ġ
			}	
			return -1;	// rs�� �������� ������ ���̵� ����
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;	// �����ͺ��̽� ����
}
	
	// ȸ������ �޼���
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";	
		try {
			pstmt = conn.prepareStatement(SQL);	// SQL���� PreparedStatement ��ü�� ����
			pstmt.setString(1, user.getUserID());		// user �ڹٺ��� getter �޼��带 �̿��� pstmt �� setString
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();		// ����� 0�̻��� ���� ����
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;	// �����ͺ��̽� ����
	}
	
}
