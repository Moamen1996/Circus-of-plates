package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sprite.Sprite;

public abstract class CustomShape {

	protected Color fillColor, strokeColor;
	protected double xPosition, yPosition;
	protected double xVelocity;
	protected double yVelocity;

	private static double maxVelocity = 5;
	private boolean isCaught = false;

	public CustomShape(int xPosition, int yPosition, Color fillColor, Color strokeColor) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
	}

	public CustomShape(int xPosition, int yPosition, Color fillColor) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.fillColor = fillColor;
	}

	public void setXPostion(double xPosition) {
		this.xPosition = xPosition;
	}

	public void setYPostion(double yPosition) {
		this.yPosition = yPosition;
	}

	public double getXPosition() {
		return this.xPosition;
	}

	public double getYPosition() {
		return this.yPosition;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public Color getFillColor() {
		return this.fillColor;
	}
	
	public Color getStrokeColor() {
		return this.strokeColor;
	}
	
	public void moveXDirection(double dx) {
		if (!isCaught) {
			this.xPosition += dx;
		}
	}

	public void moveYDirection(double dy) {
		if (!isCaught) {
			this.yPosition += dy;
		}
	}

	public void setXVelocity(double xVelocity) {
		this.xVelocity = Math.min(xVelocity, maxVelocity);
	}

	public void setYVelocity(double yVelocity) {
		this.yVelocity = Math.min(yVelocity, maxVelocity);
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public double getXVelocity() {
		return xVelocity;
	}
	
	public boolean getIsCaught() {
		return this.isCaught;
	}

	public void resetMotion() {
		this.xPosition = 0;
		this.yPosition = 0;
		this.xVelocity = 0;
		this.yVelocity = 0;
		isCaught = false;
	}
	
	public void setCaught(){
		isCaught = true;
	}
	public abstract int getWidth();

	public abstract int getHeight();

	public abstract void draw(GraphicsContext g);

	public abstract Sprite getSprite();
}
