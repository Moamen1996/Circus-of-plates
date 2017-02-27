package avatar;

import org.apache.log4j.Logger;

import shapes.CustomShape;

public class EmptyStack implements StackState {
	private static final Logger LOGGER = Logger.getLogger(EmptyStack.class);

	@Override
	public boolean canAttach(CustomShape shape, int xPosition, int heightSum) {
		int baseHeight = (int) (shape.getYPosition() + shape.getHeight());
		int centrePosition = (int) (shape.getXPosition() + (shape.getWidth() / 2));
		int realCentre = xPosition + (Stack.WIDTH / 2);
		if (baseHeight <= heightSum && baseHeight >= heightSum - Stack.VERT_EPS
				&& centrePosition >= realCentre - Stack.HORT_EPS && centrePosition <= realCentre + Stack.HORT_EPS) {
			LOGGER.info("Shape attatched to Stack");
			return true;
		}
		return false;
	}

}
