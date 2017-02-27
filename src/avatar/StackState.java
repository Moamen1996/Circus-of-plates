package avatar;

import shapes.CustomShape;

public interface StackState {

	public boolean canAttach(CustomShape shape, int xPosition, int heightSum);
}
