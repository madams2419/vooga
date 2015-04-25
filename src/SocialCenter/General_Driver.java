/**
 * 
 */
package SocialCenter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.sql.*;

/**
 * @author hojeanniechung
 *
 */
public class General_Driver {

	/**
	 * @param args
	 */
	private static ResultSet result;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static ArrayList<String> get(String statement, String request)throws Exception{
		try{
			Connection con = getConnection();
			PreparedStatement select=con.prepareStatement(statement);
			result=select.executeQuery();
			ArrayList<String> array=new ArrayList<String>();
			while(result.next()){
				array.add(result.getString(request));
			}
			if(result.first()==false){
				array.add("none");
			}
			return array;
		}catch(Exception e){
			System.out.println("Exception");
			return null;
		}
	}
	
	public static void post(String statement) throws Exception{
		try{
			Connection con = getConnection();
			PreparedStatement posted=con.prepareStatement(statement);
			posted.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}finally{
			System.out.println("insert complete");
		}	
	}
	public static Connection getConnection() throws Exception{
		try{
			String driver="com.mysql.jdbc.Driver";
			String url="jdbc:mysql://localhost:3306/LoginInfo";
			//no local host
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
