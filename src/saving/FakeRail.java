package saving;

import java.util.ArrayList;
import java.util.List;

import rail.Allign;

public class FakeRail {

	private Allign direction;
	private int height, length, position;
	private List<FakeShape> shapes;

	public FakeRail(Allign direction, int height, int length, int position, List<FakeShape> shapes) {
		this.direction = direction;
		this.height = height;
		this.length = length;
		this.position = position;
		this.shapes = new ArrayList<FakeShape>(shapes);
	}

	public Allign getDir() {
		return this.direction;
	}

	public int getHeight() {
		return this.height;
	}

	public int getLength() {
		return this.length;
	}

	public int getPos() {
		return this.position;
	}

	public List<FakeShape> getShapes() {
		return this.shapes;
	}

}
