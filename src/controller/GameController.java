package controller;

import java.util.Optional;

import org.apache.log4j.Logger;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import model.AllStacksEndSystem;
import model.EndSystemStrategy;
import model.GameModel;
import model.OneStackEndSystem;
import saving.JsonWriter;
import saving.StateBundle;
import shapes.ShapesPool;
import view.GraphicsDrawer;

public class GameController extends AnimationTimer {
	private static final Logger LOGGER = Logger.getLogger(GameController.class);

	private final static int FIRST_AVATAR = 0, SECOND_AVATAR = 1;
	private final static int AVATAR_MOVED_DISTANCE = 15;

	private static GameController gameController;
	private GameModel gameModel;
	private GraphicsDrawer graphicsDrawer;
	private AudioController audioController;
	private EndSystemStrategy endSystemStrategy;

	private boolean gameEnded;
	private boolean avatarOneToleft, avatarOneToRight, avatarTwoToLeft, avatarTwoToRight;
	private int difficulty = 0;

	private GameController() {
	}

	public static GameController getInstance() {
		if (gameController == null) {
			gameController = new GameController();
		}
		return gameController;
	}

	public void setGraphicsDrawer(GraphicsDrawer graphicsDrawer) {
		endSystemStrategy = new AllStacksEndSystem();
		this.gameModel = new GameModel(difficulty, endSystemStrategy);
		this.graphicsDrawer = graphicsDrawer;
		graphicsDrawer.attachSubject(gameModel);
		this.audioController = AudioController.getInstance();
		audioController.playBackgroundMusic();
		gameEnded = false;
	}

	public void newGame(int i) {
		if (i != difficulty) {
			difficulty = i;
			gameEnded = false;
			gameModel.releaseAll();
			this.gameModel = new GameModel(difficulty, endSystemStrategy);
			graphicsDrawer.attachSubject(gameModel);
			resumeGame();
		}
		LOGGER.info("New game started");
	}

	public void newGame() {
		audioController.beginBackgroundMusic();
		gameEnded = false;
		gameModel.releaseAll();
		this.gameModel = new GameModel(difficulty, endSystemStrategy);
		graphicsDrawer.attachSubject(gameModel);
		resumeGame();
	}

	public void resumeGame() {
		if (!gameEnded) {
			audioController.resumeBackgroundMusic();
			start();
			LOGGER.info("Game resumed");
		}
	}

	public void pauseGame() {
		audioController.pauseBackgroundMusic();
		stop();
		LOGGER.info("Game paused");
	}

	@Override
	public void handle(long now) {
		if (gameEnded) {
			return;
		}
		gameModel.updateState();
		handleMotion();
		if (gameModel.gameEnded()) {
			int winner = gameModel.getWinner();
			pauseGame();
			gameEnded = true;
			LOGGER.debug("Winner = " + winner);
		}
	}

	public void moveAvatars(KeyCode keyCode) {
		switch (keyCode) {
		case LEFT:
			avatarOneToleft = true;
			break;
		case RIGHT:
			avatarOneToRight = true;
			break;
		case A:
			avatarTwoToLeft = true;
			break;
		case D:
			avatarTwoToRight = true;
			break;
		default:
			break;
		}
	}

	public void stopMovingAvatars(KeyCode keyCode) {
		switch (keyCode) {
		case LEFT:
			avatarOneToleft = false;
			break;
		case RIGHT:
			avatarOneToRight = false;
			break;
		case A:
			avatarTwoToLeft = false;
			break;
		case D:
			avatarTwoToRight = false;
			break;
		default:
			break;
		}
	}

	public void changeEndSystem() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Change End System");
		alert.setHeaderText("Change the End System used to anounce game over.");
		alert.setContentText("Choose your option. You must start new game to take effect");
		ButtonType buttonTypeOne = new ButtonType("All stacks full");
		ButtonType buttonTypeTwo = new ButtonType("One stack full");
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			endSystemStrategy = new AllStacksEndSystem();
		} else if (result.get() == buttonTypeTwo) {
			endSystemStrategy = new OneStackEndSystem();
		}
	}

	public void save(String filePath) {
		JsonWriter jsonWriter = new JsonWriter();
		StateBundle bundle = getGameState();
		try {
			jsonWriter.save(bundle, filePath);
			done("Saving game", "Game saved successfully.");
		} catch (Exception e) {
			alert("Saving game", "Couldn't save the file, make sure you have permision.");
		} finally {
			resumeGame();
		}
	}

	public void load(String filePath) {
		StateBundle stateBundle = null;
		try {
			JsonWriter jsonWriter = new JsonWriter();
			stateBundle = jsonWriter.load(filePath);
			setGameState(stateBundle);
			done("Load game", "Game loaded Successfully.");
			LOGGER.info("Game loaded successfully");
		} catch (Exception e) {
			alert("Load game", "Couldn't load the file, Check if it's corrupted.");
			LOGGER.fatal("Game loading failed.");
		} finally {
			resumeGame();
		}
	}

	private void setGameState(StateBundle stateBundle) {
		this.difficulty = stateBundle.getDifficulty();
		EndSystemStrategy state;
		if (stateBundle.getState() == 1)
			state = new AllStacksEndSystem();
		else
			state = new OneStackEndSystem();
		gameModel = new GameModel(this.difficulty, stateBundle.getAvatar(), stateBundle.getInUse(),
				stateBundle.getRailsContainer(), state);
		graphicsDrawer.attachSubject(gameModel);
	}

	private StateBundle getGameState() {
		StateBundle b = new StateBundle(gameModel.getAvatars().get(0), gameModel.getAvatars().get(1),
				ShapesPool.getInstance().getInUse(), this.difficulty, gameModel.getRailsContainer(),
				gameModel.getEndStrategy());
		return b;
	}

	private void handleMotion() {
		if (avatarOneToleft) {
			gameModel.movePlayer(FIRST_AVATAR, -AVATAR_MOVED_DISTANCE);
			LOGGER.info("First player moved to left");
		}
		if (avatarOneToRight) {
			gameModel.movePlayer(FIRST_AVATAR, AVATAR_MOVED_DISTANCE);
			LOGGER.info("First player moved to right");
		}
		if (avatarTwoToLeft) {
			gameModel.movePlayer(SECOND_AVATAR, -AVATAR_MOVED_DISTANCE);
			LOGGER.info("Second player moved to left");
		}
		if (avatarTwoToRight) {
			gameModel.movePlayer(SECOND_AVATAR, AVATAR_MOVED_DISTANCE);
			LOGGER.info("Second player moved to right");
		}
	}

	private void alert(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Warning");
		alert.setHeaderText(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void done(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Done");
		alert.setHeaderText(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
}