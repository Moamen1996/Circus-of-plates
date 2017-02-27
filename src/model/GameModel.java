package model;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import avatar.Avatar;
import controller.ShapesController;
import gui.Main;
import javafx.scene.image.Image;
import rail.Rail;
import rail.RailsContainer;
import shapes.CustomShape;
import shapes.ShapesPool;
import sprite.Sprite;
import sprite.TextSprite;
import view.Observer;

public class GameModel implements Observable {
	private static final Logger LOGGER = Logger.getLogger(GameModel.class);

	private static final String[] gameEnd = { "The First Player Won the game.", "The Second Player Won the game.",
			"The game end in tie." };

	private static final int PLAYER_COUNT = 2;
	private static final int EASY = 0, MEDIUM = 1, HARD = 2;

	private Observer observer;

	private ArrayList<Avatar> avatars;
	private RailsContainer railsContainer;
	private ShapesController shapesController;
	private long prevCycleTime;
	private int shapeCycle;
	private int numberOfRails;
	private EndSystemStrategy endSystemStrategy;
	private boolean gameEnded;
	private int winner;

	public GameModel(int difficulty, EndSystemStrategy endSystemStrategy) {
		releaseAvatars();
		releaseShapes();
		this.prevCycleTime = System.currentTimeMillis();
		this.endSystemStrategy = endSystemStrategy;
		railsContainer = new RailsContainer();
		shapesController = new ShapesController(railsContainer);
		initialiseDifficulty(difficulty);
		initialiseAvatars();
		initialiseRails();
		LOGGER.info("Game Model Created");
	}

	public GameModel(int difficulty, Avatar[] avatars2, ArrayList<CustomShape> inUse, RailsContainer railsContainer,
			EndSystemStrategy endSystemStrategy) {
		releaseAvatars();
		releaseShapes();
		this.prevCycleTime = System.currentTimeMillis();
		this.railsContainer = railsContainer;
		this.shapesController = new ShapesController(this.railsContainer);
		initialiseAvatars(avatars2);
		initialiseDifficulty(difficulty);
		this.endSystemStrategy = endSystemStrategy;
		LOGGER.info("Game Model Created");
	}

	private void initialiseAvatars(Avatar[] avatars2) {
		initialiseAvatars();
		for (int i = 0; i < 2; i++) {
			Avatar curAvatar = this.avatars.get(i);
			Avatar newAvatar = avatars2[i];
			curAvatar.setX(newAvatar.getX());
			curAvatar.setY(newAvatar.getY());
			curAvatar.setStack(newAvatar.getStack());
		}
	}

	public RailsContainer getRailsContainer() {
		return railsContainer;
	}

	public ArrayList<Avatar> getAvatars() {
		return this.avatars;
	}

	public ArrayList<Sprite> getSprites() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for (Avatar avatar : avatars) {
			sprites.addAll(avatar.getSprites());
		}
		List<CustomShape> shapes = ShapesPool.getInstance().getInUse();
		for (int j = 0; j < shapes.size(); j++) {
			boolean attached = false;
			CustomShape shape = shapes.get(j);
			for (int i = 0; i < 2 && (!attached); i++) {
				attached = avatars.get(i).attach(shape);
			}
			if (attached) {
				ShapesPool.getInstance().removeInUse(shape);
				j--;
			} else {
				sprites.add(shape.getSprite());
			}
		}
		for (Rail rail : railsContainer.getRails()) {
			sprites.add(rail.getSprite());
		}
		if (gameEnded) {
			sprites.add(new TextSprite(getWinMessage(winner)));
			LOGGER.info(getWinMessage(winner));
		}
		LOGGER.debug("Sprites added to the List");
		return sprites;
	}

	public void movePlayer(int i, int dist) {
		avatars.get(i).move(dist);
	}

	public boolean gameEnded() {
		return this.endSystemStrategy.gameEnded(avatars);
	}

	public int getWinner() {
		this.winner = avatars.get(0).getScore() - avatars.get(1).getScore();
		this.gameEnded = true;
		notifyObserver();
		return winner;
	}

	public void releaseAll() {
		releaseShapes();
		releaseAvatars();
	}

	private void initialiseDifficulty(int difficulty) {
		switch (difficulty) {
		case EASY:
			shapeCycle = 700;
			numberOfRails = 2;
			LOGGER.info("Difficulty is easy");
			break;
		case MEDIUM:
			shapeCycle = 500;
			numberOfRails = 2;
			LOGGER.info("Difficulty is medium");
			break;
		case HARD:
			shapeCycle = 500;
			numberOfRails = 4;
			LOGGER.info("Difficulty is hard");
			break;
		}
	}

	private void initialiseAvatars() {
		avatars = new ArrayList<Avatar>();
		for (int i = 0; i < PLAYER_COUNT; i++) {
			avatars.add(new Avatar());
		}
		LOGGER.info("Avatars Initialised");
	}

	private void initialiseRails() {
		try {
			String path = Main.class.getResource(Rail.IMAGE).toURI().toString();
			Image spriteImage = new Image(path);
			for (int i = 0; i < numberOfRails; i++) {
				railsContainer.addRail(
						new Rail(Rail.allign[i], Rail.xPosition[i], Rail.yPosition[i], Rail.WIDTH, spriteImage));
			}
			LOGGER.info("rails Initialised");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void releaseShapes() {
		ShapesPool.getInstance().releaseAll();
		LOGGER.debug("All shapes released");
	}

	private void releaseAvatars() {
		if (avatars == null) {
			return;
		}
		for (Avatar avatar : avatars) {
			avatar.releaseShapes();
		}
		LOGGER.debug("All shapes in avatars released");
	}

	private void moveShapes() {
		if (System.currentTimeMillis() - prevCycleTime > shapeCycle) {
			shapesController.startNewShape();
			LOGGER.info("New Shape Started Moving on the rail");
			prevCycleTime = System.currentTimeMillis();
		}
		shapesController.moveShapes();
	}

	private String getWinMessage(int winner) {
		if (winner > 0) {
			return gameEnd[0];
		} else if (winner < 0) {
			return gameEnd[1];
		} else {
			return gameEnd[2];
		}
	}

	@Override
	public void attachObserver(Observer observer) {
		this.observer = observer;
	}

	@Override
	public void notifyObserver() {
		observer.update();
	}

	@Override
	public void updateState() {
		moveShapes();
		notifyObserver();
	}

	public int getEndStrategy() {
		if (endSystemStrategy instanceof AllStacksEndSystem)
			return 1;
		return 0;
	}
}
