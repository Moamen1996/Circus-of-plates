package sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TextSprite extends Sprite {

	private static final String FONT_NAME = "Arial";
	private static final int FONT_SIZE = 30;
	private static final int X = 200, Y = 200;

	private String message;

	public TextSprite(String message) {
		this.message = message;
	}

	@Override
	public void draw(GraphicsContext g) {
		Font font = Font.font(FONT_NAME, FontWeight.BOLD, FONT_SIZE);
		g.setFont(font);
		g.setStroke(Color.BLACK);
		g.fillText(message, X, Y);
		g.strokeText(message, X, Y);
	}
}
