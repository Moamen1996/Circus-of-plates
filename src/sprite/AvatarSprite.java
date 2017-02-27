package sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AvatarSprite extends Sprite {

	private double x, y;
	private Image spriteImage;

	public AvatarSprite(int x, int y, Image spriteImage) {
		super();
		this.x = x;
		this.y = y;
		this.spriteImage = spriteImage;
	}

	public void draw(GraphicsContext g) {
		g.drawImage(spriteImage, x, y);
	}

}
