package game_player;

import game_engine.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HeadsUpDisplay {
	private static final double PADDING = 15;

	private List<Sprite> sprites;
	
	private double totalTime;
	private Text time, score;
	
	public HeadsUpDisplay(Sprite sprite) {
		totalTime = 0;
		time = makeText("0");
		score = makeText("0");
		sprites = new ArrayList<>();
		sprites.add(sprite);
	}
	
	private Text makeText(String message) {
		Text text = new Text(message);
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(1);
		text.setFont(new Font(30));
		return text;
	}
	
	public VBox getHUD() {
		VBox holder = new VBox(PADDING);
		Text timeLabel = makeText("Time: ");
		Text scoreLabel = makeText("Score: ");
		HBox timeStuff = new HBox();
		timeStuff.getChildren().addAll(timeLabel, time);
		HBox scoreStuff = new HBox();
		scoreStuff.getChildren().addAll(scoreLabel, score);
		holder.getChildren().addAll(timeStuff, scoreStuff);
		return holder;
	}
	
	public void update(double timeElapsed) {
		totalTime += timeElapsed;
		time.setText("" + (int) totalTime);
		score.setText("" + sprites.get(0).getWorth());
	}
}