package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sprite.ShapeSprite;
import sprite.Sprite;

public class RectangleShape extends CustomShape {

	public int width, height;

	public RectangleShape(int xPosition, int yPosition, int width, int height, Color fillColor) {
		super(xPosition, yPosition, fillColor);
		this.width = width;
		this.height = height;
	}

	public RectangleShape(int xPosition, int yPosition, int width, int height, Color fillColor, Color strokeColor) {
		super(xPosition, yPosition, fillColor, strokeColor);
		this.width = width;
		this.height = height;
	}

	@Override
	public Sprite getSprite() {
		return new ShapeSprite(this);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void draw(GraphicsContext g) {
		g.setFill(fillColor);
		g.setStroke(strokeColor);
		g.fillRect(xPosition, yPosition, width, height);
		g.strokeRect(xPosition, yPosition, width, height);
	}
}