package saving;

public class FakeAvatar {

	private int xPos, yPos, playerIndex;

	public FakeAvatar(int x, int y, int playerIndex) {
		this.xPos = x;
		this.yPos = y;
		this.playerIndex = playerIndex;
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

}
