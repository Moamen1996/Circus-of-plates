package saving;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import avatar.Avatar;
import avatar.EmptyStack;
import avatar.FullStack;
import avatar.Stack;
import avatar.StackState;
import gui.Main;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import shapes.CustomShape;
import shapes.ShapeFactory;
import shapes.ShapeGenerator;
import sprite.ShapeSprite;
import sprite.Sprite;
import rail.Allign;
import rail.Rail;
import rail.RailsContainer;

public class StateBundle implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<FakeShape> shapesInUse;
	private FakeStack[] firstPlayerStack, secondPlayerStack;
	private FakeAvatar firstPlayerAvatar, secondPlayerAvatar;
	private int difficulty;
	private ArrayList<FakeRail> fakeRails;
	private int state;

	public StateBundle(Avatar firstPlayerAvatar, Avatar secondPlayerAvatar, List<CustomShape> shapesInUse,
			int difficulty, RailsContainer railsContainer, int state) {
		initializeShapesInUse(shapesInUse);
		initializeStack(firstPlayerAvatar.getStack(), secondPlayerAvatar.getStack());
		this.firstPlayerAvatar = new FakeAvatar(firstPlayerAvatar.getX(), firstPlayerAvatar.getY(),
				firstPlayerAvatar.getPlayerIndex());
		this.secondPlayerAvatar = new FakeAvatar(secondPlayerAvatar.getX(), secondPlayerAvatar.getY(),
				secondPlayerAvatar.getPlayerIndex());
		fakeRails = new ArrayList<FakeRail>();
		this.difficulty = difficulty;
		initFakeRails(railsContainer);
		this.state = state;
	}

	private void initFakeRails(RailsContainer railsContainer) {
		List<Rail> rails = new ArrayList<Rail>(railsContainer.getRails());
		for (int i = 0; i < rails.size(); i++) {
			Rail rail = rails.get(i);
			Allign direction = rail.getDir();
			int height = rail.getHeight();
			int position = rail.getPos();
			int length = rail.getLen();
			List<CustomShape> shapes = rail.getShapes();
			fakeRails.add(new FakeRail(direction, height, length, position, getShapes(shapes)));
		}
	}

	private int getShapeType(CustomShape x) {
		if (x.getClass().getSimpleName().equals("RectangleShape"))
			return 1;
		return 0;
	}

	private ArrayList<FakeShape> getShapes(List<CustomShape> shapes) {
		ArrayList<FakeShape> fakeShapes = new ArrayList<FakeShape>();
		for (int i = 0; i < shapes.size(); i++) {
			FakeShape fakeShape = new FakeShape(shapes.get(i).getXPosition(), shapes.get(i).getYPosition(),
					shapes.get(i).getXVelocity(), shapes.get(i).getYVelocity(), shapes.get(i).getIsCaught(),
					shapes.get(i).getWidth(), shapes.get(i).getHeight(), getShapeType(shapes.get(i)),
					ShapeGenerator.getInd(shapes.get(i).getFillColor()));
			fakeShapes.add(fakeShape);
		}
		return fakeShapes;
	}

	private void initializeShapesInUse(List<CustomShape> shapesInUse) {
		this.shapesInUse = new ArrayList<FakeShape>();
		for (int i = 0; i < shapesInUse.size(); i++) {
			FakeShape fakeShape = new FakeShape(shapesInUse.get(i).getXPosition(), shapesInUse.get(i).getYPosition(),
					shapesInUse.get(i).getXVelocity(), shapesInUse.get(i).getYVelocity(),
					shapesInUse.get(i).getIsCaught(), shapesInUse.get(i).getWidth(), shapesInUse.get(i).getHeight(),
					getShapeType(shapesInUse.get(i)), ShapeGenerator.getInd(shapesInUse.get(i).getFillColor()));
			this.shapesInUse.add(fakeShape);
		}
	}

	private void initializeStack(Stack[] firstPlayerStack, Stack[] secondPlayerStack) {
		this.firstPlayerStack = new FakeStack[2];
		this.secondPlayerStack = new FakeStack[2];
		initOneDPlayerOne(0, firstPlayerStack);
		initOneDPlayerOne(1, firstPlayerStack);
		initOneDPlayerTwo(0, secondPlayerStack);
		initOneDPlayerTwo(1, secondPlayerStack);
	}

	private void initOneDPlayerOne(int index, Stack[] firstPlayerStack) {
		ArrayList<Sprite> shapes = new ArrayList<Sprite>(firstPlayerStack[index].getShapesSprite());
		ArrayList<FakeShape> fake = new ArrayList<FakeShape>();
		for (int i = 0; i < shapes.size(); i++) {
			ShapeSprite shapeSprite = (ShapeSprite) shapes.get(i);
			CustomShape cs = shapeSprite.getCustomShape();
			FakeShape fakeShape = new FakeShape((double) shapeSprite.getX(), (double) shapeSprite.getY(), 0, 0, true,
					shapeSprite.getWidth(), shapeSprite.getHeight(), getShapeType(cs),
					ShapeGenerator.getInd(shapeSprite.getColor()));
			fake.add(fakeShape);
		}
		this.firstPlayerStack[index] = new FakeStack(firstPlayerStack[index].getScore(), firstPlayerStack[index].getX(),
				firstPlayerStack[index].getY(), firstPlayerStack[index].getPlayerIndex(),
				firstPlayerStack[index].getHeightSum(), fake, firstPlayerStack[index].checkStackFull());
	}

	private void initOneDPlayerTwo(int index, Stack[] secondPlayerStack) {
		ArrayList<Sprite> shapes = new ArrayList<Sprite>(secondPlayerStack[index].getShapesSprite());
		ArrayList<FakeShape> fake = new ArrayList<FakeShape>();
		for (int i = 0; i < shapes.size(); i++) {
			ShapeSprite shapeSprite = (ShapeSprite) shapes.get(i);
			CustomShape cs = shapeSprite.getCustomShape();
			FakeShape fakeShape = new FakeShape((double) shapeSprite.getX(), (double) shapeSprite.getY(), 0, 0, true,
					shapeSprite.getWidth(), shapeSprite.getHeight(), getShapeType(cs),
					ShapeGenerator.getInd(shapeSprite.getColor()));
			fake.add(fakeShape);
		}
		this.secondPlayerStack[index] = new FakeStack(secondPlayerStack[index].getScore(),
				secondPlayerStack[index].getX(), secondPlayerStack[index].getY(),
				secondPlayerStack[index].getPlayerIndex(), secondPlayerStack[index].getHeightSum(), fake,
				secondPlayerStack[index].checkStackFull());
	}

	private CustomShape getCustom(FakeShape shape) {
		try {
			return (CustomShape) ShapeFactory.getLoaded().get(shape.getShape())[1].newInstance((int) shape.getYPos(),
					(int) shape.getYPos(), shape.getWidth(), shape.getHeight(),
					ShapeGenerator.getColor(shape.getColorInd()), Color.BLACK);
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<CustomShape> getInUse() {
		ArrayList<CustomShape> retInUse = new ArrayList<CustomShape>();
		for (int i = 0; i < this.shapesInUse.size(); i++) {
			retInUse.add(getCustom(shapesInUse.get(i)));
		}
		return retInUse;
	}

	public Avatar[] getAvatar() {
		Avatar first = new Avatar();
		first.setX(this.firstPlayerAvatar.getXPos());
		first.setY(this.firstPlayerAvatar.getYPos());
		first.setIndex(firstPlayerAvatar.getPlayerIndex());
		Stack[] firstStack = new Stack[2];
		firstStack[0] = getOneDP1(0);
		firstStack[1] = getOneDP1(1);
		first.setStack(firstStack);
		Avatar second = new Avatar();
		second.setX(this.secondPlayerAvatar.getXPos());
		second.setY(this.secondPlayerAvatar.getYPos());
		second.setIndex(secondPlayerAvatar.getPlayerIndex());
		Stack[] secondStack = new Stack[2];
		secondStack[0] = getOneDP2(0);
		secondStack[1] = getOneDP2(1);
		second.setStack(secondStack);
		Avatar[] avatars = new Avatar[2];
		avatars[0] = first;
		avatars[1] = second;
		return avatars;
	}

	private Stack getOneDP1(int ind) {
		ArrayList<Sprite> retShapes = new ArrayList<Sprite>();
		ArrayList<FakeShape> loaded = new ArrayList<FakeShape>(firstPlayerStack[ind].getShapes());
		for (int i = 0; i < loaded.size(); i++) {
			CustomShape customShape = getCustom(loaded.get(i));
			retShapes.add(new ShapeSprite(customShape));
		}
		StackState state;
		if (firstPlayerStack[ind].getState())
			state = new FullStack();
		else
			state = new EmptyStack();
		Stack retStack = new Stack(firstPlayerStack[ind].getScore(), firstPlayerStack[ind].getXPos(),
				firstPlayerStack[ind].getYPos(), firstPlayerStack[ind].getPlayerIndex(),
				firstPlayerStack[ind].getHeightSum(), retShapes, state);
		retStack.initializeState();
		return retStack;
	}

	private Stack getOneDP2(int ind) {
		ArrayList<Sprite> retShapes = new ArrayList<Sprite>();
		ArrayList<FakeShape> loaded = new ArrayList<FakeShape>(secondPlayerStack[ind].getShapes());
		for (int i = 0; i < loaded.size(); i++) {
			CustomShape customShape = getCustom(loaded.get(i));
			retShapes.add(new ShapeSprite(customShape));
		}
		StackState state;
		if (secondPlayerStack[ind].getState())
			state = new FullStack();
		else
			state = new EmptyStack();
		Stack retStack = new Stack(secondPlayerStack[ind].getScore(), secondPlayerStack[ind].getXPos(),
				secondPlayerStack[ind].getYPos(), secondPlayerStack[ind].getPlayerIndex(),
				secondPlayerStack[ind].getHeightSum(), retShapes, state);
		retStack.initializeState();
		return retStack;
	}

	public int getDifficulty() {
		return this.difficulty;
	}

	public RailsContainer getRailsContainer() {
		ArrayList<Rail> rails = new ArrayList<Rail>();
		for (int i = 0; i < fakeRails.size(); i++) {
			List<CustomShape> shapes = new ArrayList<CustomShape>();
			ArrayList<FakeShape> fakeShapes = new ArrayList<FakeShape>(fakeRails.get(i).getShapes());
			for (int j = 0; j < fakeShapes.size(); j++) {
				shapes.add(getCustom(fakeShapes.get(j)));
			}
			try{
			String path = Main.class.getResource(Rail.IMAGE).toURI().toString();
			Image spriteImage = new Image(path);
				Rail rail = new Rail(fakeRails.get(i).getDir(), fakeRails.get(i).getPos(), fakeRails.get(i).getHeight(),
						fakeRails.get(i).getLength(), spriteImage, shapes);
				rails.add(rail);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return new RailsContainer(rails);
	}

	public int getState() {
		return this.state;
	}

}