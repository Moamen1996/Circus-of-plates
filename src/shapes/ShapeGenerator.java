package shapes;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;

import javafx.scene.paint.Color;

public class ShapeGenerator {
	private static final Logger LOGGER = Logger.getLogger(ShapeGenerator.class);

	private static ArrayList<Color> colors;

	private ShapeFactory shapeFactory;

	public ShapeGenerator() {
		shapeFactory = new ShapeFactory();
		initializeColors();
	}

	private void initializeColors() {
		colors = new ArrayList<Color>();
		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.YELLOW);
	}

	public void addColor(Color newColor) {
		for (int i = 0; i < colors.size(); i++) {
			if (colors.get(i).equals(newColor)) {
				return;
			}
		}
		colors.add(newColor);
	}

	public CustomShape getShape() {
		int[] dimensions;
		int shapeType = (new Random()).nextInt(ShapeFactory.getLoadedShapesCount());
		int colorIndex = (new Random()).nextInt(colors.size());
		dimensions = new int[] { 40, 15 };
		CustomShape reqShape = shapeFactory.getObject(shapeType, dimensions, colors.get(colorIndex));
		LOGGER.debug("Random Shape generated");
		if (reqShape == null) {
			LOGGER.fatal("Random Shape is null");
		}
		return reqShape;
	}

	public static Color getColor(int i) {
		return colors.get(i);
	}

	public static int getInd(Color c) {
		for (int i = 0; i < colors.size(); i++) {
			if (colors.get(i).equals(c))
				return i;
		}
		return 0;
	}

}
