package utilities.SocialCenter;



public class HighScoreFactory {
	private static Driver db = new Driver();
	private static String[] ID={"Kevin","Michael","Natalie","Duvall","HoJean","Mike","Emre","Daniel","Andrew","Brian","MengChao","YangCheng"};
	
	
	public static void main(String[] args) throws Exception {
		createIDTables();
		addGame();
	}

	
	private static void createIDTables(){
		for(int i=0;i<ID.length;i++){
			try {
				db.createTable("HighScore",ID[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	private static  void addGame(){
		String[] Games={"Red","Blue","Yellow","Gold","Silver","Crystal","Ruby","Sapphire","Emerald","Fire Red", "Leaf Green", "Diamond", "Pearl", "Platinum", "Heart Gold", "Soul Silver", "Black", "White", "Black II", "White II", "X", "Y", "Omega Ruby", "Alpha Sapphire"};
		String[] Percent={"5%","1%","2%","6%","3%","7%","11%","32%","78%","98%", "32%", "65%", "32%", "44%", "22%", "90%", "32%", "11%", "88%", "23%", "100%", "100%", "23%", "44%"};
		for(String s:ID){
			for(int i=0; i<Games.length; i++){
				try {
					db.addGame("HighScore",s, Games[i], Percent[i]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}	
}
