package game_player;

import game_engine.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class HeadsUpDisplay {

	private List<Sprite> sprites;
	
	private double totalTime;
	private Text time, score;
	
	public HeadsUpDisplay(Sprite sprite) {
		totalTime = 0;
		time = new Text("0");
		score = new Text("0");
		sprites = new ArrayList<>();
		sprites.add(sprite);
	}
	
	public StackPane getHUD() {
		StackPane holder = new StackPane();
		holder.getChildren().addAll(time, score);
		holder.setAlignment(time, Pos.TOP_LEFT);
		holder.setAlignment(score, Pos.TOP_RIGHT);
		return holder;
	}
	
	public void update(double timeElapsed) {
		totalTime += timeElapsed;
		time.setText("" + (int) totalTime);
		score.setText("" + sprites.get(0).getWorth());
	}
}