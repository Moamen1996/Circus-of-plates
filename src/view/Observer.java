package view;

import model.GameModel;

public interface Observer {

	public void attachSubject(GameModel gameModel);

	public void update();
}
