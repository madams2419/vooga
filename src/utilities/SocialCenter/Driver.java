package utilities.SocialCenter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Driver {


//	public static void main(String[] args) throws Exception {
//		getConnection();
//		createTable();
//		//post();
//		//get();
//	}
//	
	public static ArrayList<String> get(String statement, String...strings)throws Exception{
		try{
			Connection con = getConnection();
			PreparedStatement select=con.prepareStatement(statement);
			//PreparedStatement statement=con.prepareStatement("SELECT Login_id,Login_pass FROM Login WHERE Login_id = '"+ID+"' AND Login_pass='"+Pass+"'");
			ResultSet result=select.executeQuery();
			ArrayList<String> array=new ArrayList<String>();
			while(result.next()){
				for(int i=0; i<strings.length; i++){
					array.add(result.getString(strings[i]));					
				}	
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

	
	public static void updateHS(String ID, String GamesPlayed, String HighScore)throws Exception{
		try{
			Connection con=getConnection();
			PreparedStatement update=con.prepareStatement("UPDATE '"+ID+"' SET HighScore='"+HighScore+"' WHERE Gamesplayed='"+GamesPlayed+"'");
			update.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}finally{
			System.out.println("update complete");
		}
	}
	

	public static void addGame(String table, String GamesPlayed, String HighScore) throws Exception{
		try{
			Connection con=getConnection();
			//PreparedStatement posted = con.prepareStatement(statement);
			PreparedStatement posted = con.prepareStatement("INSERT INTO "+table+" (GAMESPLAYED,HIGHSCORE) VALUES ('"+GamesPlayed+"','"+HighScore+"')");
			//executeQuery (receives info), executeUpdate(manipulates info)
			posted.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}finally{
			System.out.println("insert complete");
		}
	}
	
	public static void createTable(String ID) throws Exception{
		try{
			Connection con=getConnection();
			//Getting the sql statement ready to be used
			//PreparedStatement create=con.prepareStatement(statement);
			//PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS profile(ID VARCHAR(30), NICKNAME VARCHAR(30), GAMESPLAYED VARCHAR(255), HIGHSCORE INT,PRIMARY KEY(ID))");
			PreparedStatement create=con.prepareStatement(String.format("CREATE TABLE IF NOT EXISTS %s(GamesPlayed VARCHAR(255), HighScore VARCHAR(255))", ID));
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
			String url="jdbc:mysql://localhost:3306/LoginInfo";
//			String url="jdbc:mysql://10.190.3.194:3306/LoginInfo";
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
