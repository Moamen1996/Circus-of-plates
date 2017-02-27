package view;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import gui.Main;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.GameModel;
import sprite.Sprite;

public class GraphicsDrawer implements Observer {

	private static final Logger LOGGER = Logger.getLogger(GraphicsDrawer.class);

	public static final int SCENE_WIDTH = 950, SCENE_HEIGH = 630;
	public static final int CANVAS_WIDTH = 950, CANVAS_HEIGH = 600;

	public static final int STAND_THICKNESS = 10;
	public static final int STAND_HEIGHT = 585;
	public static final int STAND_WIDTH = 950;

	private GameModel gameModel;
	private Canvas gameCanvas;
	private Image backGround;

	public GraphicsDrawer(Canvas gameCanvas) {
		this.gameCanvas = gameCanvas;
		try {
			String path = Main.class.getResource("/pic/" + "background.jpg").toURI().toString();
			this.backGround = new Image(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void draw(ArrayList<Sprite> sprites) {
		GraphicsContext g = gameCanvas.getGraphicsContext2D();
		drawBackground(gameCanvas.getGraphicsContext2D());
		int spriteCount = sprites.size();
		for (int idx = 0; idx < spriteCount; idx++) {
			((Sprite) sprites.get(idx)).draw(g);
		}
		LOGGER.debug("Sprites repainted");
	}

	private void drawBackground(GraphicsContext g) {
		g.drawImage(backGround, 0, 0);
		g.setFill(Color.BLACK);
		g.fillRect(0, STAND_HEIGHT, STAND_WIDTH, STAND_THICKNESS);
		LOGGER.debug("Background repainted");
	}

	@Override
	public void update() {
		draw(gameModel.getSprites());
	}

	public void attachSubject(GameModel gameModel) {
		this.gameModel = gameModel;
		gameModel.attachObserver(this);
	}
}
