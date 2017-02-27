package avatar;

import org.apache.log4j.Logger;

import shapes.CustomShape;

public class FullStack implements StackState {
	private static final Logger LOGGER = Logger.getLogger(FullStack.class);

	@Override
	public boolean canAttach(CustomShape shape, int xPosition, int heightSum) {
		LOGGER.info("Stack is full");
		return false;
	}

}
