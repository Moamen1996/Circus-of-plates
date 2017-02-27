package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import controller.GameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import shapes.DynamicLoader;
import shapes.ShapeFactory;
import view.GraphicsDrawer;

public class Main extends Application {
	private static final Logger LOGGER = Logger.getLogger(Main.class);

	private Canvas gameCanvas;
	private GameController gameController;
	private GraphicsDrawer graphicsDrawer;

	@Override
	public void start(Stage gameStage) throws Exception {
		initialize(gameStage);
		LOGGER.debug("Game stage initialized");
		gameController.start();
	}

	private void initialize(Stage gameStage) {
		gameCanvas = new Canvas(GraphicsDrawer.CANVAS_WIDTH, GraphicsDrawer.CANVAS_HEIGH);
		initialiseGame(gameCanvas);
		SplitPane layout = new GameLayout(gameCanvas, gameController);
		Scene gameScene = new Scene(layout, GraphicsDrawer.SCENE_WIDTH, GraphicsDrawer.SCENE_HEIGH);
		move(gameScene);
		setStageProperties(gameStage, gameScene);
	}

	void move(Scene gameScene) {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				gameController.moveAvatars(event.getCode());
			}
		});
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				gameController.stopMovingAvatars(event.getCode());
			}
		});
	}

	void initialiseGame(Canvas gameCanvas) {
		graphicsDrawer = new GraphicsDrawer(gameCanvas);
		gameController = GameController.getInstance();
		gameController.setGraphicsDrawer(graphicsDrawer);
	}

	void setStageProperties(Stage gameStage, Scene gameScene) {
		gameStage.setTitle("Circus of Plates");
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);
		gameStage.show();
	}

	private static void configureLogger() throws FileNotFoundException, IOException {
		try {
			Properties prop = new Properties();
			prop.load(Main.class.getResourceAsStream("/properties/" + "log4j.properties"));
			PropertyConfigurator.configure(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			configureLogger();
		} catch (IOException e) {
			System.out.println("Couldn't Initialise Log4J");
		}
		DynamicLoader dl = DynamicLoader.getInstance();
		ArrayList<Constructor<?>[]> loades = dl.initialize();
		ShapeFactory.addNewShape(loades.get(0));
		ShapeFactory.addNewShape(loades.get(1));
		LOGGER.info("Game started");
		launch(args);
	}
}
