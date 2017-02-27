package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EllipseShape extends RectangleShape {

	public EllipseShape(int x, int y, int width, int height, Color fillColor) {
		super(x, y, width, height, fillColor);
	}

	public EllipseShape(int x, int y, int width, int height, Color fillColor, Color strokeColor) {
		super(x, y, width, height, fillColor, strokeColor);
	}

	@Override
	public void draw(GraphicsContext g) {
		g.setFill(fillColor);
		g.setStroke(strokeColor);
		g.fillOval(xPosition, yPosition, width, height);
		g.strokeOval(xPosition, yPosition, width, height);
	}

}