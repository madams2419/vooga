// This entire file is part of my masterpiece.
// Jeannie Chung

package utilities.SocialCenter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import XML.QueryParser;

public class Driver {
	private static Map<String,String> queryMap=new HashMap<>();
	public Driver(){
		//parse commands
		QueryParser queries=new QueryParser();
		queryMap=queries.parse("resources/XML/Query.xml");
	}
	// public static void main(String[] args) throws Exception {
	// getConnection();
	// createTable();
	// //post();
	// //get();
	// }
	//
	
	public static ArrayList<String> get(String database, String statement,
			String... strings) throws Exception {
		try {
			Connection con = getConnection(database);
			PreparedStatement select = con.prepareStatement(statement);
			// PreparedStatement
			// statement=con.prepareStatement("SELECT Login_id,Login_pass FROM Login WHERE Login_id = '"+ID+"' AND Login_pass='"+Pass+"'");
			ResultSet result = select.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			while (result.next()) {
				for (int i = 0; i < strings.length; i++) {
					array.add(result.getString(strings[i]));
				}
			}
			if (result.first() == false) {
				array.add("none");
			}
			return array;
		} catch (Exception e) {
			System.out.println("Exception");
			return null;
		}
	}

//	public ArrayList<String> getScores(String database, String game,String columnName) {
//		try {
//			Connection con=getConnection(database);
//			PreparedStatement update = con.prepareStatement("SELECT HoJean.HighScore, Duvall.HighScore, Michael.HighScore FROM HoJean INNER JOIN Duvall ON HoJean.GamesPlayed=Duvall.GamesPlayed INNER JOIN Michael ON HoJean.GamesPlayed=Michael.GamesPlayed");
//			ResultSet result = update.executeQuery();
//			ArrayList<String> array = new ArrayList<String>();
//			while (result.next()) {
//				String[] strings={"HoJean.HighScore", "Duvall.HighScore", "Michael.HighScore"};
//				for (int i = 0; i < strings.length; i++) {
//					array.add(result.getString(strings[i]));
//				}
//			}
//			if (result.first() == false) {
//				array.add("none");
//			}
//			return array;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//		
//
//	}

	public static void updateHS(String database, String ID, String GamesPlayed,
			String HighScore) throws Exception {
		try {
			Connection con = getConnection(database);
			PreparedStatement update = con.prepareStatement(queryMap.get("updateHS"));
			update.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("update complete");
		}
	}

	public static void updateURL(String database, String ID, String URL)
			throws Exception {
		try {
			Connection con = getConnection(database);
			PreparedStatement update = con
					.prepareStatement(queryMap.get("updateURL"));
			update.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("update complete");
		}
	}

	public static void addGame(String database, String table,
			String GamesPlayed, String HighScore) throws Exception {
		try {
			Connection con = getConnection(database);
			// PreparedStatement posted = con.prepareStatement(statement);
			PreparedStatement posted = con.prepareStatement("INSERT INTO " + table + " (GAMESPLAYED,HIGHSCORE) VALUES ('" + GamesPlayed + "','" + HighScore + "')");
			// executeQuery (receives info), executeUpdate(manipulates info)
			posted.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("insert complete");
		}
	}

	public static void addLine(String database, String table, String CHAT)
			throws Exception {
		try {
			Connection con = getConnection(database);
			PreparedStatement posted = con.prepareStatement("INSERT INTO " + table + " (CHATLINE) VALUES ('" + CHAT + "')");
			posted.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("insert complete");
		}
	}

	public static void createTable(String database, String ID) throws Exception {
		try {
			Connection con = getConnection(database);
			// Getting the sql statement ready to be used
			// PreparedStatement create=con.prepareStatement(statement);
			// PreparedStatement create =
			// con.prepareStatement("CREATE TABLE IF NOT EXISTS profile(ID VARCHAR(30), NICKNAME VARCHAR(30), GAMESPLAYED VARCHAR(255), HIGHSCORE INT,PRIMARY KEY(ID))");
			PreparedStatement create = con
					.prepareStatement(String
							.format(queryMap.get("createTable")));
			create.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Function complete");
		}
	}

	public static void createChat(String database, String gamename)
			throws Exception {
		try {
			Connection con = getConnection(database);
			PreparedStatement create = con.prepareStatement(String.format(
					queryMap.get("createChat")));
			create.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("creation complete");
		}
	}

	public static Connection getConnection(String database) throws Exception {
		try {
			//System.out.println(queryMap);
			String driver = "com.mysql.jdbc.Driver";
			String url=String.format("jdbc:mysql://localhost:3306/%s",database);
//			String url = String.format("jdbc:mysql://10.190.93.173:3306/%s",database);
			
//			String username = "guest";
//			String password = "password";
			String username="root";
			String password="Tkfkdgo<3";
			Class.forName(driver);

			Connection conn = DriverManager.getConnection(url, username,
					password);
			System.out.println("Connected");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}
