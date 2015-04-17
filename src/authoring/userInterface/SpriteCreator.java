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
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet=null;

	//Creating an ArrayList of Players based on spritesheets
	private BufferedImage player;
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
		try {   
			spriteSheet = loader.loadImage("/Users/hojeanniechung/Documents/workspace_308/voogasalad_HighScrollers/res/testSpritesheet.png"); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		SpriteSheet ss = new SpriteSheet(spriteSheet);  
		sprite = ss.grabSprite(0, 0, 20, 20);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(sprite, 100, 100, 50, 50, null);
		repaint();
	}

	public static void main(String[] args) {
		SpriteCreator main = new SpriteCreator();
	}
}



