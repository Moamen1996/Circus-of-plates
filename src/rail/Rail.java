package rail;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.image.Image;
import shapes.CustomShape;
import sprite.Sprite;
import sprite.RailSprite;

public class Rail {
	private static final Logger LOGGER = Logger.getLogger(Rail.class);

	public static final int[] xPosition = { 0, 750, 0, 750 };
	public static final int[] yPosition = { 30, 30, 70, 70 };
	public static final Allign[] allign = { Allign.LEFT_ALLIGN, Allign.RIGHT_ALLIGN, Allign.LEFT_ALLIGN,
			Allign.RIGHT_ALLIGN };
	public static int WIDTH = 200, HEIGHT = 10;
	public static final String IMAGE = "/pic/" + "rail.png";

	private Allign direction;
	private int height, length, position;
	private List<CustomShape> shapes;
	private Image spriteImage;

	/**
	 * @param direction
	 *            the direction from which shapes start moving.
	 * @param length
	 *            the length of the rail.
	 * @param height
	 *            the y position of the rail.
	 * @param position
	 *            the position of the left side of the rail.
	 * @param spriteImage
	 *            The Image of the rail.
	 */
	public Rail(Allign direction, int position, int height, int length, Image spriteImage) {
		this.direction = direction;
		this.height = height;
		this.length = length;
		this.position = position;
		this.spriteImage = spriteImage;
		this.shapes = new ArrayList<>();
	}

	public Rail(Allign direction, int position, int height, int length, Image spriteImage, List<CustomShape> shapes) {
		this.direction = direction;
		this.height = height;
		this.length = length;
		this.position = position;
		this.spriteImage = spriteImage;
		this.shapes = new ArrayList<CustomShape>(shapes);
	}

	/**
	 * @return the x position from which shapes start moving.
	 **/
	public int getStartX() {
		switch (direction) {
		case LEFT_ALLIGN:
			return this.position;
		default:
			return this.position + this.length;
		}
	}

	/**
	 * @return The x position from which shapes start falling.
	 */
	public int getEndX() {
		switch (direction) {
		case LEFT_ALLIGN:
			return this.length;
		default:
			return this.position;
		}
	}

	public int getHeight() {
		return this.height;
	}

	/**
	 * @param shape
	 *            The shape to be added to the rail.
	 */
	public void putShapeOnRail(CustomShape shape, double velocity) {
		if (this.direction == Allign.LEFT_ALLIGN) {
			shape.setXPostion(this.getStartX() - shape.getWidth());
			shape.setXVelocity(velocity);
		} else {
			shape.setXPostion(this.getStartX());
			shape.setXVelocity(-velocity);
		}
		shape.setYPostion(this.getHeight() - shape.getHeight());
		this.shapes.add(shape);
	}

	public void removeShape(int index) {
		shapes.remove(index);
	}

	public static boolean falling(Rail rail, CustomShape shape) {
		boolean ret;
		if (rail.direction == Allign.LEFT_ALLIGN) {
			ret = shape.getXPosition() >= rail.getEndX();
		} else {
			ret = shape.getXPosition() + shape.getWidth() <= rail.getEndX();
		}
		LOGGER.info("Shape is falling ? " + ret);
		return ret;
	}

	public List<CustomShape> getShapes() {
		return this.shapes;
	}

	public Sprite getSprite() {
		return new RailSprite(this.position, this.height, this.spriteImage);
	}

	public Allign getDir() {
		return this.direction;
	}

	public int getPos() {
		return this.position;
	}

	public int getLen() {
		return this.length;
	}

	public Image getImage() {
		return this.spriteImage;
	}
}
