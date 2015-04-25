package authoring.userInterface;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;
import java.util.ArrayList;


public class SpriteCreator extends JFrame {
	BufferedImage sprite;
	private static final int WIDTH =512;
	private static final int HEIGHT=512;

	//Creating an ArrayList of Players based on spritesheets
	private ArrayList<BufferedImage> Player=new ArrayList<>();

	public SpriteCreator() { 
		setSize(600, 500); 
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		init();
	}


	private void init() {  
		BufferedImageLoader loader = new BufferedImageLoader();
		BufferedImage spriteSheet = null;  
		int pixelcount=60;
		
		try {   
			spriteSheet = loader.loadImage("/Resources/images/Test.Spritesheet.png"); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		SpriteSheet ss = new SpriteSheet(spriteSheet);  
		for(int i=0; i<spriteSheet.getWidth()/pixelcount; i++){
			for(int j=0; j<spriteSheet.getHeight()/pixelcount; i++){
				Player.add(ss.grabSprite(i,j, 60, 60));
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		for(int k=0; k<Player.size(); k++){
			g.drawImage(Player.get(k), 100, 100, 50, 50, null);
		}
		
		repaint();
	}

	public static void main(String[] args) {
		SpriteCreator main = new SpriteCreator();
	}
}



