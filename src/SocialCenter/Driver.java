package SocialCenter;
import java.sql.*;

public class Driver {

	public static void main(String[] args) throws Exception {
		getConnection();
	}
	
	public static Connection getConnection() throws Exception{
		try{
			String driver="com.mysql.jdbc.Driver";
			String url="jdbc:mysql://localhost:3306/LoginInfo";
			//no local host
			//String url="jdbc:mysql://10.190.208.163:3306/LoginInfo";
			String username="root";
			String password="Tkfkdgo<3";
			Class.forName(driver);
			
			Connection conn=DriverManager.getConnection(url,username,password);
			System.out.println("Connected");
			return conn;
		}catch(Exception e){
			System.out.println(e);
		}	
		return null;
	}
}
