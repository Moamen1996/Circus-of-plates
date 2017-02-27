package saving;

import java.util.ArrayList;

public class FakeStack {

	private int score;
	private int xPos, yPos;
	private int playerIndex;
	private int heightSum;

	private ArrayList<FakeShape> shapes;
	private boolean state;

	public FakeStack(int score, int xPos, int yPos, int playerIndex, int heightSum, ArrayList<FakeShape> shapes,
			boolean state) {
		this.score = score;
		this.xPos = xPos;
		this.yPos = yPos;
		this.playerIndex = playerIndex;
		this.heightSum = heightSum;
		this.shapes = new ArrayList<FakeShape>(shapes);
		this.state = state;
	}

	public int getScore() {
		return this.score;
	}

	public int getXPos() {
		return this.xPos;
	}

	public int getYPos() {
		return this.yPos;
	}

	public int getPlayerIndex() {
		return this.playerIndex;
	}

	public int getHeightSum() {
		return this.heightSum;
	}

	public ArrayList<FakeShape> getShapes() {
		return this.shapes;
	}

	public boolean getState() {
		return this.state;
	}

}
