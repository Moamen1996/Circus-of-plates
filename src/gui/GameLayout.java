package gui;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import controller.GameController;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;

public class GameLayout extends SplitPane {
	private static final Logger LOGGER = Logger.getLogger(SplitPane.class);

	private ToolBar toolbar;
	private ArrayList<Node> toolbarNodes = new ArrayList<Node>();
	private StackPane graphicsPane, toolbarPane;
	private Canvas gameCanvas;

	public GameLayout(Canvas gameCanvas, GameController gameController) {
		this.gameCanvas = gameCanvas;
		initialiseToolbarPane(gameController);
		initialiseGraphicsPane();
		getItems().addAll(toolbarPane, graphicsPane);
		setOrientation(Orientation.VERTICAL);
		setDividerPositions(0.1f, 0.6f);
		LOGGER.debug("Game layout created");
	}

	private void initialiseGraphicsPane() {
		graphicsPane = new StackPane();
		graphicsPane.getChildren().add(gameCanvas);
	}

	private void initialiseToolbarPane(GameController gameController) {
		toolbarPane = new StackPane();
		toolbar = new ToolBar();
		Iterator<Node> nodeIter = ButtonContainer.getInstance().getIterator();
		while (nodeIter.hasNext()) {
			toolbarNodes.add(nodeIter.next());
		}
		toolbar.getItems().addAll(toolbarNodes);
		toolbarPane.getChildren().add(toolbar);
	}

}
