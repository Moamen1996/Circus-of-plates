package sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import shapes.CustomShape;

public class ShapeSprite extends Sprite {

	private CustomShape shape;

	public ShapeSprite(CustomShape shape) {
		this.shape = shape;
	}

	@Override
	public void draw(GraphicsContext g) {
		shape.draw(g);
	}

	public int getWidth() {
		return shape.getWidth();
	}

	public int getHeight() {
		return shape.getHeight();
	}

	public void setY(int y) {
		shape.setYPostion(y);
	}

	public void setX(int x) {
		shape.setXPostion(x);
	}

	public Color getColor() {
		return shape.getFillColor();
	}

	public Color getStrokeColor() {
		return shape.getStrokeColor();
	}

	public int getX() {
		return (int) shape.getXPosition();
	}

	public int getY() {
		return (int) shape.getYPosition();
	}

	public CustomShape getCustomShape() {
		return shape;
	}
}
