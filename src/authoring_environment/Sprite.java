package authoring_environment;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/***
 * Class that contains information about the sprites for eventually generating xml files
 * 
 * @author Daniel Luker, Andrew Sun
 *
 */
public class Sprite extends ImageView{

	String name;
	Map<String, String> position;
	double speed;
	double health;
	Map<String, String> key_actions;
	String imageuri;
	private int id;
	Sprite.GRAVITY mGravity;

	public Sprite(int id, String image) {
		this.id = id;
		imageuri = image;
		this.setImage(new Image(getClass().getResourceAsStream(imageuri)));
		position = new HashMap<>();
		key_actions = new HashMap<>();
	}

	// addKeyAction()

	enum ACTIONS {
		UP, DOWN, LEFT, RIGHT, SPACEBAR, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
	};

	enum GRAVITY {
		UP, DOWN, LEFT, RIGHT
	};
	
	public int getID(){
		return id;
		
	}

}
