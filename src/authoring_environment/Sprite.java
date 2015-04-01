package authoring_environment;

import java.util.HashMap;
import java.util.Map;

public class Sprite {

	String name;
	Map<String, String> position;
	double speed;
	double health;
	Map<String, String> key_actions;
	String imageuri;
	int id;
	Sprite.GRAVITY mGravity;

	Sprite(int id) {
		this.id = id;
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

}
