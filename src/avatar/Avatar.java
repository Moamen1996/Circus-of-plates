package avatar;

import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import gui.Main;
import javafx.scene.image.Image;
import shapes.CustomShape;
import sprite.AvatarSprite;
import sprite.ScoreSprite;
import sprite.Sprite;
import view.GraphicsDrawer;

public class Avatar {
	private static final Logger LOGGER = Logger.getLogger(Avatar.class);

	private static final String[] spriteName = { "render-1.png", "render-2.png" };
	private static final int AVATAR_WIDTH = 90;
	private static final int LEFT = 0, RIGHT = 1;
	public static final int AVATAR_HEIGHT = 400;

	private static int playerCount = 0;

	private Image spriteImage;
	private int xPosition, y;
	private Stack[] stack;
	private int playerIndex = 0;

	public Avatar() {
		playerIndex = playerCount % 2;
		y = AVATAR_HEIGHT;
		xPosition = 300 + playerCount * 300;
		stack = new Stack[2];
		stack[LEFT] = new Stack(playerCount % 2);
		stack[RIGHT] = new Stack(playerCount % 2);
		calculateStackIndex();
		playerCount = (playerCount + 1) % 2;
		addSprite();
		LOGGER.info("Avatar created");
	}

	public int getX() {
		return this.xPosition;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.xPosition = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPlayerIndex() {
		return this.playerIndex;
	}

	public ArrayList<Sprite> getSprites() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		sprites.add(getAvatarSprite());
		sprites.addAll(getStackShapes());
		return sprites;
	}

	public int getScore() {
		int totalScore = 0;
		for (Stack avatarStack : stack) {
			totalScore += avatarStack.getScore();
		}
		LOGGER.info("Score Calculated");
		return totalScore;
	}

	public Stack[] getStack() {
		return this.stack;
	}

	public void setIndex(int index) {
		this.playerIndex = index;
	}

	public void setStack(Stack[] stack) {
		this.stack = stack;
	}

	public boolean checkStackFull() {
		boolean stacksFull = true;
		for (Stack stack : stack) {
			stacksFull = stacksFull && stack.checkStackFull();
		}
		return stacksFull;
	}

	public void releaseShapes() {
		for (Stack stack : stack) {
			stack.releaseShapes();
		}
	}

	public void move(int dist) {
		if (xPosition + dist >= 0 && xPosition + dist + AVATAR_WIDTH <= GraphicsDrawer.SCENE_WIDTH) {
			xPosition += dist;
		}
		calculateStackIndex();
	}

	public boolean attach(CustomShape shape) {
		if (stack[LEFT].attach(shape) || stack[RIGHT].attach(shape)) {
			LOGGER.info("Shape added into stack");
			return true;
		}
		return false;
	}

	private void calculateStackIndex() {
		stack[LEFT].setX(xPosition + AVATAR_WIDTH / 2 - Stack.WIDTH - Stack.RADIUS);
		stack[RIGHT].setX(xPosition + Avatar.AVATAR_WIDTH - Stack.WIDTH + Stack.RADIUS + Stack.SHIFT);
	}

	private void addSprite() {
		String path;
		try {
			path = Main.class.getResource("/pic" + "/" + spriteName[playerCount]).toURI().toString();
			spriteImage = new Image(path);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private Sprite getAvatarSprite() {
		return new AvatarSprite(xPosition, y, spriteImage);
	}

	private ArrayList<Sprite> getStackShapes() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for (Stack avatarStack : stack) {
			sprites.add(avatarStack.getSprite());
			sprites.addAll(avatarStack.getShapesSprite());
		}
		sprites.add(new ScoreSprite(playerIndex, getScore()));
		return sprites;
	}

	public boolean hasFullStack() {
		for (Stack stack : this.stack) {
			if (stack.checkStackFull()) {
				return true;
			}
		}
		return false;
	}
}
