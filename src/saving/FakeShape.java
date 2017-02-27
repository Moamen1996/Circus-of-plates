package saving;

public class FakeShape {

	private double xPos, yPos;
	private double xVel, yVel;
	private boolean isCaught;
	private int width, height;
	private int shapeType;
	private int colorInd;

	public FakeShape(double xPos, double yPos, double xVel, double yVel, boolean isCaught, int width, int height,
			int shapeType, int colorInd) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
		this.isCaught = isCaught;
		this.width = width;
		this.height = height;
		this.shapeType = shapeType;
		this.colorInd = colorInd;
	}

	public double getXPos() {
		return this.xPos;
	}

	public double getYPos() {
		return this.yPos;
	}

	public double getXVel() {
		return this.xVel;
	}

	public double getYVel() {
		return this.yVel;
	}

	public boolean getIsCaught() {
		return this.isCaught;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getShape() {
		return this.shapeType;
	}

	public int getColorInd() {
		return this.colorInd;
	}

}
