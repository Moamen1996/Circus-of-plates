package shapes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import shapes.CustomShape;
import shapes.ShapeGenerator;

public class ShapesPool {
	private static final Logger LOGGER = Logger.getLogger(ShapesPool.class);

	public static final int POOL_SIZE = 100;

	private List<CustomShape> available;
	private List<CustomShape> inUse;
	private ShapeGenerator shapeGenerator;
	private static ShapesPool shapesPool;
	private static int createdShapes;

	private ShapesPool() {
		createdShapes = 0;
		available = new ArrayList<CustomShape>();
		inUse = new ArrayList<CustomShape>();
		shapeGenerator = new ShapeGenerator();
		LOGGER.debug("ShapesPool created");
	}

	public static ShapesPool getInstance() {
		if (shapesPool == null) {
			shapesPool = new ShapesPool();
		}
		return shapesPool;
	}

	public CustomShape getObject() {
		CustomShape generatedShape = null;
		if (createdShapes < POOL_SIZE) {
			generatedShape = shapeGenerator.getShape();
			inUse.add(generatedShape);
			createdShapes++;
			LOGGER.info("New shape added to the pool");
		} else if (!available.isEmpty()) {
			generatedShape = available.get(new Random().nextInt(available.size()));
			useShape(generatedShape);
			LOGGER.info("Shape selected from the pool");
		}
		return generatedShape;
	}

	public void releaseShape(CustomShape expiredShape) {
		for (int index = 0; index < inUse.size(); index++) {
			if (inUse.get(index) == expiredShape) {
				inUse.remove(index);
				break;
			}
		}
		
		boolean found = false;
		for (int i = 0; i < available.size(); i++) {
			if (available.get(i) == expiredShape) {
				found = true;
			}
		}
		if (!found) {
			available.add(expiredShape);
		}
		LOGGER.debug("Shape added to available list");
	}

	public List<CustomShape> getInUse() {
		return inUse;
	}

	public void removeInUse(CustomShape shape) {
		for (int i = 0; i < inUse.size(); i++) {
			if (inUse.get(i) == shape) {
				inUse.remove(i);
				LOGGER.debug("Shape removed from inUse list");
				i--;
				return;
			}
		}
	}

	public void releaseAll() {
		while (inUse.size() > 0) {
			releaseShape(inUse.get(0));
		}
		LOGGER.debug("All shapes are released");
	}

	private boolean useShape(CustomShape availableShape) {
		for (int index = 0; index < available.size(); index++) {
			if (availableShape == available.get(index)) {
				inUse.add(available.get(index));
				available.remove(index);
				availableShape = null;
				return true;
			}
		}
		return false;
	}
}
