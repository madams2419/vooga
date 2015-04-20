package SocialCenter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Driver {

//	public static void main(String[] args) throws Exception {
//		getConnection();
////		createTable();
////		//post();
////		get();
//	}
	public static ArrayList<String> get(String ID, String Pass) throws Exception{
		try{
			Connection con=getConnection();
			PreparedStatement statement=con.prepareStatement("SELECT Login_id,Login_pass FROM Login WHERE Login_id = '"+ID+"' AND Login_pass='"+Pass+"'");
			ResultSet result=statement.executeQuery();	
			ArrayList<String> array=new ArrayList<String>();
			while(result.next()){
				System.out.println("ID: "+result.getString("Login_id"));
				System.out.println("Pass: "+result.getString("Login_pass"));
				array.add(result.getString("Login_pass"));
			}
			if(result.first()==false){
				System.out.println("Invalid UserName or Password");
			}
			return array;
		}catch(Exception e){
			System.out.println("Invalid UserName or Password");
			return null;
		}
		
	}
	
	public static void post() throws Exception{
		Scanner s = new Scanner( System.in );
		System.out.println("Enter your login ID");
		final String ID=s.next();
		System.out.println("Enter your Password");
		final String Password=s.next();
		try{
			Connection con=getConnection();
			PreparedStatement posted = con.prepareStatement("INSERT INTO Login (Login_id, Login_pass) VALUES ('"+ID+"','"+Password+"')");
			//executeQuery (receives info), executeUpdate(manipulates info)
			posted.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}finally{
			System.out.println("insert complete");
		}
		
	}
	public static void createTable() throws Exception{
		try{
			Connection con=getConnection();
			//Getting the sql statement ready to be used
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS Testing(Login_id VARCHAR(30), Login_pass VARCHAR(30),PRIMARY KEY(Login_id))");
			create.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}finally{
			System.out.println("Function complete");
		}
	}
	public static Connection getConnection() throws Exception{
		try{
			
			String driver="com.mysql.jdbc.Driver";
//			String url="jdbc:mysql://localhost:3306/LoginInfo";
			String url="jdbc:mysql://10.190.3.194:3306/LoginInfo";
			//no local host
			String username="guest";
			String password="password";
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
