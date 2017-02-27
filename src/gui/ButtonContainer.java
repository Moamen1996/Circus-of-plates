package gui;

import java.io.File;
import java.util.Iterator;

import org.apache.log4j.Logger;

import controller.AudioController;
import controller.GameController;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.stage.FileChooser;

public class ButtonContainer {
	private static final Logger LOGGER = Logger.getLogger(ButtonContainer.class);

	private static ButtonContainer buttonContainer;

	private static final String[] btnName = new String[] { "New", "Pause", "Resume", "Quit", null, "Save", "Load", null,
			"Mute", null, "Easy", "Medium", "Hard", null, "End System" };
	private static final int NEW = 0, PAUSE = 1, RESUME = 2, QUIT = 3, SAVE = 5, LOAD = 6, MUTE = 8, EASY = 10,
			MEDIUM = 11, HARD = 12, END_SYSTEM = 14;

	public static ButtonContainer getInstance() {
		if (buttonContainer == null) {
			buttonContainer = new ButtonContainer();
		}
		return buttonContainer;
	}

	private boolean isSeparator(int index) {
		return btnName[index] == null;
	}

	public Iterator<Node> getIterator() {
		return new ButtonIterator();
	}

	private Node createButton(int index) {
		Button curBtn = new Button(btnName[index]);
		switch (index) {
		case NEW:
			curBtn.setOnAction(e -> GameController.getInstance().newGame());
			break;
		case PAUSE:
			curBtn.setOnAction(e -> GameController.getInstance().pauseGame());
			break;
		case RESUME:
			curBtn.setOnAction(e -> GameController.getInstance().resumeGame());
			break;
		case QUIT:
			curBtn.setOnAction(e -> Platform.exit());
			break;
		case SAVE:
			curBtn.setOnAction(e -> {
				GameController.getInstance().pauseGame();
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Load game");
				File saveFile = chooser.showSaveDialog(null);
				if (saveFile != null)
					GameController.getInstance().save(saveFile.toPath().toString());
				else
					GameController.getInstance().resumeGame();
			});
			break;
		case LOAD:
			curBtn.setOnAction(e -> {
				GameController.getInstance().pauseGame();
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Load game");
				File loadFile = chooser.showOpenDialog(null);
				if (loadFile != null)
					GameController.getInstance().load(loadFile.toPath().toString());
				else
					GameController.getInstance().resumeGame();
			});
			break;
		case MUTE:
			curBtn.setOnAction(e -> {
				if (curBtn.getText().equals("Mute")) {
					AudioController.getInstance().mute();
					curBtn.setText("Unmute");
				} else {
					AudioController.getInstance().unmute();
					curBtn.setText("Mute");
				}
			});
			break;
		case EASY:
			curBtn.setOnAction(e -> GameController.getInstance().newGame(0));
			break;
		case MEDIUM:
			curBtn.setOnAction(e -> GameController.getInstance().newGame(1));
			break;
		case HARD:
			curBtn.setOnAction(e -> GameController.getInstance().newGame(2));
			break;
		case END_SYSTEM:
			curBtn.setOnAction(e -> {
				GameController.getInstance().pauseGame();
				GameController.getInstance().changeEndSystem();
				GameController.getInstance().resumeGame();
			});
			break;
		default:
			LOGGER.fatal("Wrong button index");
			return null;
		}
		LOGGER.debug(btnName[index] + " button created");
		return curBtn;
	}

	private class ButtonIterator implements Iterator<Node> {

		int index;

		public boolean hasNext() {
			if (index < btnName.length) {
				return true;
			}
			return false;
		}

		@Override
		public Node next() {
			if (this.hasNext()) {
				Node curNode = createNode();
				index++;
				return curNode;
			}
			return null;
		}

		private Node createNode() {
			if (isSeparator(index)) {
				return new Separator();
			}
			return createButton(index);
		}
	}
}