package shapes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import javafx.scene.paint.Color;

public class ShapeFactory {
	private static final Logger LOGGER = Logger.getLogger(ShapeFactory.class);

	private static ArrayList<Constructor<?>[]> loadedShapes = new ArrayList<Constructor<?>[]>();

	public ShapeFactory() {
		if (loadedShapes == null) {
			loadedShapes = new ArrayList<Constructor<?>[]>();
		}
	}
	
	public static ArrayList<Constructor<?>[]> getLoaded() {
		return loadedShapes;
	}

	public static void addNewShape(Constructor<?>[] newShapeConstructor) {
		int length = loadedShapes.size();
		for (int i = 0; i < length; i++) {
			if (loadedShapes.get(i).equals(newShapeConstructor)) {
				return;
			}
		}
		LOGGER.debug("Loaded shape added to the list");
		loadedShapes.add(newShapeConstructor);
	}

	public static int getLoadedShapesCount() {
		return ShapeFactory.loadedShapes.size();
	}

	public CustomShape getObject(int requiredShape, int[] dimensions, Color color) {
		if (requiredShape >= loadedShapes.size()) {
			return null;
		}
		try {
			Constructor<?> constructor = loadedShapes.get(requiredShape)[0];
			Object returnedShape = getReqShape(constructor, dimensions, color);
			return (CustomShape) returnedShape;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			LOGGER.fatal("Cannot get a shape");
			e.printStackTrace();
		}
		return null;
	}

	private Object getReqShape(Constructor<?> constructor, int[] dimensions, Color color)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			return constructor.newInstance(0, 0, dimensions[0], dimensions[1], color);
		} catch (Exception e) {
			LOGGER.fatal("Error in creating new Shape");
		}
		return constructor.newInstance(0, 0, dimensions[0], dimensions[1], color);

	}

}
